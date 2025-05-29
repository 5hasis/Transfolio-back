<img src="https://capsule-render.vercel.app/api?type=waving&color=BDBDC8&height=150&section=header" />


---

## 📄 Transfolio-Back: 번역가 포트폴리오 관리 서비스 백엔드 (개발 중)

---

### 🚀 프로젝트 소개

**Transfolio-Back**은 번역가들이 자신의 번역 결과물을 **온라인 포트폴리오로 체계적으로 관리하고 전시**할 수 있도록 지원하는 서비스의 **백엔드 시스템**입니다. 현재 개발이 진행 중이며, 번역가들이 자신의 역량을 효과적으로 보여줄 수 있는 플랫폼을 구축하기 위해 사용자 관리, 번역 프로젝트 업로드 및 관리, 그리고 포트폴리오 조회를 위한 안정적인 API를 구축하고 있어요.

---

### 🛠️ 기술 스택

* **언어:** `Java`
* **프레임워크:** `Spring Boot`
* **빌드 도구:** `Gradle`
* **ORM:** `Spring Data JPA`
* **데이터베이스:** `MySQL` 
* **테스트:** `JUnit 5`, `Mockito` (Mock Test)
* **인증/보안:** `Spring Security`, `JWT` 
* **컨테이너:** `Docker` 

---

### 📁 프로젝트 구조 

├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── transfolio/
│   │   │               ├── common/ # 공통적으로 사용되는 유틸리티, 설정, 예외 처리 등
│   │   │               │   ├── config/
│   │   │               │   ├── error/
│   │   │               │   ├── filter/
│   │   │               │   └── ...
│   │   │               ├── domain/
│   │   │               │   ├── user/ # 사용자 계정 및 프로필 관리 관련 기능
│   │   │               │   │   ├── controller/
│   │   │               │   │   ├── service/
│   │   │               │   │   ├── dto/
│   │   │               │   │   └── ... # (repository, entity 등 도메인 핵심 요소 포함)
│   │   │               │   ├── board/ # 번역 게시물 관리 기능
│   │   │               │   │   ├── controller/
│   │   │               │   │   ├── service/
│   │   │               │   │   ├── dto/
│   │   │               │   │   └── ...
│   │   │               │   └── ... # 기타 도메인 (career, instr 등)
│   │   │               ├── security/ # 인증 및 인가 관련 로직 (Spring Security 설정, JWT 등)
│   │   │               │   └── ...
│   │   │               └── TransfolioBackApplication.java # Spring Boot 메인 애플리케이션 시작점
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── transfolio/
│       │               ├── user/ # 사용자 관련 테스트 (Mock Test 포함)
│       │               ├── board/ # 번역 게시물 관련 테스트 (Mock Test 포함)
│       │               └── ... # 기타 도메인/공통 모듈 테스트
│       └── resources/
├── Dockerfile # Docker 이미지 빌드 및 실행을 위한 설정 파일
├── build.gradle # Gradle 빌드 스크립트 (의존성, 태스크 정의)
└── README.md # 프로젝트 설명 문서

### 🚀 설치 및 실행 방법

이 프로젝트를 로컬 개발 환경에서 실행하려면 아래 단계를 따르세요.

1.  **저장소 클론:**
    ```bash
    git clone [https://github.com/5hasis/Transfolio-back.git](https://github.com/5hasis/Transfolio-back.git)
    cd Transfolio-back
    ```

2.  **환경 변수 설정:**
    * `src/main/resources/application.properties` 또는 `application.yml` 파일에 데이터베이스 연결 정보를 포함한 환경 변수를 설정해야 합니다.
    * Docker를 사용하는 경우, `docker-compose.yml` 파일을 작성하여 데이터베이스 컨테이너를 함께 실행할 수 있어요.

3.  **의존성 빌드:**
    ```bash
    ./gradlew clean build
    ```

4.  **테스트 실행:**
    ```bash
    ./gradlew test
    ```

5.  **애플리케이션 실행:**
    * JAR 파일 실행:
        ```bash
        java -jar build/libs/transfolio-back-0.0.1-SNAPSHOT.jar # 또는 프로젝트 빌드 결과 파일
        ```
    * IDE에서 실행: Spring Boot 애플리케이션을 IDE(IntelliJ IDEA, Eclipse 등)에서 직접 실행합니다.
    * **Docker로 실행:**
        ```bash
        docker build -t transfolio-back .
        docker run -p 8080:8080 transfolio-back
        ```

6.  **접속:** 서버가 `http://localhost:8080` (기본 Spring Boot 포트)에서 실행됩니다.

---

### 🚧 현재 개발 상태 및 진행 상황

* **진행 상황:** 현재 사용자 및 번역 프로젝트의 **CRUD(생성, 조회, 수정, 삭제) 기능의 백엔드 로직 구현**에 집중하고 있습니다.
* **테스트:** `JUnit 5`와 `Mockito`를 활용하여 주요 서비스 로직 및 컨트롤러에 대한 **단위 테스트 및 Mock 테스트**를 작성하며 코드의 견고함과 신뢰성을 확보하고 있어요.
* **완성 목표:** 번역가들이 자신의 작업물을 안정적으로 업로드하고, 필요한 정보를 함께 저장하며, 포트폴리오로 공개할 수 있는 최소한의 기능을 갖추는 것을 목표로 합니다.

---

### 💡 배운 점 및 성장 기록

이 Transfolio-Back 프로젝트를 개발하면서 다음과 같은 기술적 경험과 지식을 얻고 있습니다.

* **Spring Boot 기반 API 개발:** Java와 Spring Boot를 사용하여 **RESTful API를 설계하고 구현**하는 실질적인 경험을 쌓고 있어요. 컨트롤러, 서비스, 리포지토리 계층을 분리하여 애플리케이션의 구조적 견고함을 높이는 방법을 학습하고 있습니다.
* **JPA와 ORM 활용:** JPA를 통해 **객체지향적으로 데이터베이스를 다루는 방법**을 익히고 있어요. 엔티티 관계 설정, 쿼리 작성 등 ORM의 장점을 활용하여 개발 효율성을 높이는 방법을 배우고 있습니다.
* **관계형 데이터베이스 설계:** 번역 프로젝트와 사용자 간의 관계를 고려하여 **데이터베이스 스키마를 설계**하고, 이를 JPA 엔티티로 매핑하는 과정을 통해 데이터 모델링 능력을 향상시키고 있어요.
* **효율적인 테스트 코드 작성:** `JUnit 5`와 `Mockito`를 활용하여 서비스 계층의 비즈니스 로직을 격리하여 테스트하는 **Mock 테스트**를 작성하고 있습니다. 이를 통해 외부 의존성 없이 코드의 특정 부분을 검증하고, 변경에 유연하게 대처하는 능력을 기르고 있어요.
* **Gradle을 통한 빌드 및 의존성 관리:** **Gradle의 멀티모듈 프로젝트 구성 및 의존성 관리**를 경험하며 빌드 프로세스에 대한 이해를 높이고 있습니다.
* **Docker를 활용한 개발 환경 구축:** Docker를 사용하여 개발 환경을 컨테이너화하고, 의존성 문제를 해결하며, 배포 과정을 단순화하는 방법을 경험하고 있어요. 이는 개발 생산성 향상에 크게 기여하고 있습니다.

---
