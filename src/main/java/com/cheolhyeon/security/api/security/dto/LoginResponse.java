package com.cheolhyeon.security.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LoginResponse {
    private HttpStatus status;
    private String username;
    private String role;

    public static LoginResponse from(HttpStatus status,UserDetails userDetails) {
        return LoginResponse.builder()
                .status(status)
                .username(userDetails.getUsername())
                .build();
    }
}
