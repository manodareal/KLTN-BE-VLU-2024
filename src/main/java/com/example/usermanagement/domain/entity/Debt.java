package com.example.usermanagement.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debtId;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "cart_id", insertable = false, updatable = false)
    private String cartId;

    private BigDecimal amount;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private BigDecimal remainingAmount;
    private String status;

}
