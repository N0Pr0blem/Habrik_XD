package com.image_service.image_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageFolderType {
    SYSTEM,    //image for front
    USER,      //image of user's profile
    ARTICLE;   //image from article
}
