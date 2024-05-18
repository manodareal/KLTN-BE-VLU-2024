package com.example.usermanagement.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "products")
@Entity
public class Product {
    @Id
    @Column(name = "product_id", nullable = false, updatable = false)
    private String productId;

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
    private LocalDate createAt;

    @Column
    private LocalDate updateAt;

    @Column
    private String unitSwap;

    @Column
    private Integer quantitySwap;

    @Column
    private Integer quantityRemaining;

    @Column
    private BigDecimal sellPriceSwap;

    @Column
    private BigDecimal sellPriceDebtSwap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    public Product() {
        this.categories = new ArrayList<>();
        this.productId = "MSP-" + UUID.randomUUID();
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
}
