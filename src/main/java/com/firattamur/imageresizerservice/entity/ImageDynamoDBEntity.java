package com.firattamur.imageresizerservice.entity;

import lombok.Builder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;


@DynamoDbBean
@Builder
public class ImageDynamoDBEntity {

    private String id;
    private String fileType;
    private String urlResizedImage;
    private String urlOriginalImage;
    private String dimensionsResizedImage;
    private String dimensionsOriginalImage;
    private Integer createdAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("fileType")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @DynamoDbAttribute("urlResizedImage")
    public String getUrlResizedImage() {
        return urlResizedImage;
    }

    public void setUrlResizedImage(String urlResizedImage) {
        this.urlResizedImage = urlResizedImage;
    }

    @DynamoDbAttribute("urlOriginalImage")
    public String getUrlOriginalImage() {
        return urlOriginalImage;
    }

    public void setUrlOriginalImage(String urlOriginalImage) {
        this.urlOriginalImage = urlOriginalImage;
    }

    @DynamoDbAttribute("dimensionsResizedImage")
    public String getDimensionsResizedImage() {
        return dimensionsResizedImage;
    }

    public void setDimensionsResizedImage(String dimensionsResizedImage) {
        this.dimensionsResizedImage = dimensionsResizedImage;
    }

    @DynamoDbAttribute("dimensionsOriginalImage")
    public String getDimensionsOriginalImage() {
        return dimensionsOriginalImage;
    }

    public void setDimensionsOriginalImage(String dimensionsOriginalImage) {
        this.dimensionsOriginalImage = dimensionsOriginalImage;
    }

    @DynamoDbAttribute("createdAt")
    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

}
