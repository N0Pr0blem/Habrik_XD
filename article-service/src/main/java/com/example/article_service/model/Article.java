package com.example.article_service.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity()
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    private String title;
    private String previewImageUrl;
    private String preview;
    private String content;
    private Integer views;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "article_tags", joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
    private Date createdAt;
    @Nullable
    private Date updatedAt;
    private Long authorId;

    public Article() {
    }

    public Article(String slug, String title, String previewImageUrl, String preview, String content, List<Tag> tags, Date createdAt, Long authorId) {
        this.slug = slug;
        this.title = title;
        this.previewImageUrl = previewImageUrl;
        this.preview = preview;
        this.content = content;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = null;
        this.authorId = authorId;
        this.views = 0;
    }

    public Article(Long id, String slug, String title, String previewImageUrl, String preview, String content, Integer views, List<Tag> tags, Date createdAt, @Nullable Date updatedAt, Long authorId) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.previewImageUrl = previewImageUrl;
        this.preview = preview;
        this.content = content;
        this.views = views;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
