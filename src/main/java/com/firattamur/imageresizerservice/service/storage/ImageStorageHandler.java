package com.firattamur.imageresizerservice.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Optional;

@Service
public class ImageStorageHandler {

    private final StorageStrategy storageStrategy;

    @Autowired
    public ImageStorageHandler(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public String uploadImage(String key, BufferedImage image, String format) throws Exception {
        return storageStrategy.uploadImage(key, image, format);
    }

    public Optional<BufferedImage> download(String key) {
        return storageStrategy.download(key);
    }

    public Boolean delete(String key) {
        return storageStrategy.delete(key);
    }

    public Boolean exists(String key) {
        return storageStrategy.exists(key);
    }

}
