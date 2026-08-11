package com.allan.expense_tracker.repository;

import com.allan.expense_tracker.entity.Expense;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> { // is-a relationship (inheritance), so we extend
  // JpaRepository provides CRUD operations, needs what entity, and what type is the primary key
  
  // custom query methods, pattern is returnType findByFieldName(fieldType fieldName)
  List<Expense> findByCategory(String category);
  // or SELECT * FROM expenses WHERE category = ?
}
