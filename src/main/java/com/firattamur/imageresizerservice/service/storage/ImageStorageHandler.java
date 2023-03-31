package com.firattamur.imageresizerservice.service.storage;

import java.awt.image.BufferedImage;

public class ImageStorageHandler {

    private final StorageStrategy storageStrategy;

    public ImageStorageHandler(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public String uploadImage(String key, BufferedImage image) throws Exception {
        return storageStrategy.uploadImage(key, image);
    }

    public BufferedImage download(String key) {
        return (BufferedImage) storageStrategy.download(key);
    }

    public Boolean delete(String key) {
        return storageStrategy.delete(key);
    }

    public Boolean exists(String key) {
        return storageStrategy.exists(key);
    }

}
