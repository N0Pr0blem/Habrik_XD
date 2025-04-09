package com.example.user_service.repository.impl;

import com.example.user_service.model.UserEntity;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.repository.base.SimpleListRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends SimpleListRepositoryImpl<UserEntity,Long> implements UserRepository {

    public UserRepositoryImpl() {
        super(UserEntity::getId);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return findAll().stream()
                .filter(user-> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return findAll().stream()
                .filter(user-> user.getEmail().equals(email))
                .findFirst();
    }
}
