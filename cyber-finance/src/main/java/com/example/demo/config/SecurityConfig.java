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
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/index.html", "/mainIndex.html", "/login.html", "/register.html", 
                    "/css/**", "/js/**", "/images/**", "/favicon.ico"
                ).permitAll()
                
                .requestMatchers(
                    "/api/users/register", // 🌟 UserController와 주소가 일치하는지 확인!
                    "/api/users/signup",   // 혹시 몰라 두 쪽 다 허용
                    "/api/users/login", 
                    "/api/users/me",
                    "/api/stocks/**",      // 🌟 주식 조회 API 허용 (이게 없어서 403 떴을 것)
                    "/h2-console/**", 
                    "/swagger-ui/**", 
                    "/v3/api-docs/**"
                ).permitAll()
                
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutUrl("/api/users/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(200);
                })
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .formLogin(login -> login.disable())
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}