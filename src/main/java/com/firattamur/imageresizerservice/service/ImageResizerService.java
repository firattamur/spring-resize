package com.firattamur.imageresizerservice.service;

import com.firattamur.imageresizerservice.dto.ResizeImageRequest;
import com.firattamur.imageresizerservice.dto.ResizeImageResponse;
import com.firattamur.imageresizerservice.dto.UploadImageResponse;
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
import java.util.Optional;
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

            byte[] originalImage = this.storageHandler.download(resizeImageRequest.getImageKey())
                    .orElseThrow(() -> new InvalidImageFormatException("Invalid image format in request body."));

            ImageDynamoDBEntity imageDynamoDBEntity = this.dynamoDatabaseStrategy.getRecordById(resizeImageRequest.getImageId())
                    .orElseThrow(() -> new InvalidImageFormatException("Invalid image format in request body."));

            byte[] resizedImage = this.imageResizer.resizeImage(originalImage, imageDynamoDBEntity.getFileType(), resizeImageRequest.getWidth(), resizeImageRequest.getHeight(), resizeImageRequest.isAspectRatio())
                    .orElseThrow(() -> new InvalidImageFormatException("Invalid image format in request body."));

            String resizedImageKey = "resized/" + resizeImageRequest.getImageId() + "_" + resizeImageRequest.getWidth() + "x" + resizeImageRequest.getHeight() + "." + imageDynamoDBEntity.getFileType();

            String resizedImageUrl = this.storageHandler.uploadImage(resizedImageKey, resizedImage, imageDynamoDBEntity.getFileType());

            imageDynamoDBEntity.setDimensionsResizedImage(resizeImageRequest.getWidth() + "x" + resizeImageRequest.getHeight());
            imageDynamoDBEntity.setCreatedAt((int) System.currentTimeMillis());
            imageDynamoDBEntity.setUrlResizedImage(resizedImageUrl);

            this.dynamoDatabaseStrategy.updateRecord(imageDynamoDBEntity);

            return new ResizeImageResponse(resizedImageUrl, imageDynamoDBEntity.getUrlOriginalImage());

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
    public UploadImageResponse uploadImage(MultipartFile image) throws Exception {

        try {

            String imageId = UUID.randomUUID().toString();
            String imageFormat = this.getImageFormat(image)
                    .orElseThrow(() -> new InvalidImageFormatException("Invalid image format in request body."));

            String imageKey = "original/" + imageId + "." + imageFormat;

            String imageUrl = this.storageHandler.uploadImage(imageKey, image.getBytes(), imageFormat);

            ImageDynamoDBEntity imageDynamoDBEntity = ImageDynamoDBEntity.builder()
                    .id(imageId)
                    .urlOriginalImage(imageUrl)
                    .fileType(imageFormat)
                    .build();

            this.dynamoDatabaseStrategy.createRecord(imageDynamoDBEntity);

            return new UploadImageResponse(imageId, imageKey);

        } catch (Exception e) {

            throw new Exception("Error occurred while uploading image to s3 bucket.", e);

        }

    }

    /**
     * This method returns the format of the image.
     *
     * @param image is the image to be converted.
     * @return: The format of the image.
     */
    private Optional<String> getImageFormat(MultipartFile image) {

        if (image.getContentType() == null) {
            return Optional.empty();
        }

        return image.getContentType().split("/").length > 1 ? Optional.of(image.getContentType().split("/")[1]) : Optional.empty();

    }

}
