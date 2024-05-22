package com.example.usermanagement.domain.utils;

import lombok.Getter;

@Getter
public enum DeleteFlag {
    NON_DELETE(0),
    DELETED(1);

    private final int code;

    DeleteFlag(int code) {
        this.code = code;
    }
}
