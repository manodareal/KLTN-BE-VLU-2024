package com.example.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemDTO {
    private String cartItemId;
    private Integer quantity;
    private String unit;
    private String productId;
    private String productName; // Assuming you want product name as well
    private BigDecimal price;
    private BigDecimal priceDebt;
    private BigDecimal totalPrice;
    private BigDecimal totalDebt;
}
