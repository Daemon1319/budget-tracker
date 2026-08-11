package com.allan.expense_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.allan.expense_tracker.entity.Expense;

public record ExpenseRequest( //NotBlank for String fields, NotNull for non-String fields
  @NotBlank String description,
  @NotNull @Positive BigDecimal amount,
  @NotBlank String category,
  @NotNull @PastOrPresent LocalDate date
) {
  // DTO to entity, since only entities can be saved to the DB
  public Expense toEntity() {
    return new Expense(description(), amount(), category(), date());
  }
} 