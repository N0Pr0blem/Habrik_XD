package com.example.article_service.service;

import com.example.article_service.DTO.article.ArticlePreviewDto;
import com.example.article_service.DTO.article.ArticleRequestDto;
import com.example.article_service.DTO.article.ArticleResponseDto;
import com.example.article_service.DTO.article.ArticleUpdateDto;
import com.example.article_service.DTO.user.ValidateTokenDto;
import com.example.article_service.exception.ArticleNotFoundException;
import com.example.article_service.exception.AuthorizationException;
import com.example.article_service.exception.TokenNotValidException;
import com.example.article_service.repo.ArticleRepo;
import com.example.article_service.model.Article;
import com.example.article_service.model.Tag;
import com.example.article_service.security.TokenProcessor;
import com.example.article_service.util.DiffUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleService {

    @Autowired
    private final DiffUtils diffUtils;

    @Autowired
    private final ArticleRepo articleRepo;

    @Autowired
    private final TagService tagService;

    @Autowired
    private final TokenProcessor tokenProcessor;

    public ArticleService(ArticleRepo articleRepo, TagService tagService, TokenProcessor tokenProcessor, DiffUtils diffUtils) {
        this.articleRepo = articleRepo;
        this.tagService = tagService;
        this.tokenProcessor = tokenProcessor;
        this.diffUtils = diffUtils;
    }

    public Page<ArticlePreviewDto> getArticlesFeed (int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articlePage = articleRepo.findAll(pageable);
        return articlePage.map(this::mapArticleToPreview);
    }

    public ArticleResponseDto createNewArticle (ArticleRequestDto articleRequestDto, HttpServletRequest httpServletRequest) {

        ValidateTokenDto authorizedUser = tokenProcessor.extractDataFromToken(httpServletRequest);
        if (!authorizedUser.getValid()) {
            throw new TokenNotValidException("Invalid token");
        }

        final Date moscowTime = DiffUtils.getCurrentMoscowTime();
        final List<Tag> parsedTags = tagService.parseStringToTag(articleRequestDto.getTags());
        final String articleSlug = DiffUtils.toSlug(articleRequestDto.getTitle());

        Article newArticle = new Article(
                articleSlug,
                articleRequestDto.getTitle(),
                articleRequestDto.getPreviewImageUrl(),
                articleRequestDto.getPreview(),
                articleRequestDto.getContent(),
                parsedTags, moscowTime,
                authorizedUser.getId());

        Article savedArticle =  articleRepo.save(newArticle);
        return diffUtils.mapArticleToResponse(savedArticle);
    }

    public ArticleUpdateDto updateArticleByResponse (ArticleUpdateDto articleUpdateDto, HttpServletRequest httpServletRequest) {

        ValidateTokenDto currentUser = tokenProcessor.extractDataFromToken(httpServletRequest);

        if (!currentUser.getValid()) {
            throw new TokenNotValidException("Token is not valid");
        }

        Article article = articleRepo.getArticleById(articleUpdateDto.getId()).orElseThrow(() ->
                new ArticleNotFoundException("Article not found: " + articleUpdateDto.getId()));

        if (!Objects.equals(currentUser.getId(), article.getAuthorId()) || !currentUser.getRole().equals("ADMIN")) {
            throw new AuthorizationException("Can't confirm current authorized user authorship for this article");
        }

        final List<Tag> parsedTags = tagService.parseStringToTag(articleUpdateDto.getTags());
        articleUpdateDto.setUpdatedAt(DiffUtils.getCurrentMoscowTime());

        article.setTitle(articleUpdateDto.getTitle());
        article.setPreviewImageUrl(articleUpdateDto.getPreviewImageUrl());
        article.setPreview(articleUpdateDto.getPreview());
        article.setContent(articleUpdateDto.getContent());
        article.setUpdatedAt(articleUpdateDto.getUpdatedAt());
        article.setTags(parsedTags);

        articleRepo.save(article);
        return diffUtils.mapArticleToUpdated(article);
    }

    public ArticleResponseDto getArticleResponseById (Long id) {
        Article article = articleRepo.getArticleById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article not found: " + id));
        return diffUtils.mapArticleToResponse(article);
    }

    public ArticleResponseDto getArticleResponseByIdWithValidation(Long id, HttpServletRequest httpServletRequest) {
        Article article = articleRepo.getArticleById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article not found: " + id));
        ValidateTokenDto currentUser = tokenProcessor.extractDataFromToken(httpServletRequest);
        if (Objects.equals(article.getAuthorId(), currentUser.getId()) || currentUser.getRole().equals("ADMIN"))
            return diffUtils.mapArticleToResponse(article);
        else throw new AuthorizationException("Can't confirm current authorized user authorship for this article");
    }

    public void updateArticleViews (Long id) {
        Article article = articleRepo.getArticleById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article not found: " + id));
            article.setViews(article.getViews() + 1);
            articleRepo.save(article);
    }

    private ArticlePreviewDto mapArticleToPreview (Article article) {
        return new ArticlePreviewDto(article.getId(), article.getSlug(), article.getPreview(), article.getViews(), article.getPreviewImageUrl());
    }
}
