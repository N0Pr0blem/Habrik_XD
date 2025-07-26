package com.example.article_service.DTO.article;

import java.util.Date;
import java.util.List;

public class ArticleUpdateDto {
    private Long id;
    private String title;
    private String preview;
    private String previewImageUrl;
    private String content;
    private List<String> tags;
    private Date updatedAt;

    public ArticleUpdateDto(Long id, String title, String preview, String previewImageUrl, String content, List<String> tags, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.preview = preview;
        this.previewImageUrl = previewImageUrl;
        this.content = content;
        this.tags = tags;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
