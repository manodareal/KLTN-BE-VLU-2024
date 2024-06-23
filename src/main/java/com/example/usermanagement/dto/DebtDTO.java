package com.example.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DebtDTO {
    private Long debtId;
    private String cartId;
    private BigDecimal amount;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private BigDecimal remainingAmount;
    private String status;
}
