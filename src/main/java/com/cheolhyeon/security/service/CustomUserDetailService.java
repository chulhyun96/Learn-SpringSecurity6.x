package com.cheolhyeon.security.service;

import com.cheolhyeon.security.dto.CustomUserDetails;
import com.cheolhyeon.security.entity.UserEntity;
import com.cheolhyeon.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    // 로그인 인증 절차
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity findUserEntity = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username));
        return new CustomUserDetails(findUserEntity); // DB에서 가져온 데이터를 가지고 SecurityConfig에 전달하면  인증 절차를 거친 후 SpringSession에 저장함
    }
}
