package com.cheolhyeon.security.type;

import lombok.Getter;

@Getter
public enum ErrorStatus {
    USERNAME_ALREADY_EXIST("해당 아이디는 이미 존재합니다.");

    private final String message;

    ErrorStatus(String message) {
        this.message = message;
    }
}
