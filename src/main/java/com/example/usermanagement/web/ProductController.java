package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.service.ProductService;
import com.example.usermanagement.dto.user.input.ProductInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductInput product){
        Product newProduct = productService.createProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

}
