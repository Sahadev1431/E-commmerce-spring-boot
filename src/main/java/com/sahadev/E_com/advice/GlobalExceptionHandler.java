package com.sahadev.E_com.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler (value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException (MethodArgumentNotValidException ex) {
        Map<String,String> error = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err ->
                error.put(err.getField(),err.getDefaultMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleSQLIntegrityConstraintViolationException (SQLIntegrityConstraintViolationException ex) {
        Map<String,String> error = new HashMap<>();
        String errorMessage = "Database integrity constraint violated. Please check your input.";

        if (ex.getMessage().contains("Duplicate entry")) {
            errorMessage = "Duplicate entry detected. The value you are trying to insert already exist";
        }

        error.put("error",errorMessage);
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    @ExceptionHandler (value = RuntimeException.class)
    public ResponseEntity<Map<String,String>> handleRuntimeException(RuntimeException ex) {
        Map<String,String> error = new HashMap<>();

        error.put("Error",ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
