package com.firattamur.imageresizerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class AppConfig {

    @Bean
    public DependencyFactory dependencyFactory() {
        return DependencyFactory.getInstance();
    }

    @Bean
    public ImageStorageConfiguration imageStorageConfiguration() {
        return new ImageStorageConfiguration();
    }

    @Bean
    public DynamoDbClient getDynamoDbClient() {

        AwsCredentialsProvider awsCredentialsProvider = DefaultCredentialsProvider.builder()
                .profileName("")
                .build();

        return DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(getDynamoDbClient())
                .build();
    }

}
