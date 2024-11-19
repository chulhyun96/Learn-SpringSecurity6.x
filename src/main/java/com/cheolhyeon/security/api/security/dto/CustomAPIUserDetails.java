package com.cheolhyeon.security.api.security.dto;

import com.cheolhyeon.security.api.security.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
public class CustomAPIUserDetails implements UserDetails {
    private final User user;

    private CustomAPIUserDetails(User user) {
        this.user = user;
    }

    public static CustomAPIUserDetails from(User user) {
        return new CustomAPIUserDetails(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRoleAsString()));
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
