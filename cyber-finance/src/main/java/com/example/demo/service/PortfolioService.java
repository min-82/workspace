package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.dto.PortfolioResponseDto;
import com.example.demo.repository.PortfolioRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final MarketDataService marketDataService;

    public PortfolioResponseDto getUserPortfolio(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        List<Portfolio> holdings = portfolioRepository.findByUser(user);
        double currentRate = marketDataService.getUsdToKrwRate();
        
        // 🌟 에러 방지를 위해 합계 변수를 따로 선언
        double totalStockValueKrw = 0;
        List<PortfolioResponseDto.StockHold> stockHolds = new ArrayList<>();

        for (Portfolio p : holdings) {
            double price = p.getStock().getLivePrice();
            int qty = p.getQuantity();
            double value = price * qty;

            // USD 종목이면 원화 환산
            if (p.getStock().getCurrency() == Currency.USD) {
                value = value * currentRate;
            }

            // 전체 합계에 더하기
            totalStockValueKrw += value;

            // 리스트에 추가 (DTO 생성자 형식에 맞춰서 넣어주세요)
            stockHolds.add(new PortfolioResponseDto.StockHold(
                p.getStock().getName(),
                p.getStock().getTicker(),
                p.getQuantity(),
                (long) Math.round(value)
            ));
        }

        return new PortfolioResponseDto(
            user.getUsername(),
            user.getBalance(),
            Math.round(totalStockValueKrw),
            stockHolds
        );
    }
}