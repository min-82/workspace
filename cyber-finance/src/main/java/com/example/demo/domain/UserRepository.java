package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 유저네임 중복 확인을 위한 메서드
    boolean existsByUsername(String username);
    
    Optional<User> findByUsername(String username);
}