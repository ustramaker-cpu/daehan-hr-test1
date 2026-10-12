# Release Gate: Candidate != Runtime Verified

| Gate | 현재 상태 | 해제에 필요한 증거 |
|---|---|---|
| G-01 Source Integrity | PASS | 원본 SHA256 0681abdd... 일치, 9,048개 ZIP entry, Wrapper/13 module settings 존재 |
| G-02 Gradle Toolchain | BLOCKED | Gradle 8.14.5 배포본 로딩 및 버전 로그 |
| G-03 Framework compile | NOT RUN | 5모듈 컴파일 로그/Exit Code |
| G-04 Web build | NOT RUN | Thymeleaf `bootWar` 로그/산출물 |
| G-05 Dev Boot | NOT RUN | 격리 환경 기동 로그/포트/응답 |
| G-06 Auth + DB | NOT RUN | 역할·법인·조직·대상 객체 및 DB 실행 증거 |
| G-07 HR3 code promotion | HOLD | 승인 설계·실제 Target Diff·기존 동작 회귀 PASS |
| G-08 Customer UAT | HOLD | 대한제분 업무정책·Golden 확인/승인 |

Spring Boot 4.1.1의 채택은 별도 기술 검증/고객 승인 전 HOLD. HRP는 프로젝트 범위에서 제외. 급여·근태 업무 정책과 실 데이터·DDL은 이 Increment에서 수정/생성하지 않음.
