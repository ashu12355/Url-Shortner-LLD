package com.example.url_shortener.exception;

import jakarta.persistence.ManyToOne;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> illegalArgumentException(IllegalArgumentException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("Message",ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> resourceNotFoundException(ResourceNotFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message",ex.getMessage()));
    }

    @ExceptionHandler(UrlExpiredException.class)
    public ResponseEntity<Map<String,String>> urlExpiredException(UrlExpiredException ex){
        return ResponseEntity
                .status(HttpStatus.GONE)
                .body(Map.of("message",ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleGenericException(Exception exception){

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", exception.getMessage()));
    }


}
