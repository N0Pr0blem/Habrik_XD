package com.example.user_service.repository;

import com.example.user_service.model.UserEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(@NotBlank(message = "Username is mandatory") String username);

    Optional<UserEntity> findByEmail(@NotBlank(message = "Email is mandatory") String email);
}
