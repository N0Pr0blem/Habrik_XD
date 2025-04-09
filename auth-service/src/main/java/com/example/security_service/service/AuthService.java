package com.example.security_service.service;

import com.example.security_service.dto.auth.AuthRequestDto;
import com.example.security_service.dto.auth.AuthResponseDto;
import com.example.security_service.dto.auth.RegisterRequestDto;

public interface AuthService {
    Boolean registerUser(RegisterRequestDto registerRequestDto);
    AuthResponseDto authenticateUser(AuthRequestDto authRequestDto);
}

