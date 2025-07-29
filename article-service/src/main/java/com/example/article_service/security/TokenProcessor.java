package com.example.article_service.security;

import com.example.article_service.DTO.user.ValidateTokenDto;
import com.example.article_service.client.SecurityClient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class TokenProcessor {
    private final SecurityClient securityClient;

    public TokenProcessor(SecurityClient securityClient) {
        this.securityClient = securityClient;
    }

    public ValidateTokenDto extractDataFromToken (HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) {
            throw new IllegalArgumentException("Authorization header is missing");
        }
        if (!authHeader.startsWith("Bearer")) {
            throw new IllegalArgumentException("Invalid token format (expected 'Bearer <token>'");
        }
        String token = authHeader.substring(7);
        return securityClient.validateToken(token).orElseThrow(() -> new RuntimeException("Connection with auth-service if failed"));
    }
}
