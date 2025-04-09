package com.example.security_service.service.impl;

import com.example.security_service.client.UserClient;
import com.example.security_service.dto.auth.AuthRequestDto;
import com.example.security_service.dto.auth.AuthResponseDto;
import com.example.security_service.dto.auth.RegisterRequestDto;
import com.example.security_service.dto.user.UserRequestDto;
import com.example.security_service.dto.user.UserResponseDto;
import com.example.security_service.model.TokenDetails;
import com.example.security_service.service.AuthService;
import com.example.security_service.service.SecurityService;
import com.example.security_service.utils.Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserClient userClient;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto registerUser(RegisterRequestDto registerRequestDto) {
        return userClient.addUser(UserRequestDto.builder()
                .email(registerRequestDto.getEmail())
                .role("USER")
                .username(registerRequestDto.getUsername())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .build());
    }

    @Override
    public AuthResponseDto authenticateUser(AuthRequestDto authRequestDto) {
        TokenDetails tokenDetails = securityService.authenticate(authRequestDto.getUsername(), authRequestDto.getPassword());
        return AuthResponseDto.builder()
                .userId(tokenDetails.getId())
                .token(tokenDetails.getToken())
                .expiresAt(tokenDetails.getExpiredAt())
                .issuedAt(tokenDetails.getIssuedAt())
                .role(tokenDetails.getRole())
                .build();
    }
}
