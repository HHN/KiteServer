package com.hhn.kite2server.gpt;

import com.hhn.kite2server.response.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for communication with the LLM (OpenAI).
 * Includes security mechanisms such as honeypot and tarpit against bots.
 */
@RestController
@RequestMapping(path = "ai")
@RequiredArgsConstructor
public class GptController {

    private static final Logger logger = LoggerFactory.getLogger(GptController.class);
    private final GptService gptService;

    /**
     * Main endpoint for AI requests.
     *
     * @param request The request object with prompt and security fields.
     * @return The AI's response or an error message.
     */
    @PostMapping
    public Response getCompletion(@RequestBody GptRequest request) {

        if (isBotRequest(request)) {
            return Response.error(ResultCode.FAILURE, "Processing error."); // Aborted because bot detected
        }

        String completion = gptService.getCompletion(request.prompt());

        boolean isErrorPayload = completion != null && completion.startsWith("{\"error\"");
        if (completion == null || completion.isBlank() || isErrorPayload) {
            return Response.error(ResultCode.FAILURE, "Failed to get completion from AI service.");
        }
        return Response.success(ResultCode.SUCCESSFULLY_GOT_COMPLETION, completion);
    }
   
    /**
     * Checks whether the request is from a bot (honeypot check).
     * If so, the thread is artificially delayed (tarpit) to slow down the bot.
     */
    private boolean isBotRequest(GptRequest request) {
        // The honeypot field ‘verification’ must be empty. Bots often fill it in automatically.
        if (request.verification() != null && !request.verification().isBlank()) {
            logger.warn("Bot detected! Honeypot field 'verification' was filled with: {}", request.verification());

            // Tarpit: Artificial delay of 3 seconds to slow down automated attacks.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return true;
        }
        return false;
    }
}
