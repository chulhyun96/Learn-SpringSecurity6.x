package com.cheolhyeon.security.api.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String callMain() {
        return "MainController";
    }
}
