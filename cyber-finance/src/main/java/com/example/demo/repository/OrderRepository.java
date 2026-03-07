package com.example.demo.repository;

import com.example.demo.domain.StockOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<StockOrder, Long> {
}