package com.example.user_service.service.impl;

import com.example.user_service.dto.UserRequestDto;
import com.example.user_service.exception.ApiException;
import com.example.user_service.exception.UserAddException;
import com.example.user_service.model.UserEntity;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("No such user", "GETTING_USER_EXCEPTION"));
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity addUser(UserRequestDto userRequestDto) {
        if (userRepository.findByUsername(userRequestDto.getUsername()).isPresent()) {
            throw new UserAddException(String.format("User with username - %s already exist", userRequestDto.getUsername()));
        }
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new UserAddException(String.format("User with email - %s already exist", userRequestDto.getEmail()));
        }
        return userRepository.save(UserEntity.builder()
                .username(userRequestDto.getUsername())
                .password(userRequestDto.getPassword())
                .email(userRequestDto.getEmail())
                .role(userRequestDto.getRole())
                .isActive(true)
                .build());
    }

    @Override
    public void deleteById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ApiException(String.format("User with id - %d not found", userId), "USER_NOT_FOUND");
        }
    }

    @Override
    public UserEntity getUserByUsername(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow(()-> new ApiException("No such user with username "+name+".","NO_SUCH_USER"));
    }
}
