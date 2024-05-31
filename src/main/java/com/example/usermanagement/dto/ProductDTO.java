package com.example.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private String productId;
    private String name;
    private String unit;
    private BigDecimal purchasePrice;
    private BigDecimal sellPrice;
    private BigDecimal sellPriceDebt;
    private Integer quantity;
    private LocalDate createAt;
    private LocalDate updateAt;
    private String unitSwap;
    private String note;
    private Integer quantitySwap;
    private Integer quantityRemaining;
    private BigDecimal sellPriceSwap;
    private BigDecimal sellPriceDebtSwap;
    private String supplierId;
    private String supplierName;
    private List<CategoryDTO> categories;
}
