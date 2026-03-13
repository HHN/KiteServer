package com.hhn.kite2server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhn.kite2server.response.ResultCode;
import com.hhn.kite2server.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ObjectMapper objectMapper) throws Exception {
        http
                // 1. CORS konfigurieren
                .cors(Customizer.withDefaults())

                // CSRF deaktivieren (für APIs oft sinnvoll, aber prüfe deine Anforderungen)
                .csrf(AbstractHttpConfigurer::disable)
                // Endpunkte konfigurieren:
                .authorizeHttpRequests(auth -> auth
                        // Endpunkte, die uneingeschränkt erreichbar sein sollen
                        .requestMatchers(HttpMethod.POST, "/ai").permitAll()
                        .requestMatchers(HttpMethod.GET, "/version").permitAll()
                        .requestMatchers(HttpMethod.POST, "/role").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/mappings").permitAll()
                        .requestMatchers(HttpMethod.GET, "/data").permitAll()
                        .requestMatchers(HttpMethod.POST, "/data").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/data").permitAll()
                        .requestMatchers(HttpMethod.POST, "/metrics/scenes/*/hit", "/metrics/playthroughs/hit").permitAll()
                        .requestMatchers("/metrics/**").authenticated()
                        .requestMatchers("/data/export").authenticated()
                        // Alle anderen Endpunkte
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
                            objectMapper.writeValue(response.getOutputStream(), customResponse);
                        })
                );
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        var config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "https://hhn.github.io",
                "https://kite.pages.it.hs-heilbronn.de",
                "https://kite-app.de/"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        config.setAllowedHeaders(List.of(
                "Content-Type",
                "X-Kite-Passphrase",
                "Authorization"
        ));

        config.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
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
    public UserDetailsService users(
            PasswordEncoder passwordEncoder,
            @Value("${security.api.username:KiteRoot}") String user,
            @Value("${security.api.password:fallback-geheim}") String pass) {

        return new InMemoryUserDetailsManager(
                User.withUsername(user)
                        .password(passwordEncoder.encode(pass))
                        .roles("USER")
                        .build()
        );
    }
}
