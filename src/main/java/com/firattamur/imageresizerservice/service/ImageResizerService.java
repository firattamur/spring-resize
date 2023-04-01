package com.firattamur.imageresizerservice.service;

import com.firattamur.imageresizerservice.dto.ResizeImageRequest;
import com.firattamur.imageresizerservice.dto.ResizeImageResponse;
import com.firattamur.imageresizerservice.exception.InvalidImageDimensionsException;
import com.firattamur.imageresizerservice.exception.InvalidImageFormatException;
import com.firattamur.imageresizerservice.helper.ImageResizerHelpers;
import com.firattamur.imageresizerservice.service.storage.StorageStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.UUID;

@Service
public class ImageResizerService {

    private final StorageStrategy<BufferedImage> storageHandler;

    @Autowired
    public ImageResizerService(StorageStrategy<BufferedImage> storageHandler) {
        this.storageHandler = storageHandler;
    }

    /**
     * Resizes the image with the given width and height.
     * Uploads the resized and original image to s3 bucket.
     * Store metadata of image in DynamoDB.
     * Returns the url of resized and original image.
     *
     * @param image
     * @return ResponseEntity<ResizeImageResponse>
     */
    public ResizeImageResponse resizeImage(ResizeImageRequest resizeImageRequest) throws Exception {

        try {

            BufferedImage image = ImageResizerHelpers.decodeBase64ToImage(resizeImageRequest.getImage())
                    .orElseThrow(() -> new InvalidImageFormatException("Invalid image format in request body."));

            int width = resizeImageRequest.getWidth();
            int height = resizeImageRequest.getHeight();

            BufferedImage resizedImage = ImageResizerHelpers.resizeImage(image, width, height)
                    .orElseThrow(() -> new InvalidImageDimensionsException("Invalid image dimensions in request body."));

            UUID uuid = UUID.randomUUID();
            String key = uuid.toString();

            String resizedImageUrl = storageHandler.uploadImage(String.format("%s-resized", key), resizedImage);
            String originalImageUrl = storageHandler.uploadImage(String.format("%s-original", key), image);

            // TODO: Store metadata of image in DynamoDB

            return new ResizeImageResponse(resizedImageUrl, originalImageUrl);

        } catch (Exception e) {

            throw e;

        }

    }

}
