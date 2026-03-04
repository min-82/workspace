package com.example.demo;

import com.example.demo.domain.StockMaster;
import com.example.demo.dto.StockResponseDto;
import com.example.demo.service.MarketDataService;
import com.example.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final MarketDataService marketDataService;

    @GetMapping("/api/stocks")
    public List<StockResponseDto> getAllStocks() {
        List<StockMaster> masters = stockService.getAllStockMasters();
        double rate = marketDataService.getUsdToKrwRate();
        List<StockResponseDto> results = new ArrayList<>();

        for (StockMaster m : masters) {
            double livePrice = marketDataService.getLivePrice(m.getTicker());
            results.add(new StockResponseDto(m, livePrice, rate));

            // [핵심] 무료 API의 1초당 1회 제한을 피하기 위해 1.1초간 대기합니다.
            try {
                Thread.sleep(1100); 
            } catch (InterruptedException e) {
                log.error("대기 중 오류 발생: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        return results;
    }
}