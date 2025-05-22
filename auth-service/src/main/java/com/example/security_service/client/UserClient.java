package com.example.security_service.client;

import com.example.security_service.dto.user.UserRequestDto;
import com.example.security_service.dto.user.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "user-service", url = "http://user-service:8081/api/v1/users")
public interface UserClient {
    @GetMapping("{id}")
    Optional<UserResponseDto> getUserById(@PathVariable Long id);

    @DeleteMapping("{id}")
    void deleteUserById(@PathVariable Long id);

    @PostMapping(consumes = "application/json")
    UserResponseDto addUser(@RequestBody UserRequestDto userRequestDto);

    @GetMapping()
    List<UserResponseDto> getAllUsers(@RequestParam String name);

}