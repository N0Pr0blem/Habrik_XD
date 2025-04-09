package com.example.security_service.service;

import com.example.security_service.model.TokenDetails;

public interface SecurityService {
    TokenDetails authenticate(String username, String password);
}
