package com.hhn.kite2server.security;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TempTokenService {

    // Speichert Token -> Ablaufzeitpunkt
    private final Map<String, Instant> tokenStore = new ConcurrentHashMap<>();

    // Wie lange ein Token gültig ist (10 Minuten)
    private static final long VALIDITY_SECONDS = 600;

    public String createToken() {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, Instant.now().plusSeconds(VALIDITY_SECONDS));
        return token;
    }

    public boolean isValid(String token) {
        if (token == null || !tokenStore.containsKey(token)) {
            return false;
        }

        Instant expiration = tokenStore.get(token);
        // Wenn abgelaufen, aus dem Store entfernen und false zurückgeben
        boolean isExpired = Instant.now().isAfter(expiration);
        if (isExpired) tokenStore.remove(token);
        return !isExpired;
    }
}