package com.firattamur.imageresizerservice.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentVariables {

    public static final String AWS_REGION;
    public static final String AWS_SECRET_KEY;
    public static final String AWS_ACCESS_KEY;
    public static final String AWS_S3_BUCKET_NAME;

    static {

        Dotenv dotenv = Dotenv.load();

        AWS_REGION = dotenv.get("AWS_REGION");
        AWS_SECRET_KEY = dotenv.get("AWS_SECRET_KEY");
        AWS_ACCESS_KEY = dotenv.get("AWS_ACCESS_KEY");
        AWS_S3_BUCKET_NAME = dotenv.get("AWS_S3_BUCKET_NAME");

    }

}
