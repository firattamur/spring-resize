package com.firattamur.imageresizerservice.helper.image.resizer;

import java.util.Optional;

public interface ImageResizer {

    Optional<byte[]> resizeImage(byte[] imageBytes, String format, int width, int height, boolean aspectRatio);

}
