package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.InventoryImport;
import com.example.usermanagement.domain.entity.Product;
import com.example.usermanagement.domain.repo.InventoryImportRepository;
import com.example.usermanagement.domain.repo.ProductRepository;
import com.example.usermanagement.domain.repo.SupplierRepository;
import com.example.usermanagement.dto.InventoryImportDTO;
import com.example.usermanagement.dto.user.input.InventoryImportInput;
import com.example.usermanagement.mapper.InventoryImportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryImportService {

    private final InventoryImportRepository inventoryImportRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final InventoryImportMapper inventoryImportMapper;

    public List<InventoryImportDTO> importProducts(List<InventoryImportInput> inventoryImportInputs) {
        List<InventoryImportDTO> importedProducts = inventoryImportInputs.stream()
                .map(this::importProduct)
                .collect(Collectors.toList());
        log.info("Products imported successfully");
        return importedProducts;
    }

    public InventoryImportDTO importProduct(InventoryImportInput inventoryImportInput) {
        Product product = productRepository.findById(inventoryImportInput.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + inventoryImportInput.getProductId()));

        InventoryImport inventoryImport = new InventoryImport();
        inventoryImport.setProduct(product);
        inventoryImport.setQuantity(inventoryImportInput.getQuantity());
        inventoryImport.setUnitPrice(inventoryImportInput.getUnitPrice());
        inventoryImport.setTotalPrice(inventoryImportInput.getUnitPrice().multiply(BigDecimal.valueOf(inventoryImportInput.getQuantity())));
        inventoryImportRepository.save(inventoryImport);

        product.setQuantityRemaining(product.getQuantityRemaining() + inventoryImportInput.getQuantity());
        updateProductNote(product);
        productRepository.save(product);

        return inventoryImportMapper.toDTO(inventoryImport);
    }
    private void updateProductNote(Product product) {
        String note = String.format("Số lượng tồn: %d %s/%d %s",
                product.getQuantityRemaining(),
                product.getUnit(),
                product.getQuantitySwap(),
                product.getUnitSwap());
        product.setNote(note);
    }

    public List<InventoryImportDTO> getAllImports() {
        return inventoryImportRepository.findAll().stream()
                .map(inventoryImportMapper::toDTO)
                .collect(Collectors.toList());
    }
}
