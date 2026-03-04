package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERS") // 보통 User는 DB 예약어인 경우가 많아 USERS로 씁니다.
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID (자동 생성)

    @Column(nullable = false, unique = true)
    private String username; // 유저 이름 (아이디 역할)

    @Column(nullable = false)
    private String password; // 비밀번호

    // 생성자 (처음 가입할 때 사용)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}