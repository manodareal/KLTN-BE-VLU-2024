package com.example.usermanagement.domain.repo;

import com.example.usermanagement.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByCustomer_CustomerIdAndStatus(String customerId, String status);
    @Query("SELECT c FROM Cart c WHERE c.customer.customerId = :customerId")
    List<Cart> findByCustomerId(@Param("customerId") String customerId);
}

