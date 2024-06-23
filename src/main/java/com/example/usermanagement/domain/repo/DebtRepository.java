package com.example.usermanagement.domain.repo;

import com.example.usermanagement.domain.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByCart_CartId(String cartId);
}
