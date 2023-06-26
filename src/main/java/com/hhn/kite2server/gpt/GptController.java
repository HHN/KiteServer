package com.hhn.kite2server.gpt;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "ai")
@AllArgsConstructor
public class GptController {

    private final GptService gptService;

    @PostMapping
    public Response getCompletion(@RequestBody GptRequest request) {
        Response response = new Response();
        String completion = gptService.getCompletion(request.getPrompt());

        if (completion == "") {
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
