Cyber Finance (주식 시세 및 포트폴리오 관리 시스템)

Spring Boot와 외부 API를 연동하여 주식 마스터 데이터를 관리하고 실시간 시세를 조회하는 백엔드 애플리케이션입니다.

🛠 기술 스택

Language: Java 17

Framework: Spring Boot 3.x

Database: H2 Database (In-Memory/File)

ORM: Spring Data JPA

External API: Alpha Vantage API

Environment: WSL2 (Ubuntu 22.04), VS Code

✨ 주요 기능

주식 마스터 데이터 관리: 서버 기동 시 stocks.json을 읽어 DB(StockMaster)에 자동 동기화

실시간 시세 조회: Alpha Vantage API를 연동하여 미국 주식 실시간 가격 조회

환율 적용: USD 데이터를 서버 내부 설정된 환율에 따라 KRW로 변환하여 제공

회원 관리: 사용자 가입 및 로그인 기능

데이터 정규화: 주식 정보(Master)와 사용자 보유 정보(Portfolio)를 분리한 실무형 아키텍처

🚀 실행 방법

프로젝트 클론

src/main/resources/application.properties 파일 생성 및 설정 (API Key 등)

빌드 및 실행

./mvnw clean spring-boot:run


⚙️ 설정 (Configuration)

보안을 위해 API Key는 코드에 직접 포함하지 않고 외부 설정을 통해 관리합니다.

MarketDataService.java 내의 apiKey 변수에 발급받은 Alpha Vantage API 키를 입력해야 합니다.

(향후 개선 예정: 환경 변수 또는 application-local.properties로 분리)

📁 프로젝트 구조

src/main/java/com/example/demo/
├── controller/     # API 엔드포인트 (Stock, User)
├── service/        # 비즈니스 로직 (MarketData, Stock, User)
├── domain/         # Entity 및 Repository (Database)
└── dto/            # 데이터 전송 객체 (Response/Request)


⚠️ 주의 사항

API 제한: Alpha Vantage 무료 키는 1분당 5회, 1초당 1회의 호출 제한이 있습니다.

데이터 부재: 한국 주식(.KS)의 경우 무료 플랜에서 데이터가 누락될 수 있어 미국 주식 위주로 구성되어 있습니다.