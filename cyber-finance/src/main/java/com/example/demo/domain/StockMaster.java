package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 모든 주식의 기본 정보를 담는 마스터 테이블입니다.
 * 실무에서는 이 데이터가 시스템의 기준이 됩니다.
 */
@Entity
@Getter @Setter
@NoArgsConstructor
public class StockMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ticker; // 티커는 유일해야 합니다.

    private String name;
    private String category;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    public StockMaster(String name, String ticker, String category, Currency currency) {
        this.name = name;
        this.ticker = ticker;
        this.category = category;
        this.currency = currency;
    }
}