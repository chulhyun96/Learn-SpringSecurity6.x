package com.cheolhyeon.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameAlreadyExist.class)
    public ModelAndView handleUsernameAlreadyExist(UsernameAlreadyExist e) {
        ModelAndView modelAndView = new ModelAndView("4xx");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        modelAndView.addObject("error", e.getDescription());
        log.info("[ERROR] : {}", e.getMessage());
        return modelAndView;
    }
}
