package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 나중에 로그인을 위해 아이디(username)로 유저를 찾는 기능입니다.
    User findByUsername(String username);
}