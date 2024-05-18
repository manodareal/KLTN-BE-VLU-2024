package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.entity.Supplier;
import com.example.usermanagement.domain.repo.ProductRepository;
import com.example.usermanagement.dto.user.input.ProductInput;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierService supplierService;

    private final CategoryService categoryService;

    public List<Product> getAllProducts(){
        log.info("Get All products success");
        return productRepository.findAll();
    }

    public Optional<Product> getProductbyID(String productId){
        log.info("Get product success");
        return productRepository.findById(productId);
    }



    public Product createProduct(ProductInput productInput) {


        Product newProduct = new Product();
        newProduct.setName(productInput.getName());
        newProduct.setUnit(productInput.getUnit());
        newProduct.setPurchasePrice(productInput.getPurchasePrice());
        newProduct.setSellPrice(productInput.getSellPrice());
        newProduct.setSellPriceDebt(productInput.getSellPriceDebt());
        newProduct.setQuantity(productInput.getQuantity());
        newProduct.setUnitSwap(productInput.getUnitSwap());
        newProduct.setQuantitySwap(productInput.getQuantitySwap());
        newProduct.setQuantityRemaining(productInput.getQuantityRemaining());

        newProduct.setSupplier(supplierService.getSupplierbyID(productInput.getSupplierId()));
        newProduct.getCategories().add(categoryService.getCategorybyID(productInput.getCategoryId()));

        newProduct.setQuantitySwap(productInput.getQuantitySwap());

        if (!Objects.isNull(productInput.getSellPriceSwap())) {
            newProduct.setSellPriceSwap(productInput.getSellPriceSwap());
        } else {
            newProduct.setSellPriceSwap(productInput.getSellPrice().divide(BigDecimal.valueOf(productInput.getQuantitySwap())));
        }
        if (!Objects.isNull(productInput.getSellPriceDebtSwap())) {
            newProduct.setSellPriceDebtSwap(productInput.getSellPriceSwap());
        } else {
            newProduct.setSellPriceDebtSwap(productInput.getSellPriceDebt().divide(BigDecimal.valueOf(productInput.getQuantitySwap())));
        }
        log.info("Create product success");
        productRepository.save(newProduct);

        return newProduct;

    }
}
