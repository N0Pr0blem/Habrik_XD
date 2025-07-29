package com.example.article_service.client;

import com.example.article_service.DTO.user.ValidateTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "auth-service", url = "http://localhost:8082/api/v1/security")
public interface SecurityClient {
    @PostMapping("/validate")
    Optional<ValidateTokenDto> validateToken(@RequestParam String token);
}
