package com.example.user_service.handler;

import com.example.user_service.exception.ApiException;
import com.example.user_service.exception.UserAddException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserAddException.class)
    public ResponseEntity<Map<String, String>> handleUserAddException(UserAddException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, String>> handleApiException(ApiException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }

}
