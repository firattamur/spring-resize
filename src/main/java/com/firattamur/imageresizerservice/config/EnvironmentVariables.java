package com.firattamur.imageresizerservice.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentVariables {

    private static EnvironmentVariables INSTANCE;

    private final String AWS_REGION;
    private final String AWS_SECRET_KEY;
    private final String AWS_ACCESS_KEY;
    private final String AWS_S3_BUCKET_NAME;


    private EnvironmentVariables() {

        Dotenv dotenv = Dotenv.load();

        AWS_REGION = dotenv.get("AWS_REGION");
        AWS_SECRET_KEY = dotenv.get("AWS_SECRET_KEY");
        AWS_ACCESS_KEY = dotenv.get("AWS_ACCESS_KEY");
        AWS_S3_BUCKET_NAME = dotenv.get("AWS_S3_BUCKET_NAME");

    }

    public static EnvironmentVariables getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnvironmentVariables();
        }
        return INSTANCE;
    }

    public String getAwsRegion() {
        return AWS_REGION;
    }

    public String getAwsSecretKey() {
        return AWS_SECRET_KEY;
    }

    public String getAwsAccessKey() {
        return AWS_ACCESS_KEY;
    }

    public String getAwsS3BucketName() {
        return AWS_S3_BUCKET_NAME;
    }

}
