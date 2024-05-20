package com.example.usermanagement.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String unit;
    @Column(nullable = false)
    private BigDecimal purchasePrice;
    @Column(nullable = false)
    private BigDecimal sellPrice;

    private BigDecimal sellPriceDebt;
    @Column(nullable = false)
    private Integer quantity;
    @Column
    private String note;

    @Column
    private LocalDate createdAt;
    @Column
    private LocalDate updatedAt;

    @Column
    private String unitSwap;
    @Column
    private String note;

    @Column
    private Integer quantitySwap;
    @Column(nullable = false)
    private Integer quantityRemaining;
    @Column
    private BigDecimal sellPriceSwap;
    @Column
    private BigDecimal sellPriceDebtSwap;

    @OneToMany
    @JoinColumn( name = "supplierId")
    private Supplier supplierId;
    @JoinColumn( name = "categoryId")
    private Category categoryId;
}
