package com.example.usermanagement.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "supplier")
@Entity
public class Supplier {

    @Id
    @Column(length = 50, nullable = false)
    private String supplierId;

    @Column(length = 50, nullable = false)
    private String supplierName;

    @Column(length = 50, nullable = false)
    private Integer phoneNumber;

    @Column(nullable = false)
    private String supplierAddress;

    @Column(length = 50, nullable = false, unique = true)
    private String supplierMail;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String bankNumber;

    @Column
    private LocalDate createAt;

    @Column
    private LocalDate updateAt;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Product> products;

    public Supplier() {
        this.supplierId = "SUP-" + UUID.randomUUID();
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
}
