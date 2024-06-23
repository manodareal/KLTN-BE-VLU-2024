package com.example.usermanagement.domain.repo;

import com.example.usermanagement.domain.entity.CartItem;
import com.example.usermanagement.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    List<CartItem> findByProduct(Product product);
}
