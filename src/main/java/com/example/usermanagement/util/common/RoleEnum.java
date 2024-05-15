package com.example.usermanagement.util.common;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    STAFF("ROLE_STAFF");

    private final String role;

    RoleEnum(String role) {
        this.role= role;
    }
}
