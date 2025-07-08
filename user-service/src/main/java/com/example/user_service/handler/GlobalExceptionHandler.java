package com.example.user_service.handler;

import com.example.user_service.exception.ApiException;
import com.example.user_service.exception.UserAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserAddException.class)
    public ResponseEntity<String> handleUserAddException(UserAddException ex, Locale locale) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(messageSource.getMessage(ex.getMessageCode(), ex.getArgs(), locale));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleApiException(ApiException ex, Locale locale) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(messageSource.getMessage(ex.getMessageCode(), ex.getArgs(), locale));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }

}
