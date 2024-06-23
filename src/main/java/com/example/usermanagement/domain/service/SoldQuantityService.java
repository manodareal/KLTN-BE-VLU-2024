package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.CartItem;
import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.repo.CartItemRepository;
import com.example.usermanagement.domain.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoldQuantityService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public void updateSoldQuantity(Product product) {
        int soldQuantity = cartItemRepository.findByProduct(product).stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        product.setSoldQuantity(soldQuantity);
        productRepository.save(product);
    }

    public void updateSoldQuantityForAllProducts() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            updateSoldQuantity(product);
        }
    }
}
