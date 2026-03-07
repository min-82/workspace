package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CyberFinanceApplication { // 파일 이름과 클래스 이름이 일치해야 합니다.

    public static void main(String[] args) {
        SpringApplication.run(CyberFinanceApplication.class, args);
    }

    // 아까 추가했던 Bean 설정을 여기로 옮깁니다.
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}