package com.example.usermanagement.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Table (name = "account")
@Entity
public class User {
    @Id
    @Column(length = 50, nullable = false)
    private String id;

    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String password;
    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column
    private LocalDate createAt;
    @Column
    private LocalDate updateAt;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
        this.id = "TK-" + UUID.randomUUID();
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
}
