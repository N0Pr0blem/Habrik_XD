package com.example.article_service.DTO.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ValidateTokenDto {
    private Boolean isValid;
    private Long id;
    private String role;

    public ValidateTokenDto(Boolean isValid, Long id, String role) {
        this.isValid = isValid;
        this.id = id;
        this.role = role;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
