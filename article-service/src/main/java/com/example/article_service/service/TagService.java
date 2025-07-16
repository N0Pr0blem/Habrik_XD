package com.example.article_service.service;

import com.example.article_service.repo.TagRepo;
import com.example.article_service.model.Tag;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepo tagRepo;

    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    public Set<Tag> parseStringToTag(List<String> stringTags) {

        HashSet<Tag> foundTags = new HashSet<>();
        for (String stringTag : stringTags) {
            foundTags.add(tagRepo.findByName(stringTag).orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.setName(stringTag);
                return tagRepo.save(newTag);
            }));
        } return foundTags;
    }

    public Set<String> parseTagToString(Set<Tag> tags) {
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }
}
