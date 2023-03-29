package com.firattamur.imageresizerservice.config;

import software.amazon.awssdk.services.s3.S3Client;

public class DependencyFactory {

    private DependencyFactory() {
    }

    public static DependencyFactory getInstance() {
        return new DependencyFactory();
    }

    public S3Client getS3Client() {
        return S3Client.builder()
                .build();
    }

}
