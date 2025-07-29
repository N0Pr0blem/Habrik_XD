package com.example.article_service.DTO.article;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ArticlePreviewDto {
    private Long articleId;
    private String articleSlug;
    private String preview;
    private Integer views;
    private String imageUrl;

    public ArticlePreviewDto(Long articleId, String articleSlug, String preview, Integer views, String imageUrl) {
        this.articleId = articleId;
        this.articleSlug = articleSlug;
        this.preview = preview;
        this.views = views;
        this.imageUrl = imageUrl;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getArticleSlug() {
        return articleSlug;
    }

    public void setArticleSlug(String articleSlug) {
        this.articleSlug = articleSlug;
    }
}
