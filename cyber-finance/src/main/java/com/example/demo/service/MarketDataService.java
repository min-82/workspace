package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j // 로그 출력을 위한 어노테이션
@Service
public class MarketDataService {
    private final String apiKey = "AGSZIHW8AREEX5J7";
    private final RestTemplate restTemplate = new RestTemplate();

    public double getLivePrice(String ticker) {
        String url = String.format("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s", ticker, apiKey);

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            // [중요] API가 실제로 보낸 응답 전체를 터미널에 찍어봅니다.
            log.info("Ticker: {} 조회 결과 -> {}", ticker, response);

            if (response != null && response.containsKey("Global Quote")) {
                Map<String, String> quote = (Map<String, String>) response.get("Global Quote");
                if (quote != null && !quote.isEmpty()) {
                    String priceStr = quote.get("05. price");
                    return priceStr != null ? Double.parseDouble(priceStr) : 0.0;
                }
            }
            
            // 만약 API 한도 초과 메시지가 오면 여기에 찍힙니다.
            if (response != null && response.containsKey("Note")) {
                log.warn("API 호출 한도 초과(1분 5회) 메시지가 감지되었습니다.");
            }

        } catch (Exception e) {
            log.error("Ticker {} 조회 중 예외 발생: {}", ticker, e.getMessage());
        }
        return 0.0;
    }

    public double getUsdToKrwRate() {
        // 기존 환율 로직 (생략)
        return 1400.0;
    }
}