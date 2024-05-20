package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.Supplier;
import com.example.usermanagement.domain.service.SupplierService;
import com.example.usermanagement.dto.user.input.SupplierInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    @GetMapping("")
    public ResponseEntity<List<Supplier>> getAllSuppliers(){
        log.info("Starting get all Suppliers");
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }
    @GetMapping("/{supplierId}")
    public ResponseEntity<Supplier> getSupplierbyID(@PathVariable String supplierId){
        log.info("Starting get find Supplier");
        Supplier supplier = supplierService.getSupplierbyID(supplierId);
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Supplier> createSupplier(@RequestBody SupplierInput supplier){
        log.info("Requesting to create a supplier");
        Supplier createSupplier = supplierService.createSupplier(supplier);
        return new ResponseEntity<>(createSupplier, HttpStatus.OK);
    }
    @PutMapping("/{supplierId}/update")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable String supplierId, @RequestBody Supplier supplier){
        log.info("Requesting to update a supplier");
        Supplier updateSupplier = supplierService.updateSupplier(supplierId, supplier);
        return new ResponseEntity<>(updateSupplier, HttpStatus.OK);
    }
    @DeleteMapping("/{supplierId}/delete")
    public ResponseEntity<Supplier> deleteSupplier(@PathVariable String supplierId){
        log.info("Requesting to delete a supplier");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
