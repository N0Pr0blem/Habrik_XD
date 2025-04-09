package com.example.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAddException extends ApiException{
    public UserAddException(String message) {
        super(message, "ADD_USER_EXCEPTION");
    }
}
