package com.farihim.assessment.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Generic Exception caught: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException caught: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        log.error("NoSuchElementException caught: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException caught: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        log.error("IllegalStateException caught: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
