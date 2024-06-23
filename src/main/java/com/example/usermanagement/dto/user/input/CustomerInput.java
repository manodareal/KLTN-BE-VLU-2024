package com.example.usermanagement.dto.user.input;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class CustomerInput {
    private String username;
    private String fullName;
    private String email;
    private String address;
    private Date birthday;
    private String phoneNumber;

}
