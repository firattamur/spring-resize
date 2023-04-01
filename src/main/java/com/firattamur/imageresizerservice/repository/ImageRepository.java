package com.firattamur.imageresizerservice.repository;

import com.firattamur.imageresizerservice.entity.ImageDynamoDBEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ImageRepository {

    private final DatabaseStrategy<ImageDynamoDBEntity> databaseStrategy;

    @Autowired
    public ImageRepository(DatabaseStrategy<ImageDynamoDBEntity> databaseStrategy) {
        this.databaseStrategy = databaseStrategy;
    }

    public void createRecord(ImageDynamoDBEntity record) {
        databaseStrategy.createRecord(record);
    }

    public void updateRecord(ImageDynamoDBEntity record) {
        databaseStrategy.updateRecord(record);
    }

    public void deleteRecord(ImageDynamoDBEntity record) {
        databaseStrategy.deleteRecord(record);
    }

    public ImageDynamoDBEntity getRecordById(String id) {
        return databaseStrategy.getRecordById(id);
    }

    public List<ImageDynamoDBEntity> getAllRecords() {
        return databaseStrategy.getAllRecords();
    }

    public List<ImageDynamoDBEntity> getRecordsByQuery(Map<String, String> queryMap) {
        return databaseStrategy.getRecordsByQuery(queryMap);
    }

    public void beginTransaction() {
        databaseStrategy.beginTransaction();
    }

    public void commitTransaction() {
        databaseStrategy.commitTransaction();
    }

    public void rollbackTransaction() {
        databaseStrategy.rollbackTransaction();
    }

    public void closeConnection() {
        databaseStrategy.closeConnection();
    }

}
