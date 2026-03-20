package com.hhn.kite2server.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${KITE_PASSPHRASE}")
    private String expectedPassphrase;

    public boolean validatePassphrase(String clientPassphrase) {
        return expectedPassphrase != null && expectedPassphrase.equals(clientPassphrase);
    }
}
