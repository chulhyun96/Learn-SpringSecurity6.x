package com.cheolhyeon.security.api.security.service;

import com.cheolhyeon.security.api.security.dto.CustomAPIUserDetails;
import com.cheolhyeon.security.api.security.entity.User;
import com.cheolhyeon.security.api.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        log.info("loadUserByUsername: {}", username);
        return CustomAPIUserDetails.from(user);
    }
}
