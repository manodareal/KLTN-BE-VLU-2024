package com.example.usermanagement.domain.entity;

import com.example.usermanagement.config.ShortUUIDGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "customer")
@Entity
public class Customer {
    @Id
    @Column(name = "customer_id", length = 50, nullable = false)
    private String customerId;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column
    private String phoneNumber;
    @Column
    private String address;

    @Column(nullable = false)
    private Date birthday;

    @Column
    private LocalDate createAt;

    @Column
    private LocalDate updateAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;

    public Customer() {
        this.customerId = "CUS-" + ShortUUIDGenerator.generateShortUUID();
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
}
