package com.hhn.kite2server.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;

@Component
public class HmacRequestValidator {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final long MAX_TIMESTAMP_AGE_SECONDS = 300;

    private final String secret;

    public HmacRequestValidator(@Value("${security.kite.hmac-secret}") String secret) {
        this.secret = secret;
    }

    public boolean isValid(String timestampHeader, String signatureHeader) {
        if (timestampHeader == null || timestampHeader.isBlank()) {
            return false;
        }

        if (signatureHeader == null || signatureHeader.isBlank()) {
            return false;
        }

        long timestamp;
        try {
            timestamp = Long.parseLong(timestampHeader);
        } catch (NumberFormatException e) {
            return false;
        }

        long now = Instant.now().getEpochSecond();
        long age = Math.abs(now - timestamp);

        if (age > MAX_TIMESTAMP_AGE_SECONDS) {
            return false;
        }

        String expectedSignature = calculateHmac(timestampHeader, secret);

        return MessageDigest.isEqual(
                expectedSignature.getBytes(StandardCharsets.UTF_8),
                signatureHeader.toLowerCase().getBytes(StandardCharsets.UTF_8)
        );
    }

    private String calculateHmac(String data, String key) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);

            SecretKeySpec keySpec = new SecretKeySpec(
                    key.getBytes(StandardCharsets.UTF_8),
                    HMAC_ALGORITHM
            );

            mac.init(keySpec);

            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new IllegalStateException("Could not calculate HMAC signature.", e);
        }
    }
}