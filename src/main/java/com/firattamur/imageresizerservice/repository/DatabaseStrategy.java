package com.firattamur.imageresizerservice.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DatabaseStrategy<T> {

    /**
     * Creates a record in the database
     *
     * @param record
     */
    void createRecord(T record);

    /**
     * Updates a record in the database
     *
     * @param record
     */
    void updateRecord(T record);

    /**
     * Deletes a record in the database
     *
     * @param record
     */
    void deleteRecord(T record);

    /**
     * Gets a record from the database by id
     *
     * @param id
     * @return
     */
    Optional<T> getRecordById(String id);

    /**
     * Gets all records from the database
     *
     * @return
     */
    List<T> getAllRecords();

    /**
     * Gets records from the database by query
     *
     * @param query
     * @return
     */
    List<T> getRecordsByQuery(Map<String, String> query);

    /**
     * Begins a transaction
     */
    void beginTransaction();

    /**
     * Commits a transaction
     */
    void commitTransaction();

    /**
     * Rolls back a transaction
     */
    void rollbackTransaction();

    /**
     * Closes the connection
     */
    void closeConnection();

}
