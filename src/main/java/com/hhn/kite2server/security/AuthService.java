package com.hhn.kite2server.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final String expectedPassphrase;

    public AuthService(@Value("${security.kite.passphrase:}") String expectedPassphrase) {
        this.expectedPassphrase = expectedPassphrase;
    }

    public boolean validatePassphrase(String clientPassphrase) {
        return expectedPassphrase != null
                && !expectedPassphrase.isBlank()
                && expectedPassphrase.equals(clientPassphrase);
    }
}