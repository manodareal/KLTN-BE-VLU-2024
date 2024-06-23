package com.example.usermanagement.mapper;

import com.example.usermanagement.domain.entity.InventoryImport;
import com.example.usermanagement.dto.InventoryImportDTO;
import org.springframework.stereotype.Component;

@Component
public class InventoryImportMapper {

    public InventoryImportDTO toDTO(InventoryImport inventoryImport) {
        InventoryImportDTO dto = new InventoryImportDTO();
        dto.setImportId(inventoryImport.getImportId());
        dto.setProductId(inventoryImport.getProduct().getProductId());
        dto.setProductName(inventoryImport.getProduct().getName());
        dto.setSupplierId(inventoryImport.getProduct().getSupplier().getSupplierId());
        dto.setSupplierName(inventoryImport.getProduct().getSupplier().getSupplierName());
        dto.setQuantity(inventoryImport.getQuantity());
        dto.setUnitPrice(inventoryImport.getUnitPrice());
        dto.setTotalPrice(inventoryImport.getTotalPrice());
        dto.setImportDate(inventoryImport.getImportDate());
        return dto;
    }
}
