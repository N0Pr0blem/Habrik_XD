package com.example.article_service.service;

import com.example.article_service.DTO.ArticlePreview;
import com.example.article_service.DTO.ArticleRequest;
import com.example.article_service.DTO.ArticleResponse;
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
import java.util.Set;

@Service
public class ArticleService {
    @Autowired
    private final ArticleRepo articleRepo;

    @Autowired
    private TagService tagService;

    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public Page<ArticlePreview> getArticlesFeed (int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articlePage = articleRepo.findAll(pageable);
        return articlePage.map(this::mapArticleToPreview);
    }

    public ArticleResponse createNewArticle (ArticleRequest articleRequest) {

        final Date moscowTime = DiffUtils.getCurrentMoscowTime();
        final Set<Tag> parsedTags = tagService.parseStringToTag(articleRequest.getTags());
        final String articleSlug = DiffUtils.toSlug(articleRequest.getTitle());

        Article newArticle = new Article(
                articleSlug,
                articleRequest.getTitle(),
                articleRequest.getPreviewImageUrl(),
                articleRequest.getPreview(),
                articleRequest.getContent(),
                parsedTags,
                moscowTime, articleRequest.getAuthorId());

        Article savedArticle =  articleRepo.save(newArticle);
        return mapArticleToResponse(savedArticle);
    }

    public ArticleResponse getArticleResponseById (Long id) {
        Article article = articleRepo.getArticleById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article not found: " + id));
        article.setViews(article.getViews() + 1);
        articleRepo.save(article);
        return mapArticleToResponse(article);
    }

    private ArticleResponse mapArticleToResponse (Article article) {
        final Set<String> stringTags = tagService.parseTagToString(article.getTags());
        final String articleHtmlContent = MarkdownUtils.toHtml(article.getContent());

        return new ArticleResponse(article.getId(), article.getSlug(), article.getTitle(), article.getPreview(), articleHtmlContent,
                stringTags, article.getAuthorId(), article.getCreatedAt(), article.getUpdatedAt());
    }

    private ArticlePreview mapArticleToPreview (Article article) {
        return new ArticlePreview(article.getId(), article.getPreview(), article.getViews(), article.getPreviewImageUrl());
    }
}
