package com.api_gateway.api_gateway.client;

import com.api_gateway.api_gateway.dto.ValidateTokenDto;
import com.api_gateway.api_gateway.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SecurityClient {

    private final WebClient webClient;

    public SecurityClient(@Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }

    public Mono<ValidateTokenDto> validateToken(String token) {
        return webClient.post()
                .uri("/api/v1/security/validate?token={token}", token)
                .retrieve()
                .bodyToMono(ValidateTokenDto.class)
                .onErrorResume(e -> Mono.error(new ApiException("Exception during parse response from auth service or connection trouble", "CONNECTION_TO_AUTH_SERVICE_FAIL")));
    }
}