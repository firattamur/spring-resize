package com.firattamur.imageresizerservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Image {

    private String id;
    private String fileType;
    private String urlResizedImage;
    private String urlOriginalImage;
    private String dimensionsResizedImage;
    private String dimensionsOriginalImage;
    private Date createdAt;

}
