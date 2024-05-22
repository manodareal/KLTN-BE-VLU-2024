package com.example.usermanagement.dto.user.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInput {
    private String email;

    private String password;
}
