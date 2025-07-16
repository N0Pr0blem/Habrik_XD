package com.example.article_service.repo;

import com.example.article_service.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
}
