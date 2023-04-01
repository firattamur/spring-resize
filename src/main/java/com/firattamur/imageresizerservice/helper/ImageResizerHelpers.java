package com.firattamur.imageresizerservice.helper;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Optional;

public class ImageResizerHelpers {

    /**
     * This method resizes the image and returns the resized image as a BufferedImage object.
     *
     * @param originalImage: The original image to be resized.
     * @param width:         The width of the resized image.
     * @param height:        The height of the resized image.
     * @return: The resized image as a BufferedImage object.
     */
    public static Optional<BufferedImage> resizeImage(BufferedImage originalImage, int width, int height) {

        try {

            return Optional.of(Thumbnails.of(originalImage)
                    .size(width, height)
                    .asBufferedImage());

        } catch (Exception e) {

            return Optional.empty();

        }
        
    }

    /**
     * This method decode the base64 string to a BufferedImage object.
     *
     * @param base64Image: The base64 string to be decoded.
     * @return: The decoded image as a BufferedImage object.
     */
    public static Optional<BufferedImage> decodeBase64ToImage(String base64Image) {

        try {

            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            return Optional.of(ImageIO.read(inputStream));

        } catch (Exception e) {

            return Optional.empty();

        }

    }

    /**
     * This method retrieves the image type from the base64 string.
     *
     * @param base64Image: The base64 string to be decoded.
     * @return: The image type.
     */
    public static String getImageType(String base64Image) {

        String[] imageType = base64Image.split(";");
        return imageType[0].split("/")[1];

    }

    /**
     * This method converts the BufferedImage object to a byte array.
     *
     * @param image : The image to be converted.
     * @return: The image as a byte array.
     */
    public static byte[] convertImageToByteArray(BufferedImage image, String imageType) throws Exception {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, imageType, outputStream);

        return outputStream.toByteArray();

    }

}
