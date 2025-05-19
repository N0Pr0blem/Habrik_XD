package com.image_service.image_service.component;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class MinioInitializer implements ApplicationRunner {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;
    @Value("${minio.region}")
    private String region;

    private Logger logger = LogManager.getLogger(MinioInitializer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .region(region)
                    .build());
            if (!found) {
                logger.warn("Bucket " + bucketName + "wasn't found");
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .region(region)
                        .build());
                logger.info("Bucket " + bucketName + " successfully created");
            } else logger.info("Bucket" + bucketName + " exist");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Error initializing MinIO bucket", e);
        }
    }
}
