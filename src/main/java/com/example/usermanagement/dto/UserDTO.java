package com.example.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private LocalDate createAt;
    private LocalDate updateAt;
    private String roleName;
    private String roleDescription;
}
