# 대한제분 HR 구현2 — INC-00 실행 Baseline 복구 / 첫 실행 인계

**인계 상태: SOURCE PREPARED / GRADLE DOWNLOAD BLOCKED / COMPILE NOT RUN / BOOT NOT RUN**

이 ZIP은 `saas-ustra-hr-master.zip`의 무결한 원본 바이트와 실행 복구 도구·결과·개발 인계서를 포함한 **첫 통합 기준 후보**입니다. HR3 전체 소스를 병합한 운영품 또는 실행 검증 완료본이 아닙니다.

## 최초 읽기 순서
1. `00_START_HERE.md` (이 문서)
2. `01_EXECUTION_KIT/COMMANDS.md`
3. `01_EXECUTION_KIT/ENVIRONMENT_CHECKLIST.md`
4. `01_EXECUTION_KIT/RELEASE_GATES.md`
5. `02_EVIDENCE/source_preflight.json`, `02_EVIDENCE/actual_gradle_wrapper.REDACTED.log`
6. `03_HR3_REUSE/REUSE_CANDIDATES.md`, `03_HR3_REUSE/WHOLE_SCOPE_TRACKER.md`
7. `04_DOCUMENTS/INC00_DEVELOPER_HANDOFF.md` 또는 Word 인계서

## 반드시 지킬 것
- `SOURCE_REFERENCE/saas-ustra-hr-master.zip`를 해시 비교 후 격리 작업 공간에 풀고, 원본을 직접 변경하지 않습니다.
- 소스 전체가 있는 것은 Gradle 사내의존성, Jasypt, DB Schema, 외부 엔진/라이선스, 고객 승인 데이터까지 모두 준비됐다는 의미가 아닙니다.
- 임의로 Boot 4.1로 업그레이드하거나 HR3 Candidate 패치를 기계적으로 적용하지 않습니다.
- HRP는 이번 구현에서 제외. UI는 웹 기본, 그리드는 U.STRA 래퍼를 대량업무에만 단계적 검증 후 적용.
- 운영 DB, 개인정보, 자격증명, 실제 급여·마감·소급 계산을 시연 목적으로 연결하거나 가정하지 않습니다.

## 원본 증거
- `saas-ustra-hr-master.zip` = SHA-256 `0681abdd0a320315e0a3eea49468bd524f0faa131f47680eff952c9538a4b026`
- Java 21 / Spring Boot 3.5.16 / Gradle 8.14.5 / 13개 모듈 / wrapper JAR 포함.
- 실제 Gradle Wrapper 시도에서 `UnknownHostException: services.gradle.org`, Exit Code 1 → ENV BLOCKED.
- `bootRun`, Framework 5모듈 컴파일, 로그인/API/DB, 배포: **NOT RUN**.

## 개발자 다음 액션
1. 승인된 내부 네트워크에서 Gradle 8.14.5 배포본과 사내 Repository 접근 준비.
2. `01_EXECUTION_KIT/COMMANDS.md`에 따라 Wrapper → 5모듈 → 웹 `bootWar` 순으로 실행.
3. 테스트 MariaDB와 Secret/프로파일/외부 호출 격리를 확인한 뒤 웹 기동.
4. `*.exitcode`와 비식별 로그, 기동 및 로그인/DB 결과, 실제 누락된 사내 JAR 정보 반환.
5. 이 증거를 기준으로 INC-01 조직/사원 조회 수직 Slice를 최소변경 개발.
