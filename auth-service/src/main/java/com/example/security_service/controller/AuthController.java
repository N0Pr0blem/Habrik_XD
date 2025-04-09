package com.example.security_service.controller;

import com.example.security_service.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final SecurityService securityService;
}
