package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // SecurityConfig에서 생성된 Bean 주입

    @Transactional
    public User register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
         
        // 반드시 User 객체를 반환해야 함 (에러 해결 포인트)
        return userRepository.save(user);
    }

    public boolean login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        // Optional을 사용하여 안전하게 비교 후 boolean 반환 (에러 해결 포인트)
        return userOptional
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }
}