package com.image_service.image_service.controller;

import com.image_service.image_service.dto.SaveImageDto;
import com.image_service.image_service.service.ImageService;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @Operation(description = "Save image")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveImage(
            @RequestPart("imageFolderType") String imageFolderType,
            @RequestPart("username") String username,
            @RequestPart("multipartFile") MultipartFile file
    ) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return imageService.save(imageFolderType, username, file);
    }

}
