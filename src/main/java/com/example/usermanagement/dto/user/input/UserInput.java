package com.example.usermanagement.dto.user.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {
    private String username;
    private String fullName;
    private String email;
    private String password;

    private String role;
}
