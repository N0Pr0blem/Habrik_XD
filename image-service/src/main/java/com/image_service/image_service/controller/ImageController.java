package com.image_service.image_service.controller;

import com.image_service.image_service.model.ImageFolderType;
import com.image_service.image_service.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @Operation(description = "Save image")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveImage(
            @RequestPart("imageFolderType") ImageFolderType imageFolderType,
            @RequestPart("multipartFile") MultipartFile file,
            HttpServletRequest httpServletRequest
    ) throws Exception {
        return imageService.save(imageFolderType.name(), file,httpServletRequest);
    }

    @GetMapping()
    @Operation(description = "Get image")
    public byte[] getImage(@RequestParam String path) throws Exception {
        return imageService.get(path);
    }

}
