package com.cheolhyeon.security.api.security.dto;

import com.cheolhyeon.security.api.security.type.AuthorityPolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class JoinResponse {
    private String username;
    private AuthorityPolicy role;
}
