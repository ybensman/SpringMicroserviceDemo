package com.example.demo.errorhandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

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

    @ExceptionHandler(NumsLimitExceededException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<String> handleNumsLimitExceededException(NumsLimitExceededException ex) {
        return new ResponseEntity<>("Bad request: " + ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>("Input validation failed: " + ex.getMessage(), BAD_REQUEST);
    }

    @Override
    @ResponseStatus(BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>("Input validation failed", BAD_REQUEST);

    }

}
