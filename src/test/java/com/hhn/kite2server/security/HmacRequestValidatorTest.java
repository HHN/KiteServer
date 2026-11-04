package com.hhn.kite2server.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;

class HmacRequestValidatorTest {

    private static final String SECRET = "test-secret";
    private HmacRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new HmacRequestValidator(SECRET);
    }

    /** Computes the expected signature the same way the Kite client would. */
    private String sign(String timestamp, String body) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] hash = mac.doFinal((timestamp + body).getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }

    private String now() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    @Test
    @DisplayName("Valid signature with body is accepted")
    void validSignatureWithBody() throws Exception {
        String ts = now();
        assertThat(validator.isValid(ts, sign(ts, "{\"vn\":\"123\"}"), "{\"vn\":\"123\"}")).isTrue();
    }

    @Test
    @DisplayName("Valid signature with null body is accepted")
    void validSignatureWithNullBody() throws Exception {
        String ts = now();
        assertThat(validator.isValid(ts, sign(ts, ""), null)).isTrue();
    }

    @Test
    @DisplayName("Signature in uppercase is accepted (case-insensitive hex)")
    void uppercaseSignature() throws Exception {
        String ts = now();
        assertThat(validator.isValid(ts, sign(ts, "x").toUpperCase(), "x")).isTrue();
    }

    @Test
    @DisplayName("Tampered body is rejected")
    void tamperedBody() throws Exception {
        String ts = now();
        String signature = sign(ts, "original-body");
        assertThat(validator.isValid(ts, signature, "tampered-body")).isFalse();
    }

    @Test
    @DisplayName("Signature computed with a different secret is rejected")
    void wrongSecret() throws Exception {
        String ts = now();
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec("evil-secret".getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        String foreignSig = HexFormat.of().formatHex(mac.doFinal((ts + "x").getBytes(StandardCharsets.UTF_8)));
        assertThat(validator.isValid(ts, foreignSig, "x")).isFalse();
    }

    @Test
    @DisplayName("Timestamp older than 300 seconds is rejected")
    void expiredTimestamp() throws Exception {
        String oldTs = String.valueOf(Instant.now().getEpochSecond() - 301);
        assertThat(validator.isValid(oldTs, sign(oldTs, "x"), "x")).isFalse();
    }

    @Test
    @DisplayName("Timestamp more than 300 seconds in the future is rejected")
    void futureTimestamp() throws Exception {
        String futureTs = String.valueOf(Instant.now().getEpochSecond() + 400);
        assertThat(validator.isValid(futureTs, sign(futureTs, "x"), "x")).isFalse();
    }

    @Test
    @DisplayName("Non-numeric timestamp is rejected")
    void nonNumericTimestamp() {
        assertThat(validator.isValid("not-a-number", "deadbeef", "x")).isFalse();
    }

    @Test
    @DisplayName("Missing timestamp header is rejected")
    void missingTimestamp() {
        assertThat(validator.isValid(null, "deadbeef", "x")).isFalse();
        assertThat(validator.isValid("", "deadbeef", "x")).isFalse();
        assertThat(validator.isValid("   ", "deadbeef", "x")).isFalse();
    }

    @Test
    @DisplayName("Missing signature header is rejected")
    void missingSignature() {
        String ts = now();
        assertThat(validator.isValid(ts, null, "x")).isFalse();
        assertThat(validator.isValid(ts, "", "x")).isFalse();
        assertThat(validator.isValid(ts, "   ", "x")).isFalse();
    }
}