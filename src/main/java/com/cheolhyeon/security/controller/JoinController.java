package com.cheolhyeon.security.controller;

import com.cheolhyeon.security.dto.JoinRequest;
import com.cheolhyeon.security.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;
    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }
    @PostMapping("/joinProc")
    public String processJoin(@ModelAttribute JoinRequest joinRequest) {
        log.info("joinRequest: {}", joinRequest);
        joinService.processSave(joinRequest);
        return "redirect:/login";
    }
}
