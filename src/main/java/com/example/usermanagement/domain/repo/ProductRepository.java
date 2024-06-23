package com.example.usermanagement.domain.repo;



import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findBySupplier(Supplier supplier);
}