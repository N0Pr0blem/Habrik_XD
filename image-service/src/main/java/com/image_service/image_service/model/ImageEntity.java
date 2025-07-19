package com.image_service.image_service.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {
    private Long id;
    private String path;
    private ImageFolderType type;
    private String username;
    private Long userId;
}
