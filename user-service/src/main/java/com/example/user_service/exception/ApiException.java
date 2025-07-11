package com.example.user_service.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private final String messageCode;
    private final Object[] args;

    public ApiException(String messageCode, Object... args) {
        this.messageCode = messageCode;
        this.args = args;
    }
}
