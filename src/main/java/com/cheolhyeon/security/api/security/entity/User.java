package com.cheolhyeon.security.api.security.entity;

import com.cheolhyeon.security.api.security.dto.JoinRequest;
import com.cheolhyeon.security.api.security.type.AuthorityPolicy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthorityPolicy role;

    public User(String username, AuthorityPolicy role) {
        this.username = username;
        this.role = role;
    }

    public static User of(JoinRequest joinRequest, String encodedPassword) {
        return User.builder()
                .username(joinRequest.getUsername())
                .password(encodedPassword)
                .role(AuthorityPolicy.ROLE_ADMIN)
                .build();
    }
    public String getRoleAsString() {
        return role.toString();
    }
}
