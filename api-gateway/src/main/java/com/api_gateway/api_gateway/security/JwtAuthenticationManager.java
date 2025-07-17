package com.api_gateway.api_gateway.security;

import com.api_gateway.api_gateway.client.SecurityClient;
import com.api_gateway.api_gateway.dto.ValidateTokenDto;
import com.api_gateway.api_gateway.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final SecurityClient securityClient;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .filter(auth -> auth instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap(this::validateToken)
                .onErrorResume(e -> Mono.error(new UnauthorizedException("Token validation failed")));
    }

    private Mono<Authentication> validateToken(String token) {
        return securityClient.validateToken(token)
                .filter(ValidateTokenDto::getIsValid)
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid token")))
                .map(dto -> new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + dto.getRole().toUpperCase()))
                ));
    }
}