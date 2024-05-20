package com.example.usermanagement.dto.user.input;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductInput {
    private String name;
    private String unit;
    private BigDecimal purchasePrice;
    private BigDecimal sellPrice;
    private BigDecimal sellPriceDebt;
    private Integer quantity;
    private String unitSwap;
    private Integer quantitySwap;
    private String supplierId;
    private String categoryId;
    private BigDecimal sellPriceSwap;
    private BigDecimal sellPriceDebtSwap;
    private String note;

    private Integer quantityRemaining;
}
