package com.example.user_service.controller;

import com.example.user_service.dto.UserRequestDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(consumes = "application/json")
    @Operation(description = "Add new user")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userMapper.toDto(userService.addUser(userRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @GetMapping("/{userId}")
    @Operation(description = "Get user by id")
    public ResponseEntity<?> getUserById(@PathVariable(name = "userId") Long userId) {
        UserResponseDto userResponseDto = userMapper.toDto(userService.getUserById(userId));
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping()
    @Operation(description = "Get all users")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String username) {
        if (username != null && !username.isEmpty()) {
            UserResponseDto userResponseDto = userMapper.toDto(userService.getUserByUsername(username));
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
        } else {
            List<UserResponseDto> responseUsers = userMapper.toDtos(userService.getAllUsers());
            return ResponseEntity.status(HttpStatus.OK).body(responseUsers);
        }
    }

    @DeleteMapping("/{userId}")
    @Operation(description = "Delete user by id")
    public ResponseEntity<?> deleteUserById(@PathVariable(name = "userId") Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User with id - " + userId + " successful deleted");
    }
}
