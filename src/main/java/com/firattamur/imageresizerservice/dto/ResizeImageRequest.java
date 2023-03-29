package com.firattamur.imageresizerservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResizeImageRequest {

    @NotEmpty
    private String image;

    @NotEmpty
    private int width;

    @NotEmpty
    private int height;

}
