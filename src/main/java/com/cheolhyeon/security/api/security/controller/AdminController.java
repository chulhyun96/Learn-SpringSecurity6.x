package com.cheolhyeon.security.api.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public String callAdmin() {
        return "AdminController";
    }
}
