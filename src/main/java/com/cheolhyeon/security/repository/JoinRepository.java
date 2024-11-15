package com.cheolhyeon.security.repository;

import com.cheolhyeon.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinRepository extends JpaRepository<UserEntity, Long> {
}
