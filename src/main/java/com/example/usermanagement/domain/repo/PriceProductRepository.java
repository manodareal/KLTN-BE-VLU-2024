package com.example.usermanagement.domain.repo;
import com.example.usermanagement.domain.entity.PriceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceProductRepository extends JpaRepository<PriceProduct, Integer> {
    List<PriceProduct> findByProduct_ProductIdAndUnit(String productId, String unit);

}
