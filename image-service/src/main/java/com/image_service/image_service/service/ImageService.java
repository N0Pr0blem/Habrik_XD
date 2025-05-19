package com.image_service.image_service.service;

import com.image_service.image_service.model.ImageFolderType;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ImageService {
    String save(ImageFolderType folder, String subfolder, MultipartFile image) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, IOException;
    byte[] get(String path);
    void delete(String path);
}
