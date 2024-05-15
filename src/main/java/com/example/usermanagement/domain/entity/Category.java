package com.example.usermanagement.domain.entity;

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

    public Category(){
        this.categoryId = "CGR-" + UUID.randomUUID();
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
}
