package com.hhn.kite2server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

import static com.hhn.kite2server.security.SecurityResponseUtil.writeErrorResponse;

/**
 * Filter responsible for validating security tokens and HMAC signatures.
 * It intercepts requests to /api/auth/** for HMAC validation and /ai/** for Bearer token validation.
 */
@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TempTokenService tokenService;
    private final HmacRequestValidator hmacRequestValidator;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/api/auth")) {
            // Wrap the request so the body can be read for HMAC validation 
            // while remaining available for the controller.
            CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
            
            String timestamp = wrappedRequest.getHeader("X-Kite-Timestamp");
            String signature = wrappedRequest.getHeader("X-Kite-Signature");
            String body = wrappedRequest.getBody(); // Uses the wrapper's helper method

            if (hmacRequestValidator.isValid(timestamp, signature, body)) {
                filterChain.doFilter(wrappedRequest, response);
            } else {
                writeErrorResponse(response, objectMapper);
            }
            return;
        }

        if (path.startsWith("/ai")) {
            // For the AI endpoint, we only check the Bearer token. 
            // Wrapping is only necessary here if we also wanted to read the body in the filter.
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7);

                if (tokenService.isValid(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            writeErrorResponse(response, objectMapper);
            return;
        }

        filterChain.doFilter(request, response); // Continue with the filter chain for other requests
    }
}
