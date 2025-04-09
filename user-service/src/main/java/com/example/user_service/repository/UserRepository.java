package com.example.user_service.repository;

import com.example.user_service.model.UserEntity;
import com.example.user_service.repository.base.SimpleListRepository;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public interface UserRepository extends SimpleListRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(@NotBlank(message = "Username is mandatory") String username);

    Optional<UserEntity> findByEmail(@NotBlank(message = "Email is mandatory") String email);
}
