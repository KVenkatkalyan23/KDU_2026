package com.jwtAuth.demo.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log =
            LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        log.warn("Unauthorized access attempt to '{}' from IP {}",
                request.getRequestURI(),
                request.getRemoteAddr());

        Map<String, Object> body = new HashMap<>();
        body.put("status", 401);
        body.put("error", "Unauthorized");
        body.put("message", "Invalid or missing authentication token");
        body.put("timestamp", LocalDateTime.now());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
