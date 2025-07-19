package com.image_service.image_service.service.impl;

import com.image_service.image_service.model.ImageFolderType;
import com.image_service.image_service.service.ImageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Autowired
    MinioClient minioClient;

    @Value("${minio.bucket-name}")
    String bucketName;

    Logger logger = LogManager.getLogger(ImageServiceImpl.class);


    @Override
    public String save(String folder, String subfolder, MultipartFile image) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, IOException {
        logger.info("---Request to upload image---");
        String extension = FileNameUtils.getExtension(image.getOriginalFilename());
        logger.info("- extension: " + extension);
        String fileName = UUID.randomUUID() + "." + extension;
        logger.info("- filename: " + fileName);
        String path = folder + "/" + subfolder + "/" + fileName;
        logger.info("- path: " + path);

        if (!image.getContentType().startsWith("image/")) {
            logger.error("---Error--- ");
            throw new RuntimeException("Wrong file type. It should be image.");
        }

        minioClient.putObject(PutObjectArgs.builder()
                .contentType(image.getContentType())
                .bucket(bucketName)
                .object(path)
                .stream(image.getInputStream(), image.getSize(), -1)
                .build());

        logger.info("---Success---: " + path);
        return path;
    }

    @SneakyThrows
    @Override
    public byte[] get(String path) {
        try(InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(path)
                .build())){

            return stream.readAllBytes();
        }
    }

    @Override
    public void delete(String path) {

    }
}
