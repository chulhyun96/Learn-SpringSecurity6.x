package com.cheolhyeon.security.type;

import lombok.Getter;

@Getter
public enum ErrorStatus {
    USERNAME_ALREADY_EXIST("The Username Already Exist.");

    private final String message;

    ErrorStatus(String message) {
        this.message = message;
    }
}
