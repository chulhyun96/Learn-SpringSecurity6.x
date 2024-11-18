package com.cheolhyeon.security.api.security.dto;

import com.cheolhyeon.security.api.security.entity.User;
import com.cheolhyeon.security.api.security.type.AuthorityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class JoinRequest {
    private String username;
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(AuthorityStatus.ROLE_ADMIN)
                .build();
    }

}
