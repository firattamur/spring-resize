package com.firattamur.imageresizerservice.service.storage;

import java.awt.image.BufferedImage;

public class S3StorageHandler implements StorageHandler {

    @Override
    public String uploadImage(String key, BufferedImage image) {
        return null;
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
