package com.example.usermanagement.dto.user.input;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierInput {

    private String supplierName;
    private Integer phoneNumber;
    private String supplierAddress;
    private String supplierMail;
    private String bankName;
    private String bankNumber;
}
