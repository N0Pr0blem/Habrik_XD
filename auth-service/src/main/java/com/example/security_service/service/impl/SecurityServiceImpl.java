package com.example.security_service.service.impl;

import com.example.security_service.client.UserClient;
import com.example.security_service.dto.user.UserResponseDto;
import com.example.security_service.exception.AuthException;
import com.example.security_service.model.TokenDetails;
import com.example.security_service.service.SecurityService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expirationInSeconds;
    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    public TokenDetails authenticate(String username, String password) {
        UserResponseDto userResponseDto = userClient.getAllUsers(username).getFirst();
        if(!userResponseDto.getIsActive()){
            throw new AuthException("Account disabled","ACCOUNT_DISABLED");
        }
        if(!userResponseDto.getPassword().equals(passwordEncoder.encode(password))){
            throw new AuthException("Invalid password","INVALID_PASSWORD");
        }

        return generateToken();
    }

    private TokenDetails generateToken(UserResponseDto userResponseDto){
        Map<String,Object> claims = new HashMap<>() {{
            put("role",userResponseDto.getRole());
            put("username",userResponseDto.getUsername());
        }};

        return generateToken(claims,userResponseDto.getId().toString());
    }

    private TokenDetails generateToken(Map<String, Object> claims, String subject) {
        long expirationTimeInMillis = expirationInSeconds*1000L;
        Date expirationDate = new Date(new Date().getTime()+expirationTimeInMillis);

        return generateToken(expirationDate,claims,subject);
    }

    private TokenDetails generateToken(Date expirationDate, Map<String, Object> claims, String subject) {
        Date createdDate = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();

        return new TokenDetails().toBuilder()
                .token(token)
                .expiredAt(expirationDate)
                .issuedAt(createdDate)
                .build();
    }
}
