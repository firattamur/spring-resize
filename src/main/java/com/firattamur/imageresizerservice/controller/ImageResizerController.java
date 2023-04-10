package com.firattamur.imageresizerservice.controller;

import com.firattamur.imageresizerservice.dto.ResizeImageRequest;
import com.firattamur.imageresizerservice.dto.ResizeImageResponse;
import com.firattamur.imageresizerservice.service.ImageResizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resizer")
public class ImageResizerController {

    private final ImageResizerService imageResizerService;

    @Autowired
    public ImageResizerController(ImageResizerService imageResizerService) {
        this.imageResizerService = imageResizerService;
    }

    /**
     * Resizes the image with the given width and height.
     * Uploads the resized image to s3 bucket.
     * Store metadata of image in DynamoDB.
     * Returns the url of resized and original image.
     *
     * @param resizeImageRequest
     * @return ResponseEntity<ResizeImageResponse>
     */
    @PostMapping("/resize")
    public ResizeImageResponse resizeImage(ResizeImageRequest resizeImageRequest) throws Exception {

        return this.imageResizerService.resizeImage(resizeImageRequest);

    }

    /**
     * Uploads the image to s3 bucket.
     * Store metadata of image in DynamoDB.
     * Returns the url of original image.
     *
     * @param image is the image file
     * @return ResponseEntity<ResizeImageResponse>
     */
    @PostMapping("/upload-image")
    public ResizeImageResponse uploadImage(@RequestParam("imageFile") MultipartFile image) throws Exception {

        return this.imageResizerService.uploadImage(image);

    }

}
