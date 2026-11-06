package com.hhn.kite2server.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Service for basic passphrase-based authentication logic.
 */
@Service
public class AuthService {

    private final String expectedPassphrase;

    public AuthService(@Value("${security.kite.passphrase:}") String expectedPassphrase) {
        this.expectedPassphrase = expectedPassphrase;
    }

    public boolean validatePassphrase(String clientPassphrase) {
        if (expectedPassphrase == null || expectedPassphrase.isBlank()
                || clientPassphrase == null || clientPassphrase.isBlank()) {
            return false;
        }
        return MessageDigest.isEqual(
                expectedPassphrase.getBytes(StandardCharsets.UTF_8),
                clientPassphrase.getBytes(StandardCharsets.UTF_8));
    }
}