package com.example.usermanagement.dto.user.input;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InventoryImportInput {
    private String productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
