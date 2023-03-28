package com.firattamur.imageresizerservice.service;

import org.springframework.stereotype.Service;

@Service
public class ImageResizerService {

    public String resizeImage(String imageId, String width, String height) {
        return "Resized image";
    }

}
