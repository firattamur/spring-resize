package com.firattamur.imageresizerservice.service;

import com.firattamur.imageresizerservice.dto.ResizeImageRequest;
import com.firattamur.imageresizerservice.dto.ResizeImageResponse;
import com.firattamur.imageresizerservice.entity.ImageDynamoDBEntity;
import com.firattamur.imageresizerservice.exception.InvalidImageFormatException;
import com.firattamur.imageresizerservice.helper.image.converter.ImageConverter;
import com.firattamur.imageresizerservice.helper.image.resizer.ImageResizer;
import com.firattamur.imageresizerservice.repository.DynamoDBRepository;
import com.firattamur.imageresizerservice.service.storage.StorageStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.UUID;

@Service
public class ImageResizerService {

    private final ImageResizer imageResizer;
    private final ImageConverter<BufferedImage> imageConverter;
    private final StorageStrategy<byte[]> storageHandler;
    private final DynamoDBRepository<ImageDynamoDBEntity> dynamoDatabaseStrategy;

    @Autowired
    public ImageResizerService(ImageResizer imageResizer,
                               ImageConverter<BufferedImage> imageConverter,
                               StorageStrategy<byte[]> storageHandler,
                               DynamoDBRepository<ImageDynamoDBEntity> dynamoDatabaseStrategy) {
        this.imageResizer = imageResizer;
        this.imageConverter = imageConverter;
        this.storageHandler = storageHandler;
        this.dynamoDatabaseStrategy = dynamoDatabaseStrategy;
    }

    /**
     * Resizes the image with the given width and height.
     * Uploads the resized image to s3 bucket.
     * Store metadata of image in DynamoDB.
     * Returns the url of resized and original image.
     *
     * @param resizeImageRequest is the request body
     * @return ResponseEntity<ResizeImageResponse> is the response body
     */
    public ResizeImageResponse resizeImage(ResizeImageRequest resizeImageRequest) throws Exception {

        try {

            // Download original image from s3 bucket
            byte[] originalImage = this.storageHandler.download(resizeImageRequest.getImageUrl())
                    .orElseThrow(() -> new InvalidImageFormatException("Invalid image format in request body."));

            // Get dynamoDB entity
            ImageDynamoDBEntity imageDynamoDBEntity = this.dynamoDatabaseStrategy.getRecordById(resizeImageRequest.getImageUrl())
                    .orElseThrow(() -> new InvalidImageFormatException("Invalid image format in request body."));

            // Resize the image
            byte[] resizedImage = this.imageResizer.resizeImage(originalImage, imageDynamoDBEntity.getFileType(), resizeImageRequest.getWidth(), resizeImageRequest.getHeight(), resizeImageRequest.isAspectRatio())
                    .orElseThrow(() -> new InvalidImageFormatException("Invalid image format in request body."));

            // upload resized image to s3 bucket
            String resizedImageUrl = this.storageHandler.uploadImage(UUID.randomUUID().toString(), resizedImage);

            // update dynamoDB entity
            imageDynamoDBEntity.setDimensionsResizedImage(resizeImageRequest.getWidth() + "x" + resizeImageRequest.getHeight());
            imageDynamoDBEntity.setCreatedAt((int) System.currentTimeMillis());
            imageDynamoDBEntity.setUrlResizedImage(resizedImageUrl);

            // save dynamoDB entity
            this.dynamoDatabaseStrategy.updateRecord(imageDynamoDBEntity);

            return new ResizeImageResponse(resizeImageRequest.getImageUrl(), resizedImageUrl);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    /**
     * Uploads the image to s3 bucket.
     * Store metadata of image in DynamoDB.
     * Returns the url of original image.
     *
     * @param image is the image file
     * @return ResponseEntity<ResizeImageResponse>
     */
    public ResizeImageResponse uploadImage(MultipartFile image) throws Exception {

        try {

            // Generate unique key for image
            String imageKey = this.generateUniqueKey();

            // Upload the image to s3 bucket
            String imageUrl = this.storageHandler.uploadImage(imageKey, image.getBytes());

            // Store metadata of image in DynamoDB
            ImageDynamoDBEntity imageDynamoDBEntity = ImageDynamoDBEntity.builder()
                    .id(imageKey)
                    .urlOriginalImage(imageUrl)
                    .fileType(image.getContentType())
                    .build();

            this.dynamoDatabaseStrategy.createRecord(imageDynamoDBEntity);

            // Return the url of original image
            return new ResizeImageResponse(null, imageUrl);

        } catch (Exception e) {

            throw new Exception("Error occurred while uploading image to s3 bucket.", e);

        }

    }

    /**
     * This method generates a unique key for the image.
     *
     * @return: The unique key.
     */
    private String generateUniqueKey() {
        return UUID.randomUUID().toString();
    }

}
