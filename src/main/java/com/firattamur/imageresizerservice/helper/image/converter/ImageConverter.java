package com.firattamur.imageresizerservice.helper.image.converter;


import java.util.Optional;

/**
 * This interface is used to convert the BufferedImage object to a byte array and vice versa.
 */
public interface ImageConverter<T> {

    /**
     * This method converts the BufferedImage object to a byte array.
     *
     * @param imageBytes is the image to be converted.
     * @return: The converted image as a T object.
     */
    Optional<T> fromByteArrayToImage(byte[] imageBytes);

    /**
     * This method converts the byte array to a BufferedImage object.
     *
     * @param image  is the image to be converted.
     * @param format is the format of the image.
     * @return: The converted image as a byte array.
     */
    Optional<byte[]> fromImageToByteArray(T image, String format);

}