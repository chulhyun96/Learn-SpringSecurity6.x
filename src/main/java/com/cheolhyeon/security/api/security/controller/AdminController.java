package com.cheolhyeon.security.api.security.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public String callAdmin() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String role = userRole.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("None");
        return "AdminController " + username +" "+role;
    }
}
