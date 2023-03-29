package com.firattamur.imageresizerservice.config;

import com.firattamur.imageresizerservice.service.storage.ImageStorageHandler;
import com.firattamur.imageresizerservice.service.storage.S3StorageStrategy;
import com.firattamur.imageresizerservice.service.storage.StorageStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageStorageConfiguration {

    @Bean
    public StorageStrategy storageStrategy() {

        if (useS3()) {
            return new S3StorageStrategy();
        } else {
            throw new RuntimeException("No storage strategy found");
        }

    }

    @Bean
    public ImageStorageHandler imageStorageHandler(StorageStrategy storageStrategy) {
        return new ImageStorageHandler(storageStrategy);
    }

    private boolean useS3() {
        return true;
    }

}
