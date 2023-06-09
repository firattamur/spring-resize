package com.firattamur.imageresizerservice.config;

import com.firattamur.imageresizerservice.entity.ImageDynamoDBEntity;
import com.firattamur.imageresizerservice.repository.DatabaseStrategy;
import com.firattamur.imageresizerservice.repository.DynamoDBRepository;
import com.firattamur.imageresizerservice.service.storage.S3StorageStrategy;
import com.firattamur.imageresizerservice.service.storage.StorageStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AppConfig {

    @Bean
    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public DynamoDbClient getDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(getDynamoDbClient())
                .build();
    }

    @Bean
    public StorageStrategy<byte[]> storageStrategy() {
        if (useS3()) {
            return new S3StorageStrategy(getS3Client());
        } else {
            throw new RuntimeException("No storage strategy found");
        }
    }

    @Bean
    public DatabaseStrategy<ImageDynamoDBEntity> databaseStrategy() {
        if (useDynamoDB()) {
            return new DynamoDBRepository<>(getDynamoDbEnhancedClient(), ImageDynamoDBEntity.class);
        } else {
            throw new RuntimeException("No database strategy found");
        }
    }

    @Bean
    public Class<ImageDynamoDBEntity> imageDynamoDBEntityClass() {
        return ImageDynamoDBEntity.class;
    }

    private boolean useS3() {
        return true;
    }

    private boolean useDynamoDB() {
        return true;
    }

}
