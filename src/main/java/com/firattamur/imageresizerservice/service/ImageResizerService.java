package com.firattamur.imageresizerservice.service;

import com.firattamur.imageresizerservice.helper.ImageResizerHelpers;
import com.firattamur.imageresizerservice.service.storage.StorageStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class ImageResizerService {

    private final StorageStrategy storageHandler;

    @Autowired
    public ImageResizerService(StorageStrategy storageHandler) {
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
    public String resizeImage(BufferedImage image, int width, int height) {

        BufferedImage resizedImage = ImageResizerHelpers.resizeImage(image, width, height);


    }

}
