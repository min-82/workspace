package com.example.demo;

import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 🌟 주식 매수 요청을 받는 창구
    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestBody OrderRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            orderService.buyStock(username, request.ticker(), request.quantity());
            return ResponseEntity.ok("매수가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public record OrderRequest(String ticker, int quantity) {}

    // 🌟 주식 매도 요청을 받는 창구
    @PostMapping("/sell")
    public ResponseEntity<?> sell(@RequestBody OrderRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            orderService.sellStock(username, request.ticker(), request.quantity());
            return ResponseEntity.ok("매도가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}