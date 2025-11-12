package com.hhn.kite2server.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhn.kite2server.response.ResultCode;
import com.hhn.kite2server.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
@Configuration
@AllArgsConstructor
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF deaktivieren (für APIs oft sinnvoll, aber prüfe deine Anforderungen)
                .csrf(AbstractHttpConfigurer::disable)
                // Endpunkte konfigurieren:
                .authorizeHttpRequests(auth -> auth
                        // Endpunkte, die uneingeschränkt erreichbar sein sollen
                        .requestMatchers(HttpMethod.POST, "/ai").permitAll()
                        .requestMatchers(HttpMethod.GET, "/version").permitAll()
                        .requestMatchers(HttpMethod.POST, "/role").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/mappings").permitAll()
                        .requestMatchers(HttpMethod.GET, "/novelreview").permitAll()
                        .requestMatchers(HttpMethod.POST, "/novelreview").permitAll()
                        .requestMatchers(HttpMethod.GET, "/aireview").permitAll()
                        .requestMatchers(HttpMethod.POST, "/aireview").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviewobserver").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reviewobserver").permitAll()
                        .requestMatchers(HttpMethod.GET, "/data").permitAll()
                        .requestMatchers(HttpMethod.POST, "/data").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/data").permitAll()
                        .requestMatchers(HttpMethod.POST, "/metrics/scenes/*/hit").permitAll()
                        .requestMatchers(HttpMethod.POST, "/metrics/playthroughs/hit").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/metrics/playthroughs").authenticated()
                        .requestMatchers(HttpMethod.GET,  "/metrics/scenes").authenticated()
                        .requestMatchers(HttpMethod.POST, "/metrics/reset-all").authenticated()
                        .requestMatchers("/data/export").authenticated()
                        // Alle anderen Endpunkte
                        .anyRequest().permitAll()
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
                User.withUsername("KiteRoot")
                        .password(passwordEncoder.encode("REDACTED_PASSWORD"))
                        .roles("USER")
                        .build()
        );
    }
}
