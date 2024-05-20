package com.example.usermanagement.domain.repo;



import com.example.usermanagement.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}