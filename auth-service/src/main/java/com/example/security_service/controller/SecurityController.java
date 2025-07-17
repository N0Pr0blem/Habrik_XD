package com.example.security_service.controller;

import com.example.security_service.dto.security.ValidateTokenDto;
import com.example.security_service.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenDto> isTokenValid(@RequestParam String token) {
        boolean validateResult = securityService.validateToken(token);
        String username = "", role = "";
        if (validateResult) {
            username = securityService.getUsernameFromToken(token);
            role = securityService.getRoleFromToken(token);
        }
        return ResponseEntity.ok(ValidateTokenDto.builder()
                .isValid(validateResult)
                .username(username)
                .role(role)
                .build());
    }
}
