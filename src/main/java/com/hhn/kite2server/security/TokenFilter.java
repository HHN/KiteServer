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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Wir schützen nur den /ai Endpunkt mit diesem Filter
        if (!request.getRequestURI().startsWith("/ai")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Header auslesen: "Authorization: Bearer <uuid>"
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        final String token = authHeader.substring(7); // "Bearer " abschneiden
        if (!tokenService.isValid(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid or has expired");
            return;
        }

        filterChain.doFilter(request, response);
    }
}