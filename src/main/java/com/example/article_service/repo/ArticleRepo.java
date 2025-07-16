package com.example.article_service.repo;


import com.example.article_service.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Integer> {
    public Optional<Article> getArticleById(Long id);
    @Query("SELECT a FROM Article a ORDER BY a.updatedAt, a.createdAt DESC")
    public Page<Article> getAllArticles(Pageable pageable);
}
