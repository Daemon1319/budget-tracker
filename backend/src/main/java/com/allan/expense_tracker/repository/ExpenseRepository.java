package com.allan.expense_tracker.repository;

import com.allan.expense_tracker.entity.Expense;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

public interface ExpenseRepository extends JpaRepository<Expense, Long> { // is-a relationship (inheritance), so we extend
  // JpaRepository provides CRUD operations, needs what entity, and what type is the primary key
  
  // custom query methods, pattern is returnType findByFieldName(fieldType fieldName)
  Page<Expense> findByCategory(String category, Pageable pageable);
  // or SELECT * FROM expenses WHERE category = ?

  // format: @Query("...") ReturnType methodName(params);
  @Query("SELECT SUM(e.amount) FROM Expense e") BigDecimal getTotalAmount();

  @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate")
  BigDecimal getTotalAmountByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
