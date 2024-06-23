package com.example.usermanagement.mapper;

import com.example.usermanagement.domain.entity.Category;
import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.dto.ProductDTO;
import com.example.usermanagement.dto.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setUnit(product.getUnit());
        dto.setPurchasePrice(product.getPurchasePrice());
        dto.setSellPrice(product.getSellPrice());
        dto.setSellPriceDebt(product.getSellPriceDebt());
        dto.setQuantity(product.getQuantity());
        dto.setCreateAt(product.getCreateAt());
        dto.setUpdateAt(product.getUpdateAt());
        dto.setUnitSwap(product.getUnitSwap());
        dto.setNote(product.getNote());
        dto.setQuantitySwap(product.getQuantitySwap());
        dto.setQuantityRemaining(product.getQuantityRemaining());
        dto.setSoldQuantity(product.getSoldQuantity());
        dto.setSellPriceSwap(product.getSellPriceSwap());
        dto.setSellPriceDebtSwap(product.getSellPriceDebtSwap());
        dto.setSupplierId(product.getSupplier().getSupplierId());
        dto.setSupplierName(product.getSupplier().getSupplierName());

        dto.setCategories(product.getCategories().stream().map(this::toCategoryDTO).collect(Collectors.toList()));
        return dto;
    }

    private CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        return null;
    }
}
