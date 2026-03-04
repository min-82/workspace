package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저와 주식 마스터를 연결하는 '보유 주식(Portfolio)' 테이블입니다.
 * 실무에서는 "누가 어떤 주식을 몇 개 가졌나"를 여기서 관리합니다.
 */
@Entity
@Getter @Setter
@NoArgsConstructor
public class UserStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_master_id")
    private StockMaster stockMaster;

    private Double quantity; // 보유 수량
    private Double averagePrice; // 평균 매수 단가

    public UserStock(User user, StockMaster stockMaster, Double quantity, Double averagePrice) {
        this.user = user;
        this.stockMaster = stockMaster;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
    }
}