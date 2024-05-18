package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Supplier;
import com.example.usermanagement.domain.repo.SupplierRepository;
import com.example.usermanagement.dto.user.input.SupplierInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers(){
        log.info("Get all supplier company success");
        return supplierRepository.findAll();
    }
    public Supplier getSupplierbyID(String supplierId){
        Supplier existSupplier = supplierRepository.findById(supplierId).orElseThrow(
                () -> new NullPointerException("Not found this supplier:" + supplierId)
        );
        log.info("Get category success");
//        return supplierRepository.findById(supplierId);
        return existSupplier;

    }

    public Supplier createSupplier(SupplierInput supplierInput){
        Supplier newSupplier = new Supplier();
        newSupplier.setSupplierName(supplierInput.getSupplierName());
        newSupplier.setSupplierAddress(supplierInput.getSupplierAddress());
        newSupplier.setSupplierMail(supplierInput.getSupplierMail());
        newSupplier.setBankName(supplierInput.getBankName());
        newSupplier.setBankNumber(supplierInput.getBankNumber());
        newSupplier.setPhoneNumber(supplierInput.getPhoneNumber());

        supplierRepository.save(newSupplier);
        log.info("Create supplier success");
        return newSupplier;
    }

    public Supplier updateSupplier(String supplierId, Supplier supplier){
        Supplier existSupplier = getSupplierbyID(supplierId);
        if(existSupplier == null){
            log.error("Supplier not exist");
        } else {
            existSupplier.setSupplierName(supplier.getSupplierName());
            existSupplier.setSupplierAddress(supplier.getSupplierAddress());
            existSupplier.setSupplierMail(supplier.getSupplierMail());
            existSupplier.setBankName(supplier.getBankName());
            existSupplier.setBankNumber(supplier.getBankNumber());
            existSupplier.setPhoneNumber(supplier.getPhoneNumber());
            log.info("Update Supplier successfully");
        }
        return existSupplier;
    }

    public void deleteSupplier(String supplierId){
        log.info("Delete successfully");
        supplierRepository.deleteById(supplierId);
    }
}
