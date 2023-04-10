package com.firattamur.imageresizerservice.helper.image.converter;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;


@Service
public class BufferedImageConverter implements ImageConverter<BufferedImage> {

    /**
     * This method converts the BufferedImage object to a byte array.
     *
     * @param imageBytes is the image to be converted.
     * @return: The converted image as a T object.
     */
    @Override
    public Optional<BufferedImage> fromByteArrayToImage(byte[] imageBytes) {

        try {

            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

            return Optional.of(ImageIO.read(inputStream));

        } catch (Exception e) {

            return Optional.empty();

        }

    }

    /**
     * This method converts the byte array to a BufferedImage object.
     *
     * @param image  is the image to be converted.
     * @param format is the format of the image.
     * @return: The converted image as a byte array.
     */
    @Override
    public Optional<byte[]> fromImageToByteArray(BufferedImage image, String format) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, format, outputStream);

            return Optional.of(outputStream.toByteArray());

        } catch (Exception e) {

            return Optional.empty();

        }

    }

}
