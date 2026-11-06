package com.hhn.kite2server.gpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hhn.kite2server.data.AddDataObjectRequest;
import com.hhn.kite2server.data.DataService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Service responsible for communicating with the OpenAI API.
 * Features circuit breaking, error handling, and interaction persistence.
 * Uses Spring's RestClient (no third-party HTTP/JSON dependencies).
 */
@RequiredArgsConstructor
@Service
public class GptService {

    private static final Logger logger = LoggerFactory.getLogger(GptService.class);
    private static final String OPENAI_CHAT_URL = "https://api.openai.com/v1/chat/completions";

    private final DataService dataService;
    private final ObjectMapper objectMapper;

    // Configuration values from .env, environment variables, or application.yml
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

    private final RestClient restClient = buildRestClient();

    /**
     * Sends a prompt to OpenAI and returns the generated response.
     *
     * @param prompt The user input.
     * @return The AI-generated completion text.
     */
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

            ObjectNode jsonBody = objectMapper.createObjectNode();
            jsonBody.put("model", model);
            jsonBody.put("max_tokens", maxTokens);
            jsonBody.put("temperature", temperature);
            ArrayNode messages = jsonBody.putArray("messages");
            messages.addObject().put("role", "system").put("content", "You are a helpful assistant.");
            messages.addObject().put("role", "user").put("content", prompt);

            if (logger.isDebugEnabled()) {
                logger.debug("OpenAI request body: {}", jsonBody);
            }

            String respString = restClient.post()
                    .uri(OPENAI_CHAT_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                    .body(jsonBody.toString())
                    .retrieve()
                    .onStatus(status -> !status.is2xxSuccessful(), (request, response) -> {
                        String errorBody = response.getBody() != null
                                ? new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8)
                                : "";
                        String apiMsg = extractErrorMessage(errorBody);
                        logger.error("OpenAI call failed: HTTP {} - {}",
                                response.getStatusCode().value(), apiMsg != null ? apiMsg : errorBody);
                        // IMPORTANT: Throw an exception so that the circuit breaker counts the error!
                        throw new IOException(String.format("OpenAI-Fehler (HTTP %d): %s",
                                response.getStatusCode().value(), apiMsg != null ? apiMsg : "siehe Server-Logs"));
                    })
                    .body(String.class);

            if (respString == null) {
                respString = "";
            }

            // Success case: Extract content from "choices[0].message.content"
            JsonNode choices = objectMapper.readTree(respString).get("choices");
            if (choices == null || !choices.isArray() || choices.isEmpty()) {
                logger.error("Antwort ohne 'choices': {}", respString);
                throw new IOException("Unerwartete Antwortstruktur der OpenAI API (kein 'choices' in der Antwort).");
            }

            JsonNode choice0 = choices.get(0);
            String completion = null;
            JsonNode content = choice0.path("message").path("content");
            if (content.isTextual() && !content.asText().isBlank()) {
                completion = content.asText();
            }
            if (completion == null) {
                JsonNode text = choice0.path("text"); // Fallback for models providing 'text'
                if (text.isTextual() && !text.asText().isBlank()) {
                    completion = text.asText();
                }
            }
            if (completion == null) {
                logger.error("Konnte keine Completion extrahieren: {}", respString);
                throw new IOException("Konnte keine Antwort aus der API-Antwort extrahieren.");
            }

            // Persist the interaction
            AddDataObjectRequest add = new AddDataObjectRequest(prompt, completion);
            dataService.addDataObject(add);

            return completion;

        } catch (Exception e) {
            logger.error("Error occurred while getting completion.", e);
            // Throw exception so that the circuit breaker triggers.
            // The fallback method then catches this for the user.
            throw new RuntimeException(e);
        }
    }

    /**
     * Fallback method triggered by the Circuit Breaker when OpenAI is unavailable or errors occur.
     */
    public String fallbackGetCompletion(String prompt, Throwable t) {
        logger.warn("Fallback ausgelöst (Circuit Breaker oder Fehler): {}", t.getMessage());
        return error("Der KI-Dienst ist derzeit aufgrund hoher Last oder technischer Probleme kurzzeitig nicht verfügbar. Bitte versuchen Sie es in ein paar Minuten erneut.");
    }

    private RestClient buildRestClient() {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofSeconds(90));
        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }

    private String extractErrorMessage(String respString) {
        try {
            JsonNode err = objectMapper.readTree(respString).path("error");
            return err.isObject() && err.hasNonNull("message") ? err.get("message").asText() : null;
        } catch (Exception ignore) {
            return null;
        }
    }

    private String error(String msg) {
        // Simple JSON error format
        return objectMapper.createObjectNode().put("error", msg).toString();
    }
}