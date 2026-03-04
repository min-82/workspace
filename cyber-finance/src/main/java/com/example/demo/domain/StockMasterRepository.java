package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMasterRepository extends JpaRepository<StockMaster, Long> {
    boolean existsByTicker(String ticker);
    StockMaster findByTicker(String ticker);
}