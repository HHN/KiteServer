package com.hhn.kite2server.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhn.kite2server.appuser.AppUser;
import com.hhn.kite2server.appuser.AppUserRepository;
import com.hhn.kite2server.appuser.AppUserService;
import com.hhn.kite2server.common.ResultCode;
import com.hhn.kite2server.login.token.AuthenticationToken;
import com.hhn.kite2server.login.token.AuthenticationTokenRepository;
import com.hhn.kite2server.login.token.AuthenticationTokenType;
import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@AllArgsConstructor
@Service
public class LoginService {
    private final AppUserRepository repository;
    private final AppUserService appUserService;
    private final AuthenticationTokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Response login(LoginRequest request) {

        Response response = new Response();
        AppUser appUser = findUserByLoginRequest(request);

        if (appUser == null) {
            response.setResultCode(ResultCode.INVALID_CREDENTIALS.toInt());
            response.setResultText(ResultCode.INVALID_CREDENTIALS.toString());
            return response;
        }

        if (!appUserService.isEmailAlreadyConfirmed(appUser.getEmail())) {
            response.setResultCode(ResultCode.EMAIL_NOT_CONFIRMED.toInt());
            response.setResultText(ResultCode.EMAIL_NOT_CONFIRMED.toString());
            return response;
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            appUser.getUsername(),
                            request.getPassword()
                    )
            );
            var user = repository.findByUsername(appUser.getUsername())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            response.setAuthToken(jwtToken);
            response.setRefreshToken(refreshToken);
            response.setResultCode(ResultCode.SUCCESSFULLY_LOGGED_IN.toInt());
            response.setResultText(ResultCode.SUCCESSFULLY_LOGGED_IN.toString());
            return response;

        } catch (AuthenticationException e) {
            response.setResultCode(ResultCode.INVALID_CREDENTIALS.toInt());
            response.setResultText(ResultCode.INVALID_CREDENTIALS.toString());
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.setResultCode(ResultCode.FAILURE.toInt());
            response.setResultText(ResultCode.FAILURE.toString());
            return response;
        }
    }

    private AppUser findUserByLoginRequest(LoginRequest request) {
        if (repository.findByEmail(request.getEmail().toLowerCase()).isPresent()) {
            return repository.findByEmail(request.getEmail().toLowerCase()).get();

        } else if (repository.findByUsername(request.getUsername().toLowerCase()).isPresent()) {
            return repository.findByUsername(request.getUsername().toLowerCase()).get();
        }
        return null;
    }

    private void saveUserToken(AppUser user, String jwtToken) {
        var token = AuthenticationToken.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(AuthenticationTokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(AppUser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                Response authResponse = new Response();
                authResponse.setAuthToken(accessToken);
                authResponse.setRefreshToken(refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
