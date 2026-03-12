package com.hhn.kite2server.gpt;

import com.hhn.kite2server.response.ResultCode;
import com.hhn.kite2server.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Controller for communication with the LLM (OpenAI).
 * Includes security mechanisms such as honeypot and tarpit against bots.
 */
@RestController
@RequestMapping(path = "ai")
public class GptController {

    private static final Logger logger = LoggerFactory.getLogger(GptController.class);
    private final GptService gptService;

    @Value("${KITE_PASSPHRASE}")
    private String expectedPassphrase;

    public GptController(GptService gptService) {
        this.gptService = gptService;
    }

    /**
     * Main endpoint for AI requests.
     *
     * @param request The request object with prompt and security fields.
     * @return The AI's response or an error message.
     */
    @PostMapping
    public Response getCompletion(
            @RequestHeader("X-Kite-Passphrase") String clientPassphrase,
            @RequestBody GptRequest request) {

        Response response = new Response();

        if (expectedPassphrase == null || !expectedPassphrase.equals(clientPassphrase)) {
            logger.warn("Unauthorised Access: Passphrase-Mismatch.");
            response.setResultCode(ResultCode.FAILURE.toInt());
            response.setResultText("Unauthorized.");
            return response;
        }

        if (isBotRequest(request, response)) {
            return response; // Aborted because bot detected
        }

        String completion = gptService.getCompletion(request.getPrompt());

        boolean isErrorPayload = completion != null && completion.startsWith("{\"error\"");
        if (completion == null || completion.isBlank() || isErrorPayload) {
            response.setCompletion(completion);
            response.setResultCode(ResultCode.FAILURE.toInt());
            response.setResultText(ResultCode.FAILURE.toString());
            return response;
        }
        response.setCompletion(completion);
        response.setResultCode(ResultCode.SUCCESSFULLY_GOT_COMPLETION.toInt());
        response.setResultText(ResultCode.SUCCESSFULLY_GOT_COMPLETION.toString());
        return response;
    }
   
    /**
     * Checks whether the request is from a bot (honeypot check).
     * If so, the thread is artificially delayed (tarpit) to slow down the bot.
     */
    private boolean isBotRequest(GptRequest request, Response response) {
        // The honeypot field ‘verification’ must be empty. Bots often fill it in automatically.
        if (request.getVerification() != null && !request.getVerification().isBlank()) {
            logger.warn("Bot detected! Honeypot field 'verification' was filled with: {}", request.getVerification());

            // Tarpit: Artificial delay of 3 seconds.
            // This ties up the attacker's resources and makes brute force attacks inefficient.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // We return a generic error to avoid giving the bot any clues about detection.
            response.setResultCode(ResultCode.FAILURE.toInt());
            response.setResultText("Processing error.");
            return true;
        }
        return false;
    }
}
