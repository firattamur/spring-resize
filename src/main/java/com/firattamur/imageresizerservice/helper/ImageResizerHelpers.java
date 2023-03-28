package com.firattamur.imageresizerservice.helper;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ImageResizerHelpers {

    /**
     * This method resizes the image and returns the resized image as a BufferedImage object.
     *
     * @param originalImage: The original image to be resized.
     * @param width:         The width of the resized image.
     * @param height:        The height of the resized image.
     * @return: The resized image as a BufferedImage object.
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) throws Exception {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(originalImage)
                .size(width, height)
                .outputFormat("JPEG")
                .outputQuality(1)
                .toOutputStream(outputStream);

        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ImageIO.read(inputStream);

    }

    /**
     * This method decode the base64 string to a BufferedImage object.
     *
     * @param base64Image: The base64 string to be decoded.
     * @return: The decoded image as a BufferedImage object.
     */
    public static BufferedImage decodeBase64ToImage(String base64Image) throws Exception {

        byte[] decodedImage = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedImage);

        BufferedImage image = ImageIO.read(inputStream);
        inputStream.close();

        return image;

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

}
