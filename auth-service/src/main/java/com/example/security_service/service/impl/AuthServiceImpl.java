package com.example.security_service.service.impl;

import com.example.security_service.dto.auth.AuthRequestDto;
import com.example.security_service.dto.auth.AuthResponseDto;
import com.example.security_service.dto.auth.RegisterRequestDto;
import com.example.security_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public Boolean registerUser(RegisterRequestDto registerRequestDto) {
        return null;
    }

    @Override
    public AuthResponseDto authenticateUser(AuthRequestDto authRequestDto) {
        return null;
    }
}
