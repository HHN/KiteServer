package com.hhn.kite2server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HexFormat;

import static org.assertj.core.api.Assertions.assertThat;

class TokenFilterTest {

    private static final String SECRET = "test-secret";
    private static final String AUTH_PATH = "/api/auth/session";
    private static final String AI_PATH = "/ai";

    private TempTokenService tokenService;
    private TokenFilter filter;

    @BeforeEach
    void setUp() {
        tokenService = new TempTokenService();
        filter = new TokenFilter(tokenService, new HmacRequestValidator(SECRET), new ObjectMapper());
    }

    private String sign(String timestamp, String body) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return HexFormat.of().formatHex(mac.doFinal((timestamp + body).getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    @DisplayName("Valid HMAC on /api/auth passes through with a wrapped request")
    void authPathWithValidHmac() throws Exception {
        String ts = String.valueOf(Instant.now().getEpochSecond());
        String body = "{\"vn\":\"123\"}";
        MockHttpServletRequest request = new MockHttpServletRequest("POST", AUTH_PATH);
        request.setContent(body.getBytes(StandardCharsets.UTF_8));
        request.addHeader("X-Kite-Timestamp", ts);
        request.addHeader("X-Kite-Signature", sign(ts, body));

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(200);
        // The body must remain readable downstream (that's the wrapper's whole purpose)
        assertThat(chain.getRequest()).isInstanceOf(CachedBodyHttpServletRequest.class);
    }

    @Test
    @DisplayName("Invalid HMAC on /api/auth is rejected with 401")
    void authPathWithInvalidHmac() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", AUTH_PATH);
        request.setContent("{}".getBytes(StandardCharsets.UTF_8));
        request.addHeader("X-Kite-Timestamp", String.valueOf(Instant.now().getEpochSecond()));
        request.addHeader("X-Kite-Signature", "deadbeef");

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getContentAsString()).contains("NOT_AUTHORIZED");
        assertThat(chain.getRequest()).isNull(); // controller never reached
    }

    @Test
    @DisplayName("/api/auth without HMAC headers is rejected with 401")
    void authPathWithoutHeaders() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", AUTH_PATH);
        request.setContent("{}".getBytes(StandardCharsets.UTF_8));

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(chain.getRequest()).isNull();
    }

    @Test
    @DisplayName("Valid Bearer token on /ai passes through")
    void aiPathWithValidToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", AI_PATH);
        request.addHeader("Authorization", "Bearer " + tokenService.createToken());

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(chain.getRequest()).isNotNull();
    }

    @Test
    @DisplayName("Unknown token on /ai is rejected with 401")
    void aiPathWithUnknownToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", AI_PATH);
        request.addHeader("Authorization", "Bearer not-a-real-token");

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(chain.getRequest()).isNull();
    }

    @Test
    @DisplayName("/ai without Authorization header is rejected with 401")
    void aiPathWithoutAuthHeader() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", AI_PATH);

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(chain.getRequest()).isNull();
    }

    @Test
    @DisplayName("/ai with a non-Bearer Authorization header is rejected with 401")
    void aiPathWithWrongScheme() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", AI_PATH);
        request.addHeader("Authorization", "Basic dXNlcjpwYXNz");

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(chain.getRequest()).isNull();
    }

    @Test
    @DisplayName("Other paths pass through without any token check")
    void otherPathsPassThrough() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/data");

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(chain.getRequest()).isNotNull();
    }
}