package com.example.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDTO {
    private String customerId;
    private String username;
    private String email;
    private String fullName;
    private String address;
    private Date birthday;
    private LocalDate createAt;
    private LocalDate updateAt;
}
