package com.hhn.kite2server.security;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for managing temporary session tokens.
 * Tokens are stored in memory and have a limited validity period.
 */
@Service
public class TempTokenService {

    // Maps token string to its expiration instant
    private final Map<String, Instant> tokenStore = new ConcurrentHashMap<>();

    // Token validity duration (10 minutes)
    private static final long VALIDITY_SECONDS = 600;

    /**
     * Creates a new temporary token and stores it with an expiration timestamp.
     * @return The generated UUID token string.
     */
    public String createToken() {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, Instant.now().plusSeconds(VALIDITY_SECONDS));
        return token;
    }

    /**
     * Checks if a token is present in the store and hasn't expired yet.
     * @param token The token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isValid(String token) {
        if (token == null || !tokenStore.containsKey(token)) {
            return false;
        }

        Instant expiration = tokenStore.get(token);
        
        // If expired, remove from store and return false
        boolean isExpired = Instant.now().isAfter(expiration);
        if (isExpired) {
            tokenStore.remove(token);
        }
        return !isExpired;
    }
}