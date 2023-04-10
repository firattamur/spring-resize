package com.firattamur.imageresizerservice.repository;

import com.firattamur.imageresizerservice.entity.ImageDynamoDBEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class DynamoDBRepository<T> implements DatabaseStrategy<T> {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final Class<T> entityClass;

    public DynamoDBRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient, Class<T> entityClass) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.entityClass = entityClass;
    }

    @Override
    public void createRecord(T record) {
        getDynamoDbTable().putItem(record);
    }

    @Override
    public void updateRecord(T record) {
        getDynamoDbTable().updateItem(record);
    }

    @Override
    public void deleteRecord(T record) {
        Key key = Key.builder().partitionValue(getId(record)).build();
        getDynamoDbTable().deleteItem(key);
    }

    @Override
    public Optional<T> getRecordById(String id) {
        Key key = Key.builder().partitionValue(id).build();
        return (Optional<T>) getDynamoDbTable().getItem(key);
    }

    @Override
    public List<T> getAllRecords() {
        return getDynamoDbTable().scan().items().stream().toList();
    }

    @Override
    public List<T> getRecordsByQuery(Map<String, String> queryMap) {

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

    private DynamoDbTable<T> getDynamoDbTable() {

        DynamoDbTable<T> dynamoDbTable = dynamoDbEnhancedClient.table(this.entityClass.getSimpleName(),
                TableSchema.fromBean(this.entityClass));
        return dynamoDbTable;

    }

    private String getId(T record) {

        if (record instanceof ImageDynamoDBEntity) {
            return ((ImageDynamoDBEntity) record).getId();
        } else {
            throw new IllegalArgumentException("Record type is not supported");
        }

    }

}
