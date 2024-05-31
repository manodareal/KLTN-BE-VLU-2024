package com.example.usermanagement.web;

import com.example.usermanagement.domain.service.ProductService;
import com.example.usermanagement.dto.ProductDTO;
import com.example.usermanagement.dto.user.input.ProductInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductByID(@PathVariable String productId) {
        ProductDTO product = productService.getProductByID(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductInput productInput) {
        ProductDTO createProduct = productService.createProduct(productInput);
        return new ResponseEntity<>(createProduct, HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productId, @RequestBody ProductInput productInput) {
        ProductDTO updateProduct = productService.updateProduct(productId, productInput);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        log.info("Requesting to delete a product");
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
