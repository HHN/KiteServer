package com.hhn.kite2server.account.reigstration;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"registration"})
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService accountService;

    @PostMapping
    public Response register(@RequestBody RegistrationRequest request) {
        Response response = new Response();
        ResultCode code = accountService.register(request);
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }
}
