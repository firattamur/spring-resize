package com.firattamur.imageresizerservice.repository;

import com.firattamur.imageresizerservice.entity.ImageDynamoDBEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class DynamoDatabaseStrategy implements DatabaseStrategy<ImageDynamoDBEntity> {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    public DynamoDatabaseStrategy(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    @Override
    public void createRecord(ImageDynamoDBEntity record) {
        getDynamoDbTable().putItem(record);
    }

    @Override
    public void updateRecord(ImageDynamoDBEntity record) {
        getDynamoDbTable().updateItem(record);
    }

    @Override
    public void deleteRecord(ImageDynamoDBEntity record) {
        Key key = Key.builder().partitionValue(record.getId()).build();
        getDynamoDbTable().deleteItem(key);
    }

    @Override
    public ImageDynamoDBEntity getRecordById(String id) {
        Key key = Key.builder().partitionValue(id).build();
        return getDynamoDbTable().getItem(key);
    }

    @Override
    public List<ImageDynamoDBEntity> getAllRecords() {

        List<ImageDynamoDBEntity> imageDynamoDBEntities = getDynamoDbTable().scan().items().stream().toList();
        return imageDynamoDBEntities;

    }

    @Override
    public List<ImageDynamoDBEntity> getRecordsByQuery(Map<String, String> queryMap) {

        if (queryMap == null || queryMap.isEmpty()) {
            return getAllRecords();
        }

        StringBuilder filterExpressionBuilder = new StringBuilder();
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        AtomicInteger counter = new AtomicInteger(0);

        queryMap.forEach((key, value) -> {

            if (value != null && !value.isBlank()) {
                if (counter.get() > 0) {
                    filterExpressionBuilder.append(" AND ");
                }

                String attributeNamePlaceholder = "#a" + counter.get();
                String queryValuePlaceholder = ":v" + counter.get();
                filterExpressionBuilder.append("contains(").append(attributeNamePlaceholder).append(", ").append(queryValuePlaceholder).append(")");
                expressionAttributeValues.put(queryValuePlaceholder, AttributeValue.builder().s(value).build());

                counter.incrementAndGet();
            }

        });

        if (expressionAttributeValues.isEmpty()) {
            return getAllRecords();
        }

        Expression filterExpression = Expression.builder()
                .expression(filterExpressionBuilder.toString())
                .expressionNames(Collections.singletonMap("#a0", queryMap.keySet().iterator().next()))
                .expressionValues(expressionAttributeValues)
                .build();

        ScanEnhancedRequest scanEnhancedRequest = ScanEnhancedRequest.builder()
                .filterExpression(filterExpression)
                .build();

        return getDynamoDbTable().scan(scanEnhancedRequest).items().stream().toList();

    }

    @Override
    public void beginTransaction() {
        // DynamoDB does not support transactions
    }

    @Override
    public void commitTransaction() {
        // DynamoDB does not support transactions
    }

    @Override
    public void rollbackTransaction() {
        // DynamoDB does not support transactions
    }

    @Override
    public void closeConnection() {
        // DynamoDB does not require connection to be closed
    }

    private DynamoDbTable<ImageDynamoDBEntity> getDynamoDbTable() {

        DynamoDbTable<ImageDynamoDBEntity> dynamoDbTable = dynamoDbEnhancedClient.table("ImageDynamoDBEntity",
                TableSchema.fromBean(ImageDynamoDBEntity.class));
        return dynamoDbTable;

    }

}
