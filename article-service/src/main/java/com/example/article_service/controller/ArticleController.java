package com.example.article_service.controller;

import com.example.article_service.DTO.article.ArticlePreviewDto;
import com.example.article_service.DTO.article.ArticleRequestDto;
import com.example.article_service.DTO.article.ArticleResponseDto;
import com.example.article_service.DTO.article.ArticleUpdateDto;
import com.example.article_service.exception.InvalidSlugException;
import com.example.article_service.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<Page<ArticlePreviewDto>> getArticleFeed(@RequestParam(defaultValue = "1") int size,
                                                                  @RequestParam(defaultValue = "1") int page) {
       return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticlesFeed(page, size));
    }

    @PostMapping("/create")
    public ResponseEntity<ArticleResponseDto> saveArticle(@RequestBody ArticleRequestDto articleRequestDto) {
        ArticleResponseDto articleResponseDto;
        articleResponseDto = articleService.createNewArticle(articleRequestDto);
        return ResponseEntity.ok(articleResponseDto);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticleResponseById(id));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ArticleUpdateDto> updateArticle(@PathVariable Long id, @RequestBody ArticleUpdateDto articleUpdateDto) {
        return ResponseEntity.status(HttpStatus.OK).body(articleService.updateArticleByResponse(articleUpdateDto));
    }

    @GetMapping("/{id}/{slug}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long id, @PathVariable String slug) {
        ArticleResponseDto articleResponseDto = articleService.getArticleResponseById(id);
        if (!articleResponseDto.getSlug().equals(slug)) {
            throw new InvalidSlugException("Неверный slug");
        }
        articleService.updateArticleViews(id);
        return ResponseEntity.status(HttpStatus.OK).body(articleResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectToFullSlug(@PathVariable Long id) {
        ArticleResponseDto articleResponseDto = articleService.getArticleResponseById(id);

        String fullUrl = articleResponseDto.getId() + "/" + articleResponseDto.getSlug();
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", fullUrl)
                .build();
    }

}
