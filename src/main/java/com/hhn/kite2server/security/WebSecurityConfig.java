package com.hhn.kite2server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhn.kite2server.response.ResultCode;
import com.hhn.kite2server.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private final String apiUsername;
    private final String apiPassword;

    public WebSecurityConfig(@Value("${security.api.username}") String apiUsername,
                             @Value("${security.api.password}") String apiPassword) {
        this.apiUsername = apiUsername;
        this.apiPassword = apiPassword;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF deaktivieren (für APIs oft sinnvoll, aber prüfe deine Anforderungen)
                .csrf(AbstractHttpConfigurer::disable)
                // Endpunkte konfigurieren:
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                // HTTP Basic Authentication aktivieren
                .httpBasic(Customizer.withDefaults())
                // Session-Management auf stateless stellen
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Exception-Handling: Bei fehlender Authentifizierung wird eine JSON-Antwort geliefert
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint((request, response, e) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            Response customResponse = new Response();
                            customResponse.setResultText(ResultCode.NOT_AUTHORIZED.toString());
                            customResponse.setResultCode(ResultCode.NOT_AUTHORIZED.toInt());
                            new ObjectMapper().writeValue(response.getOutputStream(), customResponse);
                        })
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Definiert den sicheren PasswordEncoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Beispiel: In-Memory-Benutzerverwaltung mit verschlüsseltem Passwort
    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername(apiUsername)
                        .password(passwordEncoder.encode(apiPassword))
                        .roles("USER")
                        .build()
        );
    }
}
