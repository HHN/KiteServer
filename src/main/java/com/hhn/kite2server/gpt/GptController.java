package com.hhn.kite2server.gpt;

import com.hhn.kite2server.response.ResultCode;
import com.hhn.kite2server.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "ai")
@AllArgsConstructor
public class GptController {

    private static final Logger logger = LoggerFactory.getLogger(GptController.class);
    private final GptService gptService;

    @PostMapping
    public Response getCompletion(@RequestBody GptRequest request) {
        Response response = new Response();

        // Bot-Check (Honeypot)
        if (request.getVerification() != null && !request.getVerification().isBlank()) {
            logger.warn("Bot detected! Honeypot field 'verification' was filled with: {}", request.getVerification());

            // Tarpit: We artificially make the bot wait for 3 seconds to slow it down.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            response.setResultCode(ResultCode.FAILURE.toInt());
            response.setResultText("Processing error."); // Neutral text so as not to give any clues
            return response;
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
}
