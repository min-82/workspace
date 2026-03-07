package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 🌟 로그 추가
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    // 🌟 요청 데이터를 담을 공통 규격
    public record AuthRequest(String username, String password) {}

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) { // @RequestBody 유지
        log.info("회원가입 시도: {}", request.username());
        userService.register(request.username(), request.password());
        return "회원가입 완료";
    }

    @PostMapping("/login")
    public String login(
            @RequestBody AuthRequest request, // 🌟 @RequestParam에서 @RequestBody로 변경!
            HttpServletRequest httpRequest, 
            HttpServletResponse httpResponse 
    ) {
        log.info("로그인 시도: {}", request.username());
        boolean isSuccess = userService.login(request.username(), request.password());

        if (isSuccess) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    request.username(), 
                    null, 
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, httpRequest, httpResponse);

            return "로그인 완료";
        } else {
            return "아이디 또는 비밀번호가 틀렸습니다";
        }
    }
    
    @GetMapping("/me")
    public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "guest";
        }
        return auth.getName();
    }
}