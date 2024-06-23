package com.example.usermanagement.domain.entity;


import com.example.usermanagement.config.ShortUUIDGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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

    public Supplier(){
        this.supplierId = "SUP-" + ShortUUIDGenerator.generateShortUUID();
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }


}
