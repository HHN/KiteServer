package com.hhn.kite2server.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TempTokenService tokenService;
    private final HmacRequestValidator hmacRequestValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 1. Auth-Endpunkt: HMAC prüfen.
        // Unity holt hiermit einen temporären Token.
        if (path.startsWith("/api/auth")) {
            String timestamp = request.getHeader("X-Kite-Timestamp");
            String signature = request.getHeader("X-Kite-Signature");

            if (hmacRequestValidator.isValid(timestamp, signature)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid HMAC Signature");
            }
            return;
        }

        // 2. AI-Endpunkt: weiterhin Bearer Token prüfen.
        // HMAC wird hier NICHT geprüft.
        if (path.startsWith("/ai")) {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7);

                if (tokenService.isValid(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Token");
            return;
        }

        // 3. Alle anderen Requests an Spring Security weitergeben.
        filterChain.doFilter(request, response);
    }
}