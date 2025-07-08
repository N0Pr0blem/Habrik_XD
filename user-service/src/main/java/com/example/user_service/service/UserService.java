package com.example.user_service.service;

import com.example.user_service.dto.UserFilter;
import com.example.user_service.dto.UserRequestDto;
import com.example.user_service.model.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserEntity getUserById(Long userId);

    List<UserEntity> getAllUsers();

    UserEntity addUser(UserRequestDto userRequestDto);

    void deleteById(Long userId);

    UserEntity getUserByUsername(String name);

    List<UserEntity> getAllUsers(UserFilter filter, Pageable pageable);
}
