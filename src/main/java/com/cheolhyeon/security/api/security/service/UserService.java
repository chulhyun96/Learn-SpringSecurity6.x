package com.cheolhyeon.security.api.security.service;

import com.cheolhyeon.security.api.security.dto.JoinRequest;
import com.cheolhyeon.security.api.security.entity.User;
import com.cheolhyeon.security.api.security.exception.UsernameAlreadyExist;
import com.cheolhyeon.security.api.security.repository.UserRepository;
import com.cheolhyeon.security.type.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(JoinRequest joinRequest) {
        if (userRepository.existsByUsername(joinRequest.getUsername())) {
            throw new UsernameAlreadyExist(ErrorStatus.USERNAME_ALREADY_EXIST);
        }
        return userRepository.save(joinRequest.toEntity(passwordEncoder));
    }
}
