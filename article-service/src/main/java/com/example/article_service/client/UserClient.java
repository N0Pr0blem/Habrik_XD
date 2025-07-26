package com.example.article_service.client;

import com.example.article_service.DTO.user.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-service", url = "http://user-service:8081/api/v1/users")
public interface UserClient {
    @GetMapping("{id}")
    Optional<UserResponseDto> getUserById (@PathVariable Long id);
}
