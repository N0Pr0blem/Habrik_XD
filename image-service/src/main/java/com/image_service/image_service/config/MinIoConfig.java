package com.image_service.image_service.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class MinIoConfig {
    @Value("${minio.secret-key}")
    private String secretKey;
    @Value("${minio.access-key}")
    private String accessKey;
    @Value("${minio.endpoint}")
    private String endpoint;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey)
                .build();
    }
}
