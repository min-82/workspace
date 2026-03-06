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
| **API Docs** | Swagger / OpenAPI 3.0 |
| **External API** | Alpha Vantage API, ExchangeRate-API |

---

## ✨ Key Features

### 1. 보안 및 회원 관리 (Security & User)
* **BCrypt 암호화**: `BCryptPasswordEncoder`를 사용하여 사용자 비밀번호를 안전하게 해싱하여 저장합니다.
* **세션 기반 인증**: Spring Security와 `HttpSession`을 연동하여 로그인 상태를 유지하고 권한을 검증합니다.
* **CSRF 방어 설정**: API 환경에 최적화된 보안 설정을 적용하였습니다.

### 2. 주식 및 데이터 관리 (Market Data)
* **마스터 데이터 동기화**: 서버 기동 시 `stocks.json`을 파싱하여 DB(`StockMaster`)에 자동 업데이트합니다.
* **실시간 시세 조회**: Alpha Vantage API를 연동하여 미국 주식의 최신 가격을 가져옵니다.
* **환율 자동 변환**: 외부 환율 API를 통해 `USD` 데이터를 실시간 `KRW` 가격으로 변환하여 제공합니다.

### 3. 실무형 아키텍처 (Architecture)
* **데이터 정규화**: 주식 정보(Master)와 사용자의 보유 정보(Portfolio)를 분리하여 데이터 무결성을 보장합니다.
* **Swagger API 문서화**: 모든 API 엔드포인트를 시각적으로 확인하고 테스트할 수 있는 환경을 구축했습니다.

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
spring.datasource.username=sa
spring.datasource.password=

# Hibernate settings
spring.jpa.hibernate.ddl-auto=update

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
├── service/        # 비즈니스 로직 및 외부 API 연동
├── domain/         # Entity 클래스 및 Repository 인터페이스
└── dto/            # 데이터 전송 객체 (Request/Response)
```

---

## 🔍 API Documentation (Swagger)
서버 실행 후 아래 주소에서 API를 직접 테스트해 볼 수 있습니다.
* **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
* **H2 Console**: `http://localhost:8080/h2-console`
  * JDBC URL: `jdbc:h2:file:./data/cyberfinance`
  * User Name: `sa` / Password: (없음)

---

## 🛠 Troubleshooting: Spring Security 6 Session Issue
프로젝트 개발 중, 로그인을 성공했음에도 불구하고 다음 API 요청 시 `403 Forbidden` 에러가 발생하는 이슈를 겪었습니다.

* **원인**: Spring Security 6(Spring Boot 3)부터는 수동 로그인 시 `SecurityContextHolder`에 인증 정보를 넣는 것만으로는 부족하며, 이를 세션 저장소(`SecurityContextRepository`)에 명시적으로 저장해야 함을 확인했습니다.
* **해결**: `HttpSessionSecurityContextRepository`를 사용하여 인증 성공 시 `saveContext` 메서드를 호출하도록 `UserController` 로직을 개선하여 세션 유지를 성공시켰습니다.

---

## ⚠️ Limitations
* **API Rate Limit**: Alpha Vantage 무료 플랜 특성상 분당 5회 호출 제한이 있습니다.
* **Data Scope**: 현재 미국 주식 데이터를 중심으로 구성되어 있으며, 향후 국내 주식 데이터를 추가할 예정입니다.