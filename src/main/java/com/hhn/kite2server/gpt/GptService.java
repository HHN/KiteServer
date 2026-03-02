package com.hhn.kite2server.gpt;

import com.hhn.kite2server.data.AddDataObjectRequest;
import com.hhn.kite2server.data.DataService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class GptService {

    private static final Logger logger = LoggerFactory.getLogger(GptService.class);
    private static final String OPENAI_CHAT_URL = "https://api.openai.com/v1/chat/completions";
    private static final MediaType JSON = MediaType.parse("application/json");

    private final DataService dataService;

    // Aus .env / OS-Env / application.yml
    @Value("${openai.api.key:}")
    private String openaiApiKey;

    @Value("${openai.model:gpt-4o}")
    private String model;

    @Value("${openai.max-tokens:2000}")
    private int maxTokens;

    @Value("${openai.temperature:0.5}")
    private double temperature;

    @Value("${openai.enabled:true}")
    private boolean openaiEnabled;

    private final OkHttpClient client = buildHttpClient();

    @CircuitBreaker(name = "openai", fallbackMethod = "fallbackGetCompletion")
    public String getCompletion(String prompt) {
        try {
            if (!openaiEnabled) {
                logger.warn("OpenAI outbound calls are disabled via kill-switch (openai.enabled=false).");
                return error("Dienst vorübergehend deaktiviert.");
            }

            if (openaiApiKey == null || openaiApiKey.isBlank()) {
                logger.error("OPENAI API Key fehlt – bitte in .env / Env-Var setzen (openai.api.key / OPENAI_API_KEY).");
                return error("Konfiguration: OPENAI_API_KEY fehlt.");
            }
            if (prompt == null || prompt.isBlank()) {
                return error("Prompt ist leer.");
            }

            JSONObject jsonBody = new JSONObject()
                    .put("model", model)
                    .put("messages", new JSONArray()
                            .put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."))
                            .put(new JSONObject().put("role", "user").put("content", prompt))
                    )
                    .put("max_tokens", maxTokens)
                    .put("temperature", temperature);

            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);

            Request request = new Request.Builder()
                    .url(OPENAI_CHAT_URL)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + openaiApiKey)
                    .build();

            String respString;
            int status;
            try (Response response = client.newCall(request).execute()) {
                status = response.code();
                ResponseBody responseBody = response.body();
                respString = responseBody != null ? responseBody.string() : "";
            }

            if (status < 200 || status >= 300) {
                // Fehlermeldung extrahieren (falls vorhanden)
                String apiMsg = extractErrorMessage(respString);
                logger.error("OpenAI call failed: HTTP {} - {}", status, apiMsg != null ? apiMsg : respString);
                // IMPORTANT: Throw an exception so that the circuit breaker counts the error!
                throw new IOException(String.format("OpenAI-Fehler (HTTP %d): %s", status, apiMsg != null ? apiMsg : "siehe Server-Logs"));
            }

            // Erfolgsfall: "choices[0].message.content" (Fallback auf "text", falls vorhanden)
            JSONObject json = new JSONObject(respString);
            JSONArray choices = json.optJSONArray("choices");
            if (choices == null || choices.isEmpty()) {
                logger.error("Antwort ohne 'choices': {}", respString);
                throw new IOException("Unerwartete Antwortstruktur der OpenAI API (kein 'choices' in der Antwort).");
            }

            JSONObject choice0 = choices.getJSONObject(0);
            String completion = null;
            if (choice0.has("message")) {
                completion = choice0.getJSONObject("message").optString("content", null);
            }
            if (completion == null || completion.isBlank()) {
                completion = choice0.optString("text", null); // Fallback für Modelle, die 'text' liefern
            }
            if (completion == null || completion.isBlank()) {
                logger.error("Konnte keine Completion extrahieren: {}", respString);
                throw new IOException("Konnte keine Antwort aus der API-Antwort extrahieren.");
            }

            // Persistieren
            AddDataObjectRequest add = new AddDataObjectRequest();
            add.setCompletion(completion);
            add.setPrompt(prompt);
            dataService.addDataObject(add);

            return completion;

        } catch (Exception e) {
            logger.error("Error occurred while getting completion.", e);
            // Throw exception so that the circuit breaker triggers.
            // The fallback method then catches this for the user.
            throw new RuntimeException(e);
        }
    }

    // This method is called when the circuit breaker is OPEN (automatic kill switch)
    // or when an exception has been thrown.
    public String fallbackGetCompletion(String prompt, Throwable t) {
        logger.warn("Fallback ausgelöst (Circuit Breaker oder Fehler): {}", t.getMessage());
        return error("Der KI-Dienst ist derzeit aufgrund hoher Last oder technischer Probleme kurzzeitig nicht verfügbar. Bitte versuchen Sie es in ein paar Minuten erneut.");
    }

    private OkHttpClient buildHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .callTimeout(120, TimeUnit.SECONDS);

        // Optionales HTTP-Logging (DEBUG-Level). Achtung: große Responses & sensible Daten!
        if (logger.isDebugEnabled()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(msg -> logger.debug("OKHTTP {}", msg));
            logging.redactHeader("Authorization");
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    private String extractErrorMessage(String respString) {
        try {
            JSONObject root = new JSONObject(respString);
            JSONObject err = root.optJSONObject("error");
            return err != null ? err.optString("message", null) : null;
        } catch (Exception ignore) {
            return null;
        }
    }

    private String error(String msg) {
        // Einfaches JSON-Fehlerformat; ggf. an deinen Controller/Client anpassen
        return new JSONObject().put("error", msg).toString();
    }
}
