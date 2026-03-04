package com.example.demo;

import com.example.demo.service.UserService;
import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/api/users/signup")
    public User signup(@RequestBody User user) {
        // UserService의 가입 로직을 호출합니다.
        return userService.signup(user.getUsername(), user.getPassword());
    }

    @PostMapping("/api/users/login")
    public String login(@RequestBody User user) {
        try {
            User loginUser = userService.login(user.getUsername(), user.getPassword());
            return loginUser.getUsername() + "님 환영합니다! (인증 성공)";
        } catch (IllegalArgumentException e) {
            return "로그인 실패: " + e.getMessage();
        }
    }
}