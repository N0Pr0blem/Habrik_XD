package com.example.article_service.service;

import com.example.article_service.DTO.article.ArticlePreviewDto;
import com.example.article_service.DTO.article.ArticleRequestDto;
import com.example.article_service.DTO.article.ArticleResponseDto;
import com.example.article_service.DTO.article.ArticleUpdateDto;
import com.example.article_service.exception.ArticleNotFoundException;
import com.example.article_service.repo.ArticleRepo;
import com.example.article_service.model.Article;
import com.example.article_service.model.Tag;
import com.example.article_service.util.DiffUtils;
import com.example.article_service.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private final ArticleRepo articleRepo;

    @Autowired
    private TagService tagService;

    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public Page<ArticlePreviewDto> getArticlesFeed (int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articlePage = articleRepo.findAll(pageable);
        return articlePage.map(this::mapArticleToPreview);
    }

    public ArticleResponseDto createNewArticle (ArticleRequestDto articleRequestDto) {
        final Date moscowTime = DiffUtils.getCurrentMoscowTime();
        final List<Tag> parsedTags = tagService.parseStringToTag(articleRequestDto.getTags());
        final String articleSlug = DiffUtils.toSlug(articleRequestDto.getTitle());

        Article newArticle = new Article(
                articleSlug,
                articleRequestDto.getTitle(),
                articleRequestDto.getPreviewImageUrl(),
                articleRequestDto.getPreview(),
                articleRequestDto.getContent(),
                parsedTags,
                moscowTime, articleRequestDto.getAuthorId());

        Article savedArticle =  articleRepo.save(newArticle);
        return mapArticleToResponse(savedArticle);
    }

    public ArticleUpdateDto updateArticleByResponse (ArticleUpdateDto articleUpdateDto) {
        Article article = articleRepo.getArticleById(articleUpdateDto.getId()).orElseThrow(() ->
                new ArticleNotFoundException("Article not found: " + articleUpdateDto.getId()));
        final List<Tag> parsedTags = tagService.parseStringToTag(articleUpdateDto.getTags());
        articleUpdateDto.setUpdatedAt(DiffUtils.getCurrentMoscowTime());

        article.setTitle(articleUpdateDto.getTitle());
        article.setPreviewImageUrl(articleUpdateDto.getPreviewImageUrl());
        article.setPreview(articleUpdateDto.getPreview());
        article.setContent(articleUpdateDto.getContent());
        article.setUpdatedAt(articleUpdateDto.getUpdatedAt());
        article.setTags(parsedTags);

        articleRepo.save(article);
        return mapArticleToUpdated(article);
    }

    public ArticleResponseDto getArticleResponseById (Long id) {
        Article article = articleRepo.getArticleById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article not found: " + id));
        return mapArticleToResponse(article);
    }

    public void updateArticleViews (Long id) {
        Article article = articleRepo.getArticleById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article not found: " + id));
            article.setViews(article.getViews() + 1);
            articleRepo.save(article);
    }

    private ArticleUpdateDto mapArticleToUpdated (Article article) {
        final List<String> stringTags = tagService.parseTagToString(article.getTags());
        final String articleHtmlContent = MarkdownUtils.toHtml(article.getContent());

        return new ArticleUpdateDto(article.getId(), article.getTitle(), article.getPreview(), article.getPreviewImageUrl(), articleHtmlContent,
                stringTags, article.getUpdatedAt());
    }

    private ArticleResponseDto mapArticleToResponse (Article article) {
        final List<String> stringTags = tagService.parseTagToString(article.getTags());
        final String articleHtmlContent = MarkdownUtils.toHtml(article.getContent());

        return new ArticleResponseDto(article.getId(), article.getSlug(), article.getTitle(), article.getPreview(), article.getPreviewImageUrl(), articleHtmlContent,
                stringTags, article.getAuthorId(), article.getCreatedAt(), article.getUpdatedAt());
    }

    private ArticlePreviewDto mapArticleToPreview (Article article) {
        return new ArticlePreviewDto(article.getId(), article.getSlug(), article.getPreview(), article.getViews(), article.getPreviewImageUrl());
    }
}
