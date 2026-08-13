package com.allan.expense_tracker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import java.net.URI;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import com.allan.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import com.allan.expense_tracker.dto.ExpenseRequest;
import com.allan.expense_tracker.dto.ExpenseResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpenseController {
  private final ExpenseService expenseService;

  @PostMapping
  public ResponseEntity<ExpenseResponse> createExpense(
      @Valid // triggers the DTO's validation rules
      @RequestBody // tells Spring to convert the incoming JSON body into an ExpenseRequest object
      ExpenseRequest request) {
    ExpenseResponse response = expenseService.createExpense(request);
    URI location = URI.create("/api/v1/expenses/" + response.id()); // where the client can GET this new expense
    return ResponseEntity.created(location).body(response); // 201 + Location header + the created expense
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) { // pulls the {id} value straight from the URL path
    ExpenseResponse response = expenseService.getExpenseById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<Page<ExpenseResponse>> getAllExpenses(
    @RequestParam(required = false) String category, @PageableDefault(size = 20, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
    // optional filter, e.g. ?category=Food // page/size/sort from query params, defaults if not given
    Page<ExpenseResponse> response = expenseService.getAllExpenses(category, pageable);
    return ResponseEntity.ok(response);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequest request) {
    ExpenseResponse response = expenseService.updateExpense(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
    expenseService.deleteExpense(id);
    return ResponseEntity.noContent().build(); // 204 No Content
  }

  @GetMapping("/summary/total")
  public ResponseEntity<BigDecimal> getTotalAmount(@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {
    BigDecimal totalAmount = (startDate != null && endDate != null) 
      ? expenseService.getTotalAmountByDateRange(startDate, endDate) 
      : expenseService.getTotalAmount();
    return ResponseEntity.ok(totalAmount);
  }
}
