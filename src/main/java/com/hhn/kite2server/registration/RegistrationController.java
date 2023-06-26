package com.hhn.kite2server.registration;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public Response register(@RequestBody RegistrationRequest request) {
        Response response = new Response();
        ResultCode code = registrationService.register(request);
        response.setResultCode(code.toInt());
        response.setResultText(code.toString());
        return response;
    }
}
