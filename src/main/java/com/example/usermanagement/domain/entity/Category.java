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
@Table(name= "category")
@Entity
public class Category {

    @Id
    @Column(name = "category_id",nullable = false, updatable = false)
    private String categoryId;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @Column(nullable = false)
    private String codeName;

    @Column
    private LocalDate createAt;

    @Column
    private LocalDate updateAt;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Product> products;

    public Category() {
        this.categoryId = "CGR-" + UUID.randomUUID();
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
}
