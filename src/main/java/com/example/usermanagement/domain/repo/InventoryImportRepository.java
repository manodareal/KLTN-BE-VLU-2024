package com.example.usermanagement.domain.repo;

import com.example.usermanagement.domain.entity.InventoryImport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryImportRepository extends JpaRepository<InventoryImport, Long> {
}
