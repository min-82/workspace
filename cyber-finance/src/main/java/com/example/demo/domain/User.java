package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    // 🌟 초기 자산 설정 (기본값 0.0)
    private double balance;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 10000000.0; // 생성자에서 1,000만 원 지급
    }
}