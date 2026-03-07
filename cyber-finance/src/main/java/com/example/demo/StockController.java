package com.example.demo;

import com.example.demo.domain.StockMaster;
import com.example.demo.dto.StockResponseDto;
import com.example.demo.service.MarketDataService;
import com.example.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final MarketDataService marketDataService;

    @GetMapping
    public List<StockResponseDto> getAllStocks() {
        // 1. DB에서 모든 주식 정보를 가져옵니다. (이미 스케줄러가 livePrice를 채워둠)
        List<StockMaster> masters = stockService.getAllStockMasters();
        
        // 2. 환율 정보를 가져옵니다. (환율도 DB나 캐시에서 가져오도록 설계하는 것이 좋습니다)
        double rate = marketDataService.getUsdToKrwRate();
        
        List<StockResponseDto> results = new ArrayList<>();

        for (StockMaster m : masters) {
            // 🌟 [수정 포인트] marketDataService.getLivePrice(m.getTicker()) 대신 
            // m.getLivePrice()를 직접 사용합니다. (Thread.sleep도 삭제!)
            results.add(new StockResponseDto(m, m.getLivePrice(), rate));
        }

        log.info("주식 시세 조회 완료 (조회된 종목 수: {})", results.size());
        return results;
    }

    @GetMapping("/exchange-rate")
    public double getExchangeRate() {
        return marketDataService.getUsdToKrwRate(); // 1350.0 같은 값을 반환하게 됨
    }
}