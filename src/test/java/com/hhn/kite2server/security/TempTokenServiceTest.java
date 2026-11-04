package com.hhn.kite2server.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TempTokenServiceTest {

    @Test
    @DisplayName("Newly created token is valid")
    void createdTokenIsValid() {
        TempTokenService service = new TempTokenService();
        String token = service.createToken();
        assertThat(token).isNotBlank();
        assertThat(service.isValid(token)).isTrue();
    }

    @Test
    @DisplayName("Repeated validation keeps a fresh token valid")
    void repeatedValidation() {
        TempTokenService service = new TempTokenService();
        String token = service.createToken();
        assertThat(service.isValid(token)).isTrue();
        assertThat(service.isValid(token)).isTrue();
    }

    @Test
    @DisplayName("Unknown token is rejected")
    void unknownTokenIsInvalid() {
        TempTokenService service = new TempTokenService();
        assertThat(service.isValid(UUID.randomUUID().toString())).isFalse();
    }

    @Test
    @DisplayName("Null token is rejected")
    void nullTokenIsInvalid() {
        assertThat(new TempTokenService().isValid(null)).isFalse();
    }

    @Test
    @DisplayName("Expired token is rejected and removed from the store")
    void expiredTokenIsRejectedAndCleanedUp() throws Exception {
        TempTokenService service = new TempTokenService();
        String token = service.createToken();

        // Put an already-expired token into the store (test-only, saves us the 1-hour wait)
        Field storeField = TempTokenService.class.getDeclaredField("tokenStore");
        storeField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Instant> store = (Map<String, Instant>) storeField.get(service);
        store.put("expired-token", Instant.now().minusSeconds(1));

        assertThat(service.isValid("expired-token")).isFalse();
        assertThat(store).doesNotContainKey("expired-token");   // cleanup happened
        assertThat(service.isValid("expired-token")).isFalse(); // stays rejected afterwards
        assertThat(store).containsKey(token);                   // fresh token untouched
    }
}