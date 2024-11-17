package com.cheolhyeon.security.service;

import com.cheolhyeon.security.dto.JoinRequest;
import com.cheolhyeon.security.exception.UsernameAlreadyExist;
import com.cheolhyeon.security.repository.UserRepository;
import com.cheolhyeon.security.type.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void processSave(JoinRequest joinRequest) {
        if(userRepository.existsByUsername(joinRequest.getUsername())) {
            throw new UsernameAlreadyExist(ErrorStatus.USERNAME_ALREADY_EXIST);
        }
        userRepository.save(joinRequest.toEntity(passwordEncoder));
    }
}
