package com.firattamur.imageresizerservice.service.storage;

public interface StorageStrategy<T> {

    /**
     * Uploads an image to the storage
     *
     * @param key
     * @param image
     * @return url of the uploaded image
     */
    String uploadImage(String key, T image) throws Exception;

    /**
     * Downloads an image from the storage
     *
     * @param key
     * @return image as BufferedImage
     */
    T download(String key);

    /**
     * Deletes an image from the storage
     *
     * @param key
     * @return true if deleted, false otherwise
     */
    Boolean delete(String key);

    /**
     * Checks if an image exists in the storage
     *
     * @param key
     * @return true if exists, false otherwise
     */
    Boolean exists(String key);


}
