package com.cheolhyeon.security.api.security.controller;

import com.cheolhyeon.security.api.security.dto.JoinRequest;
import com.cheolhyeon.security.api.security.dto.JoinResponse;
import com.cheolhyeon.security.api.security.entity.User;
import com.cheolhyeon.security.api.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final UserService userService;

    @PostMapping("/join")
    public JoinResponse join(@RequestBody JoinRequest joinRequest) {
        User save = userService.save(joinRequest);
        return JoinResponse.builder()
                .username(save.getUsername())
                .role(save.getRole())
                .build();
    }
}
