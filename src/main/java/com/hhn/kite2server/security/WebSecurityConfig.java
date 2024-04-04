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
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity()
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/ai").permitAll()
                        .requestMatchers(HttpMethod.GET, "/version").permitAll()
                        .requestMatchers(HttpMethod.POST, "/role").permitAll()

                        .requestMatchers(HttpMethod.GET, "/novelreview").permitAll()
                        .requestMatchers(HttpMethod.POST, "/novelreview").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/novelreview").permitAll()

                        .requestMatchers(HttpMethod.GET, "/aireview").permitAll()
                        .requestMatchers(HttpMethod.POST, "/aireview").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/aireview").permitAll()

                        .requestMatchers(HttpMethod.GET, "/reviewobserver").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reviewobserver").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/reviewobserver").permitAll()

                        .requestMatchers(HttpMethod.GET, "/data").permitAll()
                        .requestMatchers(HttpMethod.POST, "/data").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/data").permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling ->
                exceptionHandling
                .authenticationEntryPoint((request, response, e) ->
                {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_OK);
                    Response customResponse = new Response();
                    customResponse.setResultText(ResultCode.NOT_AUTHORIZED.toString());
                    customResponse.setResultCode(ResultCode.NOT_AUTHORIZED.toInt());
                    new ObjectMapper().writeValue(response.getOutputStream(), customResponse);
                }));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
