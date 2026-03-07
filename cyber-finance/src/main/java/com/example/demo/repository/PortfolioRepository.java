package com.example.demo.repository;

import com.example.demo.domain.Portfolio;
import com.example.demo.domain.StockMaster;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
    Optional<Portfolio> findByUserAndStock(User user, StockMaster stock);
}