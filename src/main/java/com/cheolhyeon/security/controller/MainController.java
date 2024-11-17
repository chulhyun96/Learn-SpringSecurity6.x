package com.cheolhyeon.security.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {
    @GetMapping("/")
    public String mainPage(Model model, HttpSession session) {
        SecurityContext context = SecurityContextHolder.getContext();
        log.info("User {}", context.getAuthentication().getName());
        log.info("Role {}", context.getAuthentication().getAuthorities());

        String sessionId = session.getId();
        log.info("Session {}", sessionId);


        String id = context.getAuthentication().getName();
        String role = context.getAuthentication().getAuthorities().toString();

        model.addAttribute("id", id);
        model.addAttribute("role", role);
        return "main";
    }
}
