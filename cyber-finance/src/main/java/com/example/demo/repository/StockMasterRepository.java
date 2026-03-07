package com.example.demo.repository;

import com.example.demo.domain.StockMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StockMasterRepository extends JpaRepository<StockMaster, Long> {
    boolean existsByTicker(String ticker);
    Optional<StockMaster> findByTicker(String ticker);
}