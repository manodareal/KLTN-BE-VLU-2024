package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.service.ProductService;
import com.example.usermanagement.dto.user.input.ProductInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductbyID(@PathVariable String productId){
        Product product = productService.getProductByID(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductInput product){
        Product createProduct = productService.createProduct(product);
        return new ResponseEntity<>(createProduct, HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody Product product){
        Product updateProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<Product> deleteProduct(@PathVariable String productId){
        log.info("Requesting to delete a product");
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
