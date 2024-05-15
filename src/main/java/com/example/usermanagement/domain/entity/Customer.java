package com.example.usermanagement.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @Column(name= "customer_id",length = 50, nullable = false)
    private String customerId;

    @Column(length = 50, nullable = false)
    private String username;
    @Column(length = 50, nullable = false)
    private String password;
    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(name="full_name",nullable = false)
    private String fullName;
    @Column
    private String address;
    @Column(nullable = false)
    private Date birthday;

    @Column
    private LocalDate createAt;
    @Column
    private LocalDate updateAt;

    public Customer(){
        this.customerId = "CUS-" + UUID.randomUUID();
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }

}
