package com.example.article_service.repo;


import com.example.article_service.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
    public Optional<Article> getArticleById(Long id);
}
