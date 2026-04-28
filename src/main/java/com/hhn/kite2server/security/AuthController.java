package com.hhn.kite2server.security;

import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.response.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TempTokenService tokenService;

    @PostMapping("/session")
    public Response createSession() {
        String token = tokenService.createToken();

        return Response.successWithText(
                ResultCode.SUCCESSFULLY_GOT_COMPLETION,
                token,
                "Session created"
        );
    }
}