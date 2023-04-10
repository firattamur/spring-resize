package com.firattamur.imageresizerservice.helper.image.resizer;

import com.firattamur.imageresizerservice.helper.image.converter.ImageConverter;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * This class is used to resize the image using the Thumbnailator library.
 */
@Service
public class ThumbnailatorImageResizer implements ImageResizer {

    private final ImageConverter<BufferedImage> imageConverter;

    @Autowired
    public ThumbnailatorImageResizer(ImageConverter<BufferedImage> imageConverter) {
        this.imageConverter = imageConverter;
    }

    @Override
    public Optional<byte[]> resizeImage(byte[] imageBytes, String format, int width, int height, boolean aspectRatio) {

        BufferedImage original = imageConverter.fromByteArrayToImage(imageBytes).orElse(null);

        if (original == null) {
            return Optional.empty();
        }

        try {

            Optional<BufferedImage> resizedImage = Optional.of(Thumbnails.of(original)
                    .size(width, height)
                    .outputFormat(format)
                    .keepAspectRatio(aspectRatio)
                    .asBufferedImage()
            );

            return imageConverter.fromImageToByteArray(resizedImage.get(), format);

        } catch (Exception e) {

            return Optional.empty();

        }

    }

}
