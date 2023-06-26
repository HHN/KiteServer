package com.hhn.kite2server.logout;

import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.login.token.AuthenticationTokenRepository;
import com.hhn.kite2server.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final AuthenticationTokenRepository tokenRepository;

    public Response logout(HttpServletRequest request) {
        Response response = new Response();
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            response.setResultCode(ResultCode.LOG_OUT_FAILED.toInt());
            response.setResultText(ResultCode.LOG_OUT_FAILED.toString());
            return response;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
        response.setResultCode(ResultCode.SUCCESSFULLY_LOGGED_OUT.toInt());
        response.setResultText(ResultCode.SUCCESSFULLY_LOGGED_OUT.toString());
        return response;
    }
}