package com.firattamur.imageresizerservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResizeImageResponse {

    private String urlResizedImage;
    private String urlOriginalImage;

}
