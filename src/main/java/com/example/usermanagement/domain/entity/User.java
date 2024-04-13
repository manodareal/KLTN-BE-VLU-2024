package com.example.usermanagement.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table (name = "account")
@Entity
public class User {
    @Id
    @Column(length = 50, nullable = false)
    private String id = "TK-" + UUID.randomUUID().toString();

    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String password;
    @Column(length = 50, nullable = false, unique = true)
    private String email;
    @Column
    private LocalDate create_at;
    @Column
    private LocalDate update_at;
    @ManyToOne
    @JoinColumn(name="roleId", nullable = false)
    private Role role;


}
