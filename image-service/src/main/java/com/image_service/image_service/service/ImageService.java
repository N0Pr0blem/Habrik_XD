package com.image_service.image_service.service;

import com.image_service.image_service.model.ImageFolderType;
import io.minio.errors.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ImageService {
    String save(String folder, MultipartFile image, HttpServletRequest httpServletRequest) throws Exception;
    byte[] get(String path);
    void delete(String path);
}
