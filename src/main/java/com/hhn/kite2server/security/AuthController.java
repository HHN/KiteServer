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

    private final AuthService authService;

    @PostMapping("/session")
    public Response createSession(@RequestHeader(value = "X-Kite-Passphrase", required = false) String clientPassphrase) {

        // 1. Prüfen, ob die Passphrase stimmt
        if (!authService.validatePassphrase(clientPassphrase)) {
            return Response.error(ResultCode.NOT_AUTHORIZED, "Invalid Passphrase");
        }

        // 2. Token generieren
        String token = tokenService.createToken();

        // 3. Token zurückgeben
        return Response.successWithText(ResultCode.SUCCESSFULLY_GOT_COMPLETION, token, "Session created");
    }
}