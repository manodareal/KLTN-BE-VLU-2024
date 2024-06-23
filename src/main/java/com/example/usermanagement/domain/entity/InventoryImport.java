package com.example.usermanagement.domain.entity;

import com.example.usermanagement.config.ShortUUIDGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "inventory_import")
public class InventoryImport {

    @Id
    @Column(name = "import_id", nullable = false, updatable = false)
    private String importId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDate importDate;

    public InventoryImport() {
        this.importId = "MNH-" + ShortUUIDGenerator.generateShortUUID();
        this.importDate = LocalDate.now();
    }
}
