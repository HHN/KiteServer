package com.hhn.kite2server.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Smoke tests for the security rules - they pin the behavior that was fixed
 * on 2026-11-03 (publicly readable/deletable research data at /data) so it
 * cannot silently regress. If one of these tests fails, the security config
 * has changed - check before deploying.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecuritySmokeTest {

    private static final String ADMIN_USER = "testadmin";
    private static final String ADMIN_PASS = "testpass";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void dataEndpointsRejectAnonymousRequests() throws Exception {
        mockMvc.perform(get("/data")).andExpect(status().isUnauthorized());
        mockMvc.perform(delete("/data")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/data/export")).andExpect(status().isUnauthorized());
    }

    @Test
    void dataEndpointsRejectWrongPassword() throws Exception {
        mockMvc.perform(get("/data").with(httpBasic(ADMIN_USER, "wrong-pass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void dataEndpointsAcceptAdminCredentials() throws Exception {
        mockMvc.perform(get("/data").with(httpBasic(ADMIN_USER, ADMIN_PASS)))
                .andExpect(status().isOk());
        mockMvc.perform(get("/data/export").with(httpBasic(ADMIN_USER, ADMIN_PASS)))
                .andExpect(status().isOk());
    }

    @Test
    void metricsHitsStayPublicForUnityClient() throws Exception {
        mockMvc.perform(post("/metrics/playthroughs/hit"))
                .andExpect(status().isOk());
    }

    @Test
    void metricsAdminEndpointsRequireAuth() throws Exception {
        mockMvc.perform(get("/metrics/playthroughs")).andExpect(status().isUnauthorized());
        mockMvc.perform(post("/metrics/reset-all")).andExpect(status().isUnauthorized());
    }

    @Test
    void aiEndpointRequiresBearerToken() throws Exception {
        mockMvc.perform(post("/ai")).andExpect(status().isUnauthorized());
    }

    @Test
    void authSessionRequiresHmacHeaders() throws Exception {
        mockMvc.perform(post("/api/auth/session")).andExpect(status().isUnauthorized());
    }
}