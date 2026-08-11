package com.allan.expense_tracker.entity;



import java.time.Instant;
import java.time.LocalDate;
import java.math.BigDecimal;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // for relational database
// import org.springframework.data.annotation.Id; // use for non-relational database

@Entity
@Getter
@Setter
@NoArgsConstructor // replace the need for a constructor with no args, which is required by JPA
// @AllArgsConstructor // we use this if we want to create an object with all the fields, import lombok.AllArgsConstructor;
@Table(name = "expenses") // just changing the table name to expenses instead of expense
public class Expense {
  // Every field will be a column in the table

//   can be annotated with any of these
//   @Column(
//     name = "column_name",       // custom column name (if different from field name)
//     nullable = true/false,      // can this column be NULL? (default: true)
//     unique = true/false,        // must values be unique across all rows? (default: false)
//     length = 255,                // max length for String/varchar columns (default: 255)
//     precision = 10,              // total digits for BigDecimal (e.g. money)
//     scale = 2,                   // digits after the decimal point for BigDecimal
//     updatable = true/false,     // can this column be changed after insert? (default: true)
//     insertable = true/false,    // should this column be included in INSERT statements? (default: true)
//   )
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // just means auto generate the id
  private Long id;

  @Column(nullable = false, length = 100)
  private String description;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;
  
  @Column(nullable = false, length = 50)
  private String category;

  @Column(nullable = false)
  private LocalDate date;

  @CreationTimestamp  // auto-sets this field to "now" when the row is first created - never changes after
  @Column(updatable = false, nullable = false)
  private Instant createdAt;

  @UpdateTimestamp    // auto-sets this field to "now" every time the row is updated
  @Column(nullable = false)
  private Instant updatedAt;

  // public Expense(){ // this is what the NoArgsConstructor replaced
  // }

  public Expense(String description, BigDecimal amount, String category, LocalDate date) { // we use these since we only need these columns at creation not all
    this.description = description;
    this.amount = amount;
    this.category = category;
    this.date = date;
  }

  // getters and setters got replaced by @Getter and @Setter annotations from Lombok
}
