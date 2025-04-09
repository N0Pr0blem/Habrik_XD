package com.example.security_service.controller;

import com.example.security_service.dto.auth.AuthRequestDto;
import com.example.security_service.dto.auth.AuthResponseDto;
import com.example.security_service.dto.auth.RegisterRequestDto;
import com.example.security_service.dto.user.UserResponseDto;
import com.example.security_service.service.AuthService;
import com.example.security_service.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public UserResponseDto register(@RequestBody RegisterRequestDto registerRequestDto){
        return authService.registerUser(registerRequestDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto){
        return authService.authenticateUser(authRequestDto);
    }

}
