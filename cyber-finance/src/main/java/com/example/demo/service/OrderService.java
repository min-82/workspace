package com.example.demo.service;

import com.example.demo.domain.*; // User, StockMaster, Portfolio, StockOrder 포함
import com.example.demo.repository.*; // UserRepository, StockRepository, PortfolioRepository, OrderRepository 포함
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserRepository userRepository;
    private final StockMasterRepository stockMasterRepository;
    private final PortfolioRepository portfolioRepository;
    private final OrderRepository orderRepository;
    private final MarketDataService marketDataService;

    @Transactional
    public void buyStock(String username, String ticker, int quantity) {
        // 1. 유저 및 주식 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        StockMaster stock = stockMasterRepository.findByTicker(ticker)
                .orElseThrow(() -> new RuntimeException("종목을 찾을 수 없습니다."));

        // 2. 가격 계산 (DB의 실시간 시세 기준)
        double currentPrice = stock.getLivePrice();
        double totalCost = currentPrice * quantity;

        // 🌟 [추가] 1주 단위로만 매수 가능하도록 제한
        if (quantity % 1 != 0) {
            throw new RuntimeException("주식은 1주 단위로만 매수할 수 있습니다.");
        }

        if (quantity <= 0) {
            throw new RuntimeException("주식은 1주 이상 매수해야 합니다.");
        }

        if (stock.getCurrency() == Currency.USD) {
            double exchangeRate = marketDataService.getUsdToKrwRate(); // 환율 가져오기
            totalCost = totalCost * exchangeRate; // 달러 금액을 원화로 변환
            log.info("달러 주식 매수: {} * {} * 환율({}) = {}원", currentPrice, quantity, exchangeRate, totalCost);
        }

        // 3. 잔고 체크
        if (user.getBalance() < totalCost) {
            throw new RuntimeException("잔고가 부족합니다. 현재 잔고: " + user.getBalance());
        }

        // 4. 잔고 차감
        user.setBalance(user.getBalance() - totalCost);

        // 5. 포트폴리오 업데이트
        Portfolio portfolio = portfolioRepository.findByUserAndStock(user, stock)
                .orElse(new Portfolio(user, stock, 0));
        portfolio.setQuantity(portfolio.getQuantity() + quantity);
        portfolioRepository.save(portfolio);

        // 6. 거래 내역 저장
        orderRepository.save(new StockOrder(user, stock, quantity, currentPrice));
    }

    @Transactional
    public void sellStock(String username, String ticker, int quantity) {
        // 1. 유저 및 주식 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        StockMaster stock = stockMasterRepository.findByTicker(ticker)
                .orElseThrow(() -> new RuntimeException("종목을 찾을 수 없습니다."));

        // 2. 보유 수량 체크
        Portfolio portfolio = portfolioRepository.findByUserAndStock(user, stock)
                .orElseThrow(() -> new RuntimeException("보유 중인 주식이 없습니다."));

        if (portfolio.getQuantity() < quantity) {
            throw new RuntimeException("보유 수량이 부족합니다. 보유량: " + portfolio.getQuantity());
        }

        // 3. 가격 계산 (DB의 실시간 시세 기준)
        double currentPrice = stock.getLivePrice();
        double totalRevenue = currentPrice * quantity;

        // 🌟 [추가] 1주 단위로만 매도 가능하도록 제한
        if (quantity % 1 != 0) {
            throw new RuntimeException("주식은 1주 단위로만 매도할 수 있습니다.");
        }

        if (quantity <= 0) {
            throw new RuntimeException("주식은 1주 이상 매도해야 합니다.");
        }

        if (stock.getCurrency() == Currency.USD) {
            double exchangeRate = marketDataService.getUsdToKrwRate(); // 환율 가져오기
            totalRevenue = totalRevenue * exchangeRate; // 달러 금액을 원화로 변환
            log.info("달러 주식 매도: {} * {} * 환율({}) = {}원", currentPrice, quantity, exchangeRate, totalRevenue);
        }

        // 4. 잔고 증가
        user.setBalance(user.getBalance() + totalRevenue);

        // 5. 포트폴리오 업데이트
        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        if (portfolio.getQuantity() == 0) {
            portfolioRepository.delete(portfolio); // 0주가 되면 삭제
        } else {
            portfolioRepository.save(portfolio);
        }

        // 6. 거래 내역 저장 (매도는 음수로 기록)
        orderRepository.save(new StockOrder(user, stock, -quantity, currentPrice));
    }
}