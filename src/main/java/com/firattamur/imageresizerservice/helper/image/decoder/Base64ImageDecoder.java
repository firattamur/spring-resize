package com.firattamur.imageresizerservice.helper.image.decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Optional;

public class Base64ImageDecoder implements Base64Decoder {


    /**
     * This method decode the base64 string to a BufferedImage object.
     *
     * @param base64Image: The base64 string to be decoded.
     * @return: The decoded image as a BufferedImage object.
     */
    @Override
    public Optional<BufferedImage> decodeBase64ToImage(String base64Image) {

        try {

            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            return Optional.of(ImageIO.read(inputStream));

        } catch (Exception e) {

            return Optional.empty();

        }

    }

}
