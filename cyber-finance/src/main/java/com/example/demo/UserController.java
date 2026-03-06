package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    // 🌟 세션에 인증 정보를 저장하기 위한 저장소 도구
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public record RegisterRequest(String username, String password) {}

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        User user = userService.register(request.username(), request.password());
        return "회원가입 완료";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username, 
            @RequestParam String password,
            HttpServletRequest request,    // 🌟 현재 요청 정보
            HttpServletResponse response   // 🌟 응답 정보 (쿠키 등을 굽기 위함)
    ) {
        boolean isSuccess = userService.login(username, password);

        if (isSuccess) {
            // 1. 새로운 인증 티켓(Authentication) 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, 
                    null, 
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );

            // 2. 빈 장부(SecurityContext)를 만들어 티켓을 넣음
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            
            // 3. 현재 실행 중인 스레드의 장부에 기록
            SecurityContextHolder.setContext(context);

            // 4. 🌟 [핵심] 이 장부를 세션 서랍(HttpSession)에 저장! 
            // 이래야 다음 요청(주식 조회 등)에서 문지기가 기억을 합니다.
            securityContextRepository.saveContext(context, request, response);

            return "로그인 완료";
        } else {
            return "아이디 또는 비밀번호가 틀렸습니다";
        }
    }
}