package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.repo.ProductRepository;
import com.example.usermanagement.dto.ProductDTO;
import com.example.usermanagement.dto.user.input.ProductInput;
import com.example.usermanagement.mapper.ProductMapper;
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
    private final ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {
        log.info("Get all products success");
        return productRepository.findAll().stream().map(productMapper::toDTO).toList();
    }

    public ProductDTO getProductByID(String productId) {
        return productRepository.findById(productId).map(productMapper::toDTO).orElseThrow(
                () -> new NullPointerException("Not found this product: " + productId)
        );
    }

    public ProductDTO createProduct(ProductInput productInput) {
        Product newProduct = new Product();
        newProduct.setName(productInput.getName());
        newProduct.setUnit(productInput.getUnit());
        newProduct.setPurchasePrice(productInput.getPurchasePrice());
        newProduct.setSellPrice(productInput.getSellPrice());
        newProduct.setSellPriceDebt(productInput.getSellPriceDebt());
        newProduct.setQuantity(productInput.getQuantity());
        newProduct.setUnitSwap(productInput.getUnitSwap());
        newProduct.setQuantitySwap(productInput.getQuantitySwap());
        newProduct.setQuantityRemaining(productInput.getQuantity());

        newProduct.setSupplier(supplierService.getSupplierbyID(productInput.getSupplierId()));
        newProduct.getCategories().add(categoryService.getCategorybyID(productInput.getCategoryId()));

        if (!Objects.isNull(productInput.getSellPriceSwap())) {
            newProduct.setSellPriceSwap(productInput.getSellPriceSwap());
        } else {
            newProduct.setSellPriceSwap(productInput.getSellPrice().divide(BigDecimal.valueOf(productInput.getQuantitySwap()), BigDecimal.ROUND_HALF_UP));
        }

        if (!Objects.isNull(productInput.getSellPriceDebtSwap())) {
            newProduct.setSellPriceDebtSwap(productInput.getSellPriceDebtSwap());
        } else {
            newProduct.setSellPriceDebtSwap(productInput.getSellPriceDebt().divide(BigDecimal.valueOf(productInput.getQuantitySwap()), BigDecimal.ROUND_HALF_UP));
        }

        newProduct.setNote("Số lượng tồn: " + productInput.getQuantity() + " " + productInput.getUnit() + "/" + productInput.getQuantitySwap() + " " + productInput.getUnitSwap());
        log.info("Create product success");
        productRepository.save(newProduct);

        return productMapper.toDTO(newProduct);
    }

    public ProductDTO getProductById(String productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.map(productMapper::toDTO).orElse(null);
    }

    public BigDecimal getProductPriceByUnit(Product product, String unit) {
        if (product.getUnit().equals(unit)) {
            return product.getSellPrice();
        } else if (product.getUnitSwap() != null && product.getUnitSwap().equals(unit)) {
            return product.getSellPriceSwap();
        } else {
            throw new IllegalArgumentException("Invalid unit");
        }
    }

    public BigDecimal getProductPriceDebtByUnit(Product product, String unit) {
        if (product.getUnit().equals(unit)) {
            return product.getSellPriceDebt();
        } else if (product.getUnitSwap() != null && product.getUnitSwap().equals(unit)) {
            return product.getSellPriceDebtSwap();
        } else {
            throw new IllegalArgumentException("Invalid unit");
        }
    }

    public ProductDTO updateQuantityRemaining(String productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NullPointerException("Product not found"));
        int remaining = product.getQuantityRemaining() - quantity;
        product.setQuantityRemaining(remaining);
        product.setNote("Số lượng tồn: " + product.getQuantityRemaining() + " " + product.getUnit()
                + "/" + product.getQuantitySwap() + " " + product.getUnitSwap());
        log.info("Updated product quantity remaining");
        return productMapper.toDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(String productId, ProductInput productInput) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new NullPointerException("Product not found"));

        existingProduct.setName(productInput.getName());
        existingProduct.setUnit(productInput.getUnit());
        existingProduct.setPurchasePrice(productInput.getPurchasePrice());
        existingProduct.setSellPrice(productInput.getSellPrice());
        existingProduct.setSellPriceDebt(productInput.getSellPriceDebt());
        existingProduct.setQuantity(productInput.getQuantity());
        existingProduct.setUnitSwap(productInput.getUnitSwap());
        existingProduct.setQuantitySwap(productInput.getQuantitySwap());
        existingProduct.setQuantityRemaining(productInput.getQuantityRemaining());

        log.info("Product's information updated");
        return productMapper.toDTO(productRepository.save(existingProduct));
    }

    public void deleteProduct(String productId) {
        log.info("Delete success");
        productRepository.deleteById(productId);
    }
}
