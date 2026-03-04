package com.example.demo.service;

import com.example.demo.domain.StockMaster;
import com.example.demo.domain.StockMasterRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMasterRepository stockMasterRepository;
    private final ObjectMapper objectMapper;

    /**
     * 서버 시작 시 실행되어 stocks.json의 데이터를 DB 마스터 테이블에 동기화합니다.
     * 실무에서 기초 데이터를 세팅할 때 자주 사용하는 패턴입니다.
     */
    @PostConstruct
    @Transactional
    public void initStockMasterData() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/stocks.json");
            List<StockMaster> stocks = objectMapper.readValue(inputStream, new TypeReference<List<StockMaster>>() {});
            
            for (StockMaster s : stocks) {
                if (!stockMasterRepository.existsByTicker(s.getTicker())) {
                    stockMasterRepository.save(s);
                }
            }
            System.out.println("주식 마스터 데이터 동기화 완료.");
        } catch (Exception e) {
            System.err.println("데이터 로딩 중 에러 발생: " + e.getMessage());
        }
    }

    public List<StockMaster> getAllStockMasters() {
        return stockMasterRepository.findAll();
    }
}