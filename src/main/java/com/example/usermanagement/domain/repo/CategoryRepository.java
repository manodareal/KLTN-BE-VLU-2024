package com.example.usermanagement.domain.repo;

import com.example.usermanagement.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
