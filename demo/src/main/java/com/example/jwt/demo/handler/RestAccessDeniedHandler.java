package com.example.jwt.demo.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger log =
            LoggerFactory.getLogger(RestAccessDeniedHandler.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        log.warn("FORBIDDEN access: User tried to access '{}' from IP {}",
                request.getRequestURI(),
                request.getRemoteAddr());

        Map<String, Object> body = new HashMap<>();
        body.put("status", 403);
        body.put("error", "Forbidden");
        body.put("message", "You do not have permission to access this resource");
        body.put("path", request.getRequestURI());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
