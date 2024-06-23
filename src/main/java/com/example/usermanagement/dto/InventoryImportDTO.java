package com.example.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InventoryImportDTO {
    private String importId;
    private String productId;
    private String productName;
    private String supplierId;
    private String supplierName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private LocalDate importDate;
}

