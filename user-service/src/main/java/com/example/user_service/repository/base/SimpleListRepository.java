package com.example.user_service.repository.base;

import java.util.List;
import java.util.Optional;

public interface SimpleListRepository<T,I> {
    Optional<T> findById(I i);
    List<T> findAll();
    T save(T t);
    void deleteById(I i);
    boolean existsById(I i);
}
