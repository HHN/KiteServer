package com.hhn.kite2server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhn.kite2server.appuser.AppUserService;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.login.token.AuthenticationTokenRepository;
import com.hhn.kite2server.response.Response;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AppUserService userDetailsService;
    private final AuthenticationTokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/login")) { // If it is a log in request, send it to the filter.
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) { // If it has no auth, send it to the filter.
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
                var isTokenValid = tokenRepository.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);

                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response); // auth it and send it to the filter.
    }
}
