# 💹 Cyber-Finance
> **Spring Boot 기반 실시간 주식 시세 조회 및 포트폴리오 관리 시스템**

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-Auth-blue?style=flat-square&logo=springsecurity)

## 👋 Hello there!

I am a student developer currently on a learning level. As I am still in the early stages of my career, I may have some mistakes or areas for improvement. Any constructive feedback or suggestions would be greatly appreciated! Thank you for looking at my project.

---

## 🛠 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x, Spring Security |
| **Build Tool** | Maven (./mvnw) |
| **Database** | H2 Database (File-based Persistence) |
| **ORM** | Spring Data JPA (Hibernate 7.x) |
| **Fronted** | Vanilla JS, HTML5, CSS3 (Smooth UI) |
| **External API** | Alpha Vantage API, ExchangeRate-API |

---

## ✨ Key Features

### 1. 보안 및 회원 관리 (Security & User)
* **BCrypt 암호화**: `BCryptPasswordEncoder`를 사용하여 사용자 비밀번호를 안전하게 해싱하여 저장합니다.
* **세션 유지 최적화**: Spring Security 6의 `SecurityContextRepository`를 명시적으로 제어하여 로그인 세션 끊김 문제를 해결했습니다.
* **회원별 자산 관리**: 각 사용자별로 독립된 현금 잔고와 포트폴리오 데이터를 유지합니다.

### 2. 주식 거래 및 포트폴리오 (Trading & Portfolio)
* **매수/매도 로직**: 현재 시세와 환율을 실시간으로 계산하여 잔고 부족 체크 및 보유 수량 검증 후 거래를 처리합니다.
* **실시간 원화(KRW) 환산**: 미국 주식(USD) 자산을 현재 환율로 직시 계산하여 총 평가금액을 원화로 가시화합니다.
* **통합 대시보드**: 예수금과 주식 평가금액을 합산한 총자산 현황을 실시간으로 제공합니다.

### 3. 사용자 경험 (UX/UI)
* **Smooth UI 인터랙션**: 상단 메뉴 클릭 시 해당 섹션으로 이동하는 스크롤 기능을 구현했습니다.
* **반응형 데이터 바인딩**: DTO와 자바스크립트의 정교한 매핑을 통해 거래 성공 시 화면 새로고침 없이 자산 정보가 갱신됩니다.

---

## 🚀 Getting Started

### Prerequisites
* Java 17 이상
* Alpha Vantage API Key ([무료 발급받기](https://www.alphavantage.co/support/#api-key))

### Configuration (`src/main/resources/application.properties`)
보안을 위해 API 키는 외부 설정을 통해 관리합니다.

```properties
# Database Configuration
spring.datasource.url=jdbc:h2:file:./data/cyberfinance
spring.datasource.driverClassName=org.h2.Driver

# External API Key (예시)
app.api.alpha-vantage.key=YOUR_API_KEY_HERE
```

### Execution
```bash
./mvnw clean spring-boot:run
```

---

## 📁 Project Structure
```text
src/main/java/com/example/demo/
├── config/         # Security, Swagger 관련 설정 클래스
├── controller/     # API 엔드포인트 정의 (Stock, User)
├── service/        # 비즈니스 로직 (외부 API 연동, 환율 계산 등)
├── domain/         # Entity (User, StockMaster, Portfolio, Order)
├── dto/            # 데이터 전송 객체 (PortfolioResponseDto, OrderRequestDto 등)
└── repository/     # JPA 데이터 접근 계층
```

---

## 🛠 More Things to Do

* **국내 주식 데이터 추가**
* **거래 수수료 및 세금 로직 추가**
* **차트 기능 추가**
* **배포**

---

## 2026-03-01 ~ 2026-03-07 (Project Reflection)

Although it was a short period of just one week, this was the very first project of my life. Since I worked on it during the first week of the semester, I couldn't dedicate entire days to it, but I spent almost every spare moment focused on this project. As it was my first time, I feel some disappointment with the results due to my currently shallow knowledge in this field.

My motivation for starting this project was to test the limits of my capabilities. Inspired by the remarkable advancements in AI, I wanted to see how far I could go in web development as a CS major who still lacks practical project experience. Reflecting on the process, there were times when using AI actually consumed more time; however, I concluded that I could never have completed a project of this scale without its assistance. A significant portion of time was wasted because I relied on chat-based AI rather than AI agents, which led to a lack of consistency across the program. I initially avoided agents out of fear that I wouldn't be writing any code myself, but in hindsight, that decision prevented me from maximizing development efficiency.

Because of the high reliance on AI, I don't feel that my raw coding skills improved significantly through this project. While I did check my understanding by taking quizzes and descriptive evaluations from the AI, I still feel a lack of deep understanding regarding the granular details beyond the core features and architecture.

Looking back, I also feel that I might have chosen an overly ambitious topic for a first project. Since security is paramount for financial websites, I spent a vast amount of time researching related materials, which made the overall functionality of the code feel quite heavy. While security is an essential element of backend development, I wonder if a topic that allowed me to focus more on handling diverse features and data would have been more appropriate for a first attempt.

For these reasons, I am bringing this project to a close. For my next project, I want to select a more suitable topic and proceed within a properly established integrated development environment. As I am still in the learning stage, there may be many shortcomings. Thank you for taking the time to read this. ^^
