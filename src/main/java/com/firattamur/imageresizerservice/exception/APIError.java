package com.firattamur.imageresizerservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class APIError {

    private HttpStatus status;
    private String message;

}
