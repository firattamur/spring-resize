package com.firattamur.imageresizerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(InvalidImageDimensionsException.class)
    public ResponseEntity<Object> handleImageTooLargeException(InvalidImageDimensionsException ex, WebRequest request) {

        String message = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        APIError apiError = new APIError(status, message);

        return new ResponseEntity<>(apiError, status);

    }

    @ExceptionHandler(InvalidImageFormatException.class)
    public ResponseEntity<Object> handleInvalidImageFormatException(InvalidImageFormatException ex, WebRequest request) {

        String message = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        APIError apiError = new APIError(status, message);

        return new ResponseEntity<>(apiError, status);

    }

}
