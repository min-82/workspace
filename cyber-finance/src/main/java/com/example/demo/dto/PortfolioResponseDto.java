package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class PortfolioResponseDto {
    private String username;
    private double cashBalance;      // 보유 현금
    private double totalStockValue;  // 총 주식 가치
    private List<StockHold> holdings; // 보유 종목 상세

    @Getter
    @AllArgsConstructor
    public static class StockHold {
        private String stockName;
        private String ticker;
        private int quantity;
        private double evaluationAmount;
    }
}