package com.example.article_service.util;

import com.example.article_service.DTO.article.ArticleResponseDto;
import com.example.article_service.DTO.article.ArticleUpdateDto;
import com.example.article_service.model.Article;
import com.example.article_service.service.TagService;
import com.ibm.icu.text.Transliterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DiffUtils {
    @Autowired
    private TagService tagService;

    public static Date getCurrentMoscowTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
        return calendar.getTime();
    }

    public static String toSlug(String input) {
        if (input == null) {
            return "";
        }
        Transliterator toLatinTrans = Transliterator.getInstance("Cyrillic-Latin");
        final String transliterated = toLatinTrans.transliterate(input);
        String lowerCased = transliterated.toLowerCase(Locale.ROOT);
        String cleaned = lowerCased.replaceAll("[^a-z0-9\\s-]", "");
        return cleaned.trim().replaceAll("[\\s-]+", "-");
    }

    public ArticleUpdateDto mapArticleToUpdated (Article article) {
        final List<String> stringTags = tagService.parseTagToString(article.getTags());
        final String articleHtmlContent = MarkdownUtils.toHtml(article.getContent());

        return new ArticleUpdateDto(article.getId(), article.getAuthorId(), article.getTitle(), article.getPreview(), article.getPreviewImageUrl(), articleHtmlContent,
                stringTags, article.getUpdatedAt());
    }

    public ArticleResponseDto mapArticleToResponse (Article article) {
        final List<String> stringTags = tagService.parseTagToString(article.getTags());
        final String articleHtmlContent = MarkdownUtils.toHtml(article.getContent());

        return new ArticleResponseDto(article.getId(), article.getSlug(), article.getTitle(), article.getPreview(), article.getPreviewImageUrl(), articleHtmlContent,
                stringTags, article.getAuthorId(), article.getCreatedAt(), article.getUpdatedAt());
    }
}
