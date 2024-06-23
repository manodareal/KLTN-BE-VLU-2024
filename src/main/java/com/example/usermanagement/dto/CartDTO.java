package com.example.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CartDTO {
    private String cartId;
    private String status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<CartItemDTO> cartItems;
    private String customerId;
    private String customerName;
    private BigDecimal downPayment;
    private BigDecimal debtAmount;
    private List<DebtDTO> debts;
}
