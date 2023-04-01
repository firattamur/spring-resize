package com.firattamur.imageresizerservice.service.storage;

import com.firattamur.imageresizerservice.config.EnvironmentVariables;
import com.firattamur.imageresizerservice.helper.ImageResizerHelpers;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.awt.image.BufferedImage;


public class S3StorageStrategy implements StorageStrategy<BufferedImage> {

    private final S3Client s3Client;
    private final String bucketName = EnvironmentVariables.AWS_S3_BUCKET_NAME;

    public S3StorageStrategy(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadImage(String key, BufferedImage image) throws Exception {

        byte[] imageBytes = ImageResizerHelpers.convertImageToByteArray(image, "jpeg");
        SdkBytes sdkBytes = SdkBytes.fromByteArray(imageBytes);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(String.format("image/%s", "jpeg"))
                .contentLength((long) imageBytes.length)
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));

        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);

    }

    @Override
    public BufferedImage download(String key) {
        return null;
    }

    @Override
    public Boolean delete(String key) {
        return null;
    }

    @Override
    public Boolean exists(String key) {
        return null;
    }

}
