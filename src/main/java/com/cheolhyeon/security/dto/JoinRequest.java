package com.cheolhyeon.security.dto;

import com.cheolhyeon.security.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Setter
@Getter
@ToString
public class JoinRequest {
    private String username;
    private String password;

    public UserEntity toEntity(PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role("ROLE_ADMIN")
                .build();
    }
}
