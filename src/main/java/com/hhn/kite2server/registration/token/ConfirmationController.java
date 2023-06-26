package com.hhn.kite2server.registration.token;

import com.hhn.kite2server.appuser.AppUserService;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "confirm/**")
@AllArgsConstructor
public class ConfirmationController {

    private final ConfirmationTokenService confirmationTokenService;
    private final AppUserService appUserService;

    @GetMapping
    public Response confirm(@RequestParam("token") String token) {
        Response response = new Response();
        ResultCode resultCode = confirmationTokenService.confirmToken(token, appUserService);
        response.setResultCode(resultCode.toInt());
        response.setResultText(resultCode.toString());
        return response;
    }
}