package com.image_service.image_service.client;

import com.image_service.image_service.dto.ValidateTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "auth-service", url = "http://auth-service:8082/api/v1/security/validate")
public interface SecurityClient {

    @GetMapping()
    Optional<ValidateTokenDto> validateToken(@RequestParam String token);
}