package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class StockOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockMaster stock;

    private int quantity;
    private double price; // 거래 당시의 livePrice
    private LocalDateTime orderTime;

    public StockOrder(User user, StockMaster stock, int quantity, double price) {
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.orderTime = LocalDateTime.now();
    }
}