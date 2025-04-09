package com.example.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private Boolean isActive;
}
