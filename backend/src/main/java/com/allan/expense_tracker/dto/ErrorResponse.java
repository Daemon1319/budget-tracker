package com.allan.expense_tracker.dto;

import java.time.Instant;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
  int status,
  String message,
  Instant timestamp
) {
  public static ErrorResponse of(HttpStatus status, String message) {
    return new ErrorResponse(status.value(), message, Instant.now());
  }
}
