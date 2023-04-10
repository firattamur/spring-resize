package com.firattamur.imageresizerservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResizeImageRequest {

    @NotEmpty
    @NotBlank
    private String imageUrl;

    @NotEmpty
    @NotBlank
    private boolean aspectRatio;

    @NotEmpty
    @NotBlank
    @Size(min = 32, max = 3096)
    private int width;

    @NotEmpty
    @Size(min = 32, max = 3096)
    private int height;

}
