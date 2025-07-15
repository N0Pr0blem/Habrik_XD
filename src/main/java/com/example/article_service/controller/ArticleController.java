package com.example.article_service.controller;

import com.example.article_service.DTO.ArticleRequest;
import com.example.article_service.DTO.ArticleResponse;
import com.example.article_service.exception.InvalidSlugException;
import com.example.article_service.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create")
    public ArticleResponse saveArticle(@RequestBody ArticleRequest articleRequest) {
        ArticleResponse articleResponse;
        articleResponse = articleService.createNewArticle(articleRequest);
        return articleResponse;
    }

    @GetMapping("/{id}/{slug}")
    public ArticleResponse getArticle(@PathVariable Long id, @PathVariable String slug) {
        ArticleResponse articleResponse = articleService.getArticleResponseById(id);

        if (!articleResponse.getSlug().equals(slug)) {
            throw new InvalidSlugException("Неверный slug");
        }
        return articleResponse;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectToFullSlug(@PathVariable Long id) {
        ArticleResponse articleResponse = articleService.getArticleResponseById(id);

        String fullUrl = "/articles/" + articleResponse.getId() + "/" + articleResponse.getSlug();
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", fullUrl)
                .build();
    }

}
