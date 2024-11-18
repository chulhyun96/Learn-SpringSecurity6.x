package com.cheolhyeon.security.api.security.exception;

import com.cheolhyeon.security.type.ErrorStatus;
import lombok.Getter;

@Getter
public class UsernameAlreadyExist extends RuntimeException {
    private final ErrorStatus errorStatus;
    private final String description;

    public UsernameAlreadyExist(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
        this.description = errorStatus.getMessage();
    }
}
