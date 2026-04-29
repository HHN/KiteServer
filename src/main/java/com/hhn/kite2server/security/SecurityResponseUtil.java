package com.hhn.kite2server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhn.kite2server.response.Response;
import com.hhn.kite2server.response.ResultCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Utility class for writing consistent JSON error responses in security filters and entry points.
 */
public class SecurityResponseUtil {

    private SecurityResponseUtil() {
        // Private constructor to prevent instantiation
    }

    public static void writeErrorResponse(HttpServletResponse response, ObjectMapper objectMapper) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Response customResponse = new Response();
        customResponse.setResultText(ResultCode.NOT_AUTHORIZED.toString());
        customResponse.setResultCode(ResultCode.NOT_AUTHORIZED.toInt());

        objectMapper.writeValue(response.getOutputStream(), customResponse);
    }
}