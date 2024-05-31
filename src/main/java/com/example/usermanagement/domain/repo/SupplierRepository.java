package com.example.usermanagement.domain.repo;

import com.example.usermanagement.domain.entity.Supplier;
import com.example.usermanagement.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
    @Query("SELECT s FROM Supplier s WHERE s.supplierName LIKE %:keyword%")
    List<Supplier> searchByName(@Param("keyword") String keyword);
}
