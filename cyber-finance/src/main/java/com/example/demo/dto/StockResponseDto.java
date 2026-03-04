package com.example.demo.dto;

import com.example.demo.domain.Currency;
import com.example.demo.domain.StockMaster;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StockResponseDto {
    private String name;
    private String ticker;
    private String category;
    private double livePrice;
    private String currency;

    public StockResponseDto(StockMaster master, double price, double exchangeRate) {
        this.name = master.getName();
        this.ticker = master.getTicker();
        this.category = master.getCategory();
        this.currency = master.getCurrency().toString();
        
        // 달러일 경우 환율 적용 로직
        if (master.getCurrency() == Currency.USD) {
            this.livePrice = Math.round(price * exchangeRate);
        } else {
            this.livePrice = price;
        }
    }
}