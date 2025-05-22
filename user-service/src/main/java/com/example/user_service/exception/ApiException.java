package com.example.user_service.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    protected String errorCode;

    public ApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
