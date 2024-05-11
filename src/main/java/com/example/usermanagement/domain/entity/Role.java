package com.example.usermanagement.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Random;


@Getter
@Setter
@AllArgsConstructor
@Table(name = "role")
@Entity
public class Role {

    @Id
    @Column(name = "role_id", nullable = false, updatable = false)
    private String roleId;
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
    @Column(nullable = true)
    private String description;
    @Column
    private boolean isAdministrator;
    @Column
    private LocalDate createAt;
    @Column
    private LocalDate updateAt;

    public Role() {
        Random random= new Random();
        this.roleId = "role-" + random.nextInt(10000);
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }

    public Role(String roleName, String description, boolean isAdministrator) {
        Random random= new Random();
        this.roleId = "role-" + random.nextInt(10000);
        this.roleName = roleName;
        this.description = description;
        this.isAdministrator = isAdministrator;
    }

}
