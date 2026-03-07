package com.example.demo;

import com.example.demo.dto.PortfolioResponseDto;
import com.example.demo.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    // 🌟 내 포트폴리오 정보를 가져오는 창구
    @GetMapping
    public PortfolioResponseDto getMyPortfolio() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return portfolioService.getUserPortfolio(username);
    }
}