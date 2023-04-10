package com.firattamur.imageresizerservice.helper.image.decoder;

import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * This interface is used to decode the base64 string to a BufferedImage object.
 */
public interface Base64Decoder {

    /**
     * This method decode the base64 string to a BufferedImage object.
     *
     * @param base64Image: The base64 string to be decoded.
     * @return: The decoded image as a BufferedImage object.
     */
    Optional<BufferedImage> decodeBase64ToImage(String base64Image);

}
