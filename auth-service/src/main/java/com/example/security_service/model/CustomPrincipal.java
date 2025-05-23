package com.example.security_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

@Data
@AllArgsConstructor
public class CustomPrincipal implements Principal {
    private Long id;
    private String name;
}
