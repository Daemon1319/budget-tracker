package com.allan.expense_tracker.repository;

import com.allan.expense_tracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
public interface ExpenseRepository extends JpaRepository<Expense, Long> { // is-a relationship (inheritance), so we extend
  // JpaRepository provides CRUD operations, needs what entity, and what type is the primary key
  
  // custom query methods, pattern is returnType findByFieldName(fieldType fieldName)
  Page<Expense> findByCategory(String category, Pageable pageable);
  // or SELECT * FROM expenses WHERE category = ?
}
