package com.firattamur.imageresizerservice.service.storage;

import com.firattamur.imageresizerservice.config.EnvironmentVariables;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.Optional;


public class S3StorageStrategy implements StorageStrategy<byte[]> {

    private final S3Client s3Client;
    private final String bucketName = EnvironmentVariables.getInstance().getAwsS3BucketName();

    public S3StorageStrategy(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadImage(String key, byte[] imageBytes, String format) throws Exception {

        SdkBytes sdkBytes = SdkBytes.fromByteArray(imageBytes);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(String.format("image/%s", format))
                .contentLength((long) imageBytes.length)
                .build();

        PutObjectResponse putObjectResponse = this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));

        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);

    }

    @Override
    public Optional<byte[]> download(String key) {

        try {

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            ResponseBytes<GetObjectResponse> responseBytes = this.s3Client.getObjectAsBytes(getObjectRequest);

            return Optional.of(responseBytes.asByteArray());

        } catch (Exception e) {

            return Optional.empty();

        }

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
