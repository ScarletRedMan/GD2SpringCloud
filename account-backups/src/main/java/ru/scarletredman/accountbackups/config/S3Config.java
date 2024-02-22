package ru.scarletredman.accountbackups.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class S3Config {

    @Value("${GD2SPRING_MINIO_ENDPOINT:http://localhost:9000}")
    private String endpoint;

    @Value("${GD2SPRING_MINIO_ACCESS_KEY:dev}")
    private String accessKey;

    @Value("${GD2SPRING_MINIO_SECRET_KEY:Qwerty123}")
    private String secretKey;

    @Value("${GD2SPRING_MINIO_BUCKET:account-backups}")
    private String bucketName;

    @Bean
    String bucketName() {
        return bucketName;
    }

    @Bean
    MinioClient minioClient() throws Exception {
        var client = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        var exists = client.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build());

        if (!exists) {
            log.warn("Bucket '%s' does not found. Creating bucket.".formatted(bucketName));

            client.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }

        return client;
    }
}
