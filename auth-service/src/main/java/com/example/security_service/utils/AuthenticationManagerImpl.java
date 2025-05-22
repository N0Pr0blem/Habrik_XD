package com.example.security_service.utils;

import com.example.security_service.client.UserClient;
import com.example.security_service.dto.user.UserResponseDto;
import com.example.security_service.exception.AuthException;
import com.example.security_service.model.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final UserClient userClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        Optional<UserResponseDto> userResponseDto = userClient.getUserById(principal.getId());

        if (userResponseDto.isEmpty()) {
            throw new AuthException("No such user with this username", "NO_SUCH_USER_EXCEPTION");
        }
        if (!userResponseDto.get().getIsActive()) {
            throw new AuthException("This user hasn't been activated", "INACTIVE_USER");
        }

        return authentication;
    }
}
