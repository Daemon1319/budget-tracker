package com.allan.expense_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.allan.expense_tracker.dto.ErrorResponse;

@RestControllerAdvice // handles exceptions globally, returns JSON responses
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class) // catches this specific exception type
  public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
    ErrorResponse error = ErrorResponse.of(HttpStatus.NOT_FOUND, ex.getMessage()); // build the error body
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // send 404 + that error body
  }

  
}
