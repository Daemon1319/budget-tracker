package com.allan.expense_tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.allan.expense_tracker.entity.Expense;

import java.time.Instant;

public record ExpenseResponse(
  Long id,
  String description,
  String category,
  BigDecimal amount,
  LocalDate date,
  Instant createdAt,
  Instant updatedAt
) {
  // entity to DTO, since only DTOs should go back to the client
  public static ExpenseResponse from(Expense expense) {
    return new ExpenseResponse(
      expense.getId(),
      expense.getDescription(),
      expense.getCategory(),
      expense.getAmount(),
      expense.getDate(),
      expense.getCreatedAt(),
      expense.getUpdatedAt()
    );
  }
}