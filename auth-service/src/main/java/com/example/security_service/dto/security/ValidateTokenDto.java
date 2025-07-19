package com.example.security_service.dto.security;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(toBuilder = true)
public class ValidateTokenDto {
    private Boolean isValid;
    private Long id;
    private String username;
    private String role;
}
