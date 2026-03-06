package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 테스트 편의를 위해 CSRF 보호 일시 비활성화
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // H2 콘솔 접속 허용
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/api/users/register", "/api/users/login", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // 누구나 접근 가능
                .anyRequest().authenticated() // 그 외 모든 요청은 로그인 필요
            )
            .formLogin(login -> login.disable()) // API 방식이므로 기본 폼 로그인 비활성화
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}