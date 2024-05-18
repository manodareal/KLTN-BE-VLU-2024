package com.example.usermanagement.dto.user.input;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ProductInput {
    private String name;
    //đơn vị gốc của sản phẩm

    private String unit;

    //Giá Nhập Kho
    private BigDecimal purchasePrice;

    //Giá Bán sản phẩm
    private BigDecimal sellPrice;

    //giá bán ghi nợ
    private BigDecimal sellPriceDebt;

    //số lượng nhập kho
    private Integer quantity;
    private LocalDate createAt;

    private LocalDate updateAt;

    //ĐƠN VỊ QUY ĐỔI
    private String unitSwap;
    private String supplierId;
    private String categoryId;

    //SỐ LƯỢNG QUY ĐỔI THEO ĐƠN VỊ
    private Integer quantitySwap;

    //SỐ LƯỢNG TỒN KHO
    private Integer quantityRemaining; //bí

//    GIÁ BÁN QUY ĐỔI THEO ĐƠN VỊ QUY ĐỔI
    private BigDecimal sellPriceSwap;
//
//    //GIÁ BÁN GHI NỢ QUY ĐỔI THEO ĐƠN VỊ QUY ĐỔI
    private BigDecimal sellPriceDebtSwap;
}
