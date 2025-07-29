package com.example.article_service.DTO.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private Boolean isActive;
}
