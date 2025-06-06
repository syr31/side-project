# side-project

# 📘 게시판 프로젝트 (Spring Boot 기반)

> 백엔드 중심의 게시판 프로젝트입니다. Spring Boot 기반 REST API 설계부터 인증, 예외 처리, API 문서화까지 핵심 기능 위주로 직접 구현하며 학습한 결과를 정리한 저장소입니다.

---

## ✅ 프로젝트 개요

- 사용자 인증이 포함된 게시판 백엔드 시스템
- JWT 기반 로그인/회원가입 구현
- 게시글 CRUD, 검색, 정렬, 페이징 처리
- QueryDSL을 활용한 복잡한 쿼리 구성
- Spring REST Docs를 통한 API 문서 자동화

---

## 🛠 기술 스택

- **Language**: Java 17
- **Framework**: Spring Boot 3.4.4, Spring Security, Spring REST Docs
- **Database**: MySQL
- **ORM**: JPA, QueryDSL
- **Build Tool**: Gradle
- **Documentation**: REST Docs
- **Version Control**: Git / GitHub

---

## 🧩 구현 기능

### 📌 사용자 인증
- 회원가입, 로그인 (JWT 토큰 발급)
- JWT 기반 인증/인가 처리
- Interceptor 및 ArgumentResolver 사용
- 토큰 만료/재발급 처리

### 📌 게시판 기능
- 게시글 등록, 조회, 수정, 삭제
- 게시글 페이징, 정렬, 검색 기능
- QueryDSL을 활용한 동적 쿼리

### 📌 예외 처리 및 보안
- 전역 예외 처리 핸들러 적용
- CORS 설정
- 인증 실패 및 권한 오류 처리

### 📌 API 문서화
- Spring REST Docs 적용
- 기본 응답, 인증 API, 게시글 API 문서화
- 문서 자동 생성 및 커스터마이징

---
📌 참고 사항
프론트엔드 및 배포는 간단히 실험 후 제외하였습니다.

전체 흐름은 백엔드 단독 실행 및 API 테스트를 통해 확인 가능합니다. (Postman 등)

## ▶️ 실행 방법

### 📦 1. 실행 전 준비

- Java 17 이상 설치 필요
- Git Bash 또는 CMD 사용 가능

---

### ⚙️ 2. JAR 빌드

cmd -> gradlew bootJar
성공 시, build/libs/ 폴더에 다음 파일이 생성됩니다.

hodolog-0.0.1-SNAPSHOT.jar

🚀 3. 애플리케이션 실행

cd build\libs
java -jar hodolog-0.0.1-SNAPSHOT.jar

