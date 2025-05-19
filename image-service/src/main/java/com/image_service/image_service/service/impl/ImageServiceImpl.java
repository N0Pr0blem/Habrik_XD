package com.image_service.image_service.service.impl;

import com.image_service.image_service.model.ImageFolderType;
import com.image_service.image_service.service.ImageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Autowired
    MinioClient minioClient;

    @Value("${minio.bucket-name}")
    String bucketName;

    Logger logger = LogManager.getLogger(ImageServiceImpl.class);


    @Override
    public String save(ImageFolderType folder, String subfolder, MultipartFile image) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, IOException {
        String extension = FileNameUtils.getExtension(image.getOriginalFilename());
        String fileName = UUID.randomUUID()+"."+extension;
        String path = folder+"/"+subfolder+"/"+fileName;

        if (!image.getContentType().startsWith("image/")) {
            throw new RuntimeException("Wrong file type. It should be image.");
        }

        minioClient.putObject(PutObjectArgs.builder()
                .contentType(image.getContentType())
                .bucket(bucketName)
                .object(path)
                .stream(image.getInputStream(), image.getSize(), -1)
                .build());


        return path;
    }

    @Override
    public byte[] get(String path) {
        return new byte[0];
    }

    @Override
    public void delete(String path) {

    }
}
