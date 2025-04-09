package com.example.security_service.service;

import com.example.security_service.dto.auth.AuthRequestDto;
import com.example.security_service.dto.auth.AuthResponseDto;
import com.example.security_service.dto.auth.RegisterRequestDto;
import com.example.security_service.dto.user.UserResponseDto;

public interface AuthService {
    UserResponseDto registerUser(RegisterRequestDto registerRequestDto);
    AuthResponseDto authenticateUser(AuthRequestDto authRequestDto);
}

