package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.repo.ProductRepository;
import com.example.usermanagement.dto.user.input.ProductInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    public Product getProductbyID(String productId){
        Product existProduct = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("Not found this product:" + productId)
        );
        log.info("Get product success");
        return existProduct;
    }
    public Product updateQuantityRemaining(String productId, int quantity) {
        Product product = getProductbyID(productId);
        int remaining = product.getQuantityRemaining() - quantity;
        product.setQuantityRemaining(remaining);
        product.setNote("số lượng tồn: " + product.getQuantityRemaining() + " " + product.getUnit()
                + "/" + product.getQuantitySwap() + " " + product.getUnitSwap());
        log.info("Create product success");
        return product;
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
        newProduct.setQuantityRemaining(productInput.getQuantity());

        newProduct.setSupplier(supplierService.getSupplierbyID(productInput.getSupplierId()));
        newProduct.getCategories().add(categoryService.getCategorybyID(productInput.getCategoryId()));

        newProduct.setQuantitySwap(productInput.getQuantitySwap());

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
        newProduct.setNote("số lượng tồn: " + productInput.getQuantity() + " " + productInput.getUnit()
                            + "/" + productInput.getQuantitySwap() + " " + productInput.getUnitSwap());
        log.info("Create product success");
        productRepository.save(newProduct);

        return newProduct;
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
    public Product updateProduct(String productId, Product product) {
        Product existingProduct = getProductbyID(productId);
        if (existingProduct == null) {
            log.error("Product not exist");
        } else {
            // Cập nhật thông tin sản phẩm từ updatedProduct vào existingProduct
            existingProduct.setName(product.getName());
            existingProduct.setUnit(product.getUnit());
            existingProduct.setPurchasePrice(product.getPurchasePrice());
            existingProduct.setSellPrice(product.getSellPrice());
            existingProduct.setSellPriceDebt(product.getSellPriceDebt());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setUnitSwap(product.getUnitSwap());
            existingProduct.setQuantitySwap(product.getQuantitySwap());

            log.info("Product's information updated");
        }
        // Lưu thông tin sản phẩm đã cập nhật vào cơ sở dữ liệu
        return existingProduct;
    }

    public void deleteProduct(String productId){
        log.info("Delete successfully");
        productRepository.deleteById(productId);
    }
}
