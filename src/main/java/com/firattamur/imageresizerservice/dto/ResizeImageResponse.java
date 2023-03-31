package com.firattamur.imageresizerservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResizeImageResponse {

    private String urlResizedImage;
    private String urlOriginalImage;


}
