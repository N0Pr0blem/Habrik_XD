package com.image_service.image_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageFolderType {
    SYSTEM("system"),USER("users");

    String title;
}
