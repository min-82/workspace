package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.event.EventListener; // 🌟 추가
import org.springframework.boot.context.event.ApplicationReadyEvent; // 🌟 추가
import java.util.List;
import java.time.LocalDateTime;

import com.example.demo.domain.StockMaster;
import com.example.demo.repository.StockMasterRepository; // 🌟 추가 (경로 확인 필요)

@Service
public class StockUpdateService {

    @Autowired
    private StockMasterRepository stockMasterRepository;

    // 1️⃣ [진짜 API 업데이트] 매시간 정각에만 실행 (API 제한 보호)
    @Scheduled(cron = "0 0 * * * *")
    public void updateRealPricesFromApi() {
        System.out.println("📢 정각입니다. Alpha Vantage API에서 진짜 시세를 가져옵니다.");
        List<StockMaster> stocks = stockMasterRepository.findAll();
        for (StockMaster stock : stocks) {
            // 진짜 API 호출 로직 (하루 25번 제한 안에서만 실행됨)
            double realPrice = fetchPriceFromApi(stock.getTicker()); 
            if (realPrice > 0) {
                stock.setLivePrice(realPrice);
                stockMasterRepository.save(stock);
            }
        }
    }

    // 2️⃣ [가짜 시세 시뮬레이션] 10초마다 실행 (사용자에게 '살아있는' 화면 제공)
    // 이 작업은 API를 전혀 호출하지 않으므로 무제한으로 돌려도 됩니다!
    @Scheduled(fixedRate = 10000)
    public void simulateLiveMovement() {
        List<StockMaster> stocks = stockMasterRepository.findAll();
        for (StockMaster stock : stocks) {
            // 현재 DB에 저장된 가격을 기준으로 ±0.05% 정도만 살짝 움직임
            double currentPrice = stock.getLivePrice();
            if (currentPrice <= 0) currentPrice = 150.0; // 데이터가 0이면 초기값 부여

            double move = (Math.random() * 0.001) - 0.0005; // 아주 미세한 변동
            double nextPrice = Math.round(currentPrice * (1 + move) * 100) / 100.0;
            
            stock.setLivePrice(nextPrice);
            stockMasterRepository.save(stock); // DB만 업데이트
        }
    }

    // 3️⃣ [서버 시작 시 안전장치] 서버 켜졌을 때 DB가 비어있으면 기본값만 채움 (API 호출 X)
    @EventListener(ApplicationReadyEvent.class)
    public void initDbPrices() {
        List<StockMaster> stocks = stockMasterRepository.findAll();
        for (StockMaster stock : stocks) {
            if (stock.getLivePrice() <= 0) {
                stock.setLivePrice(150.0); // 일단 0이 아니게만 만듦
                stockMasterRepository.save(stock);
            }
        }
        System.out.println("✅ 서버 기동 완료: 모든 주식의 초기 시세가 설정되었습니다.");
    }

    private double fetchPriceFromApi(String ticker) {
        // 실제 API 호출 코드 (생략)
        return 0.0; 
    }
}