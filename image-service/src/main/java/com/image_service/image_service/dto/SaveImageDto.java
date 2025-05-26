package com.image_service.image_service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SaveImageDto {
    private String imageFolderType;
    private String username;
    private MultipartFile multipartFile;
}