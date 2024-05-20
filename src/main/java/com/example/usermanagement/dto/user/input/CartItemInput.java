package com.example.usermanagement.dto.user.input;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemInput {
    private String productId;
    private Integer quantity;
    private String unit;
    private BigDecimal price;
    private String customerId;
}
