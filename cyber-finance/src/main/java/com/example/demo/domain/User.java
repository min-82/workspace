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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 고유 ID 자동 생성
    private Long id;

    @Column(unique = true, nullable = false) // 중복 방지를 위해 DB 레벨에서도 유니크 제약 추가
    private String username;

    @Column(nullable = false)
    private String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}