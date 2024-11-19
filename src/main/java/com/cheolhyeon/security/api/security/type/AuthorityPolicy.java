package com.cheolhyeon.security.api.security.type;

public enum AuthorityPolicy {
    ROLE_USER, ROLE_ADMIN;

    public static AuthorityPolicy getRole(String role) {
        if (role.equals("ROLE_USER")) {
            return ROLE_USER;
        }
        return ROLE_ADMIN;
    }
}
