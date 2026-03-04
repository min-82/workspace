package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User signup(String username, String password) {
        // 실무적 보안: 실제로는 여기서 password를 암호화(BCrypt 등)해야 합니다.
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // 현재는 평문 저장 (추후 암호화 필요)
        
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
    }
}