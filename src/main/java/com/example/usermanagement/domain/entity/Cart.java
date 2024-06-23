package com.example.usermanagement.domain.entity;

import com.example.usermanagement.config.ShortUUIDGenerator;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "carts")
@Entity
public class Cart {
    @Id
    @Column(name = "cart_id", nullable = false, updatable = false)
    private String cartId;

    @Column(nullable = false)
    private String status;

    @Column
    private LocalDate createdAt;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Debt> debts;

    @Column
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", updatable = false)
    private Customer customer;

    @Column
    private BigDecimal downPayment;

    @Column
    private BigDecimal debtAmount;

    public Cart() {
        this.cartId = "CART-" + ShortUUIDGenerator.generateShortUUID();
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.status = "Pending";
        this.downPayment = BigDecimal.ZERO;
        this.debtAmount = BigDecimal.ZERO;
    }
}
