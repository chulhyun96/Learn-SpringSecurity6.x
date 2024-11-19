package com.cheolhyeon.security.api.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameAlreadyExist.class)
    public ErrorResponse handleUsernameAlreadyExist(UsernameAlreadyExist e) {
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
