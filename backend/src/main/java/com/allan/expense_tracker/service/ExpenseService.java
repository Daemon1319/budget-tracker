package com.allan.expense_tracker.service;

import org.springframework.stereotype.Service;
import com.allan.expense_tracker.repository.ExpenseRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.allan.expense_tracker.entity.Expense;
import com.allan.expense_tracker.dto.ExpenseResponse;
import com.allan.expense_tracker.dto.ExpenseRequest;
import com.allan.expense_tracker.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor // replaces the constructor
public class ExpenseService {
  private final ExpenseRepository expenseRepository; // has-a relationship (composition), so we don't extend

  // the constructor is how a component receives the things it depends on, so its methods can use them later
  // public ExpenseService(ExpenseRepository expenseRepository) {
  //   this.expenseRepository = expenseRepository;
  // }

  @Transactional // we use this if it modifies data, all-or-nothing, if any part fails, the whole method rolls back
  public ExpenseResponse createExpense(ExpenseRequest request) {
    Expense expense = request.toEntity(); // convert incoming DTO into a savable entity
    Expense savedExpense = expenseRepository.save(expense); // insert into DB, Hibernate fills id, createdAt, updatedAt
    return ExpenseResponse.from(savedExpense); // convert the saved entity into the outgoing DTO
  }

  public ExpenseResponse getExpenseById(Long id) {
    Expense expense = expenseRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id)); // throw error if not found
    return ExpenseResponse.from(expense); // convert to response DTO
  }

  public Page<ExpenseResponse> getAllExpenses(String category, Pageable pageable) {
    // filter by category if given, otherwise get everything - both paginated + sorted via pageable
    Page<Expense> expenses = (category != null)
      ? expenseRepository.findByCategory(category, pageable)
      : expenseRepository.findAll(pageable);

    return expenses.map(ExpenseResponse::from); // convert each entity to a DTO, keep pagination info intact
  }

  @Transactional
  public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
    Expense expense = expenseRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id)); // throw error if not found
    
    // overwrite the existing entity's fields with the new data
    expense.setDescription(request.description());
    expense.setAmount(request.amount());
    expense.setCategory(request.category());
    expense.setDate(request.date());

    Expense updatedExpense = expenseRepository.save(expense); // save the updated entity
    return ExpenseResponse.from(updatedExpense); // convert to response DTO
  }

  @Transactional
  public void deleteExpense(Long id) {
    if (!expenseRepository.existsById(id)) {
      throw new ResourceNotFoundException("Expense not found with id: " + id);
    }
    expenseRepository.deleteById(id);
  }
}
