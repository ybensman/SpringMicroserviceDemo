package com.example.demo.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalAccess(IllegalAccessException ex) {
        return new ResponseEntity<>("Illegal Access!", BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
        return new ResponseEntity<>("Illegal number format exception", BAD_REQUEST);
    }

    @ExceptionHandler(FeatureNotAvailableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<String> handleFeatureNotAvailableException(FeatureNotAvailableException ex) {
        return new ResponseEntity<>("Feature is not available", BAD_REQUEST);
    }

}
