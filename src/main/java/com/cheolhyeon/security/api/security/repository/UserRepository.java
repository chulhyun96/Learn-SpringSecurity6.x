package com.cheolhyeon.security.api.security.repository;

import com.cheolhyeon.security.api.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "UserRepository")
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUsername(String username);
}
