package com.example.usermanagement.web;

import com.example.usermanagement.domain.service.InventoryImportService;
import com.example.usermanagement.dto.InventoryImportDTO;
import com.example.usermanagement.dto.user.input.InventoryImportInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/inventory-imports")
public class InventoryImportController {

    private final InventoryImportService inventoryImportService;

    @PostMapping("/import")
    public ResponseEntity<List<InventoryImportDTO>> importProducts(@RequestBody List<InventoryImportInput> inventoryImportInputs) {
        List<InventoryImportDTO> importedProducts = inventoryImportService.importProducts(inventoryImportInputs);
        return new ResponseEntity<>(importedProducts, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<InventoryImportDTO>> getAllImports() {
        List<InventoryImportDTO> imports = inventoryImportService.getAllImports();
        return new ResponseEntity<>(imports, HttpStatus.OK);
    }
}
