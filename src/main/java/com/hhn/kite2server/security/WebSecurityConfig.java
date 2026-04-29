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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ObjectMapper objectMapper, TokenFilter tokenFilter) throws Exception {
        http
                // Insert TokenFilter before the standard authentication filter
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)

                // 1. Configure CORS
                .cors(Customizer.withDefaults())

                // Disable CSRF (often useful for APIs, but check your requirements)
                .csrf(AbstractHttpConfigurer::disable)
                // Configure endpoints:
                .authorizeHttpRequests(auth -> auth
                        // Endpoints that should be accessible without restriction
                        .requestMatchers("/api/auth/**").permitAll() 
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
                        // All other endpoints
                        .anyRequest().authenticated()
                )
                // Enable HTTP Basic Authentication
                .httpBasic(Customizer.withDefaults())
                // Set session management to stateless
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Exception handling: Return a JSON response if authentication is missing
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint((request, response, e) -> {
                            SecurityResponseUtil.writeErrorResponse(response, objectMapper);
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
                "https://kite-app.de"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        config.setAllowedHeaders(List.of(
                "Content-Type",
                "X-Kite-Timestamp",
                "X-Kite-Signature",
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

    // Defines the secure PasswordEncoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Example: In-memory user management with encrypted password
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
