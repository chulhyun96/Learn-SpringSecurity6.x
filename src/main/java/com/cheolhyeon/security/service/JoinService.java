package com.cheolhyeon.security.service;

import com.cheolhyeon.security.dto.JoinRequest;
import com.cheolhyeon.security.repository.JoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final JoinRepository joinRepository;
    private final PasswordEncoder passwordEncoder;

    public void processSave(JoinRequest joinRequest) {
        joinRepository.save(joinRequest.toEntity(passwordEncoder));
    }
}
