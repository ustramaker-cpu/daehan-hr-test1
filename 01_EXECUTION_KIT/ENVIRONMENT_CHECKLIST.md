# 대한제분 HR INC-00 — 실행환경 준비 체크리스트

**민감정보를 이 파일에 작성하지 마십시오.** 실행 프로파일 및 실제 데이터 접속 정보는 승인된 Secret Manager/환경변수에서만 공급합니다.

| Gate | 확인 대상 | 합격 조건 | 실패 판정 |
|---|---|---|---|
| R-01 | JDK 21 | 개발자 OS에서 `java -version` = 21 | ENV BLOCKED |
| R-02 | Gradle 8.14.5 | Wrapper 배포본 로딩 / `./gradlew --version` 성공 | ENV BLOCKED |
| R-03 | 사내 Gradle/Maven Repository | 사내 인증·TLS 신뢰체인·필요 라이브러리 접근 | DEPENDENCY BLOCKED |
| R-04 | 소스 무결성 | 원본 ZIP SHA-256 정확히 일치 | SOURCE HOLD |
| R-05 | Framework 5모듈 | `compileJava` 실제 성공, 로그와 Exit Code 확보 | COMPILE FAIL/BLOCKED |
| R-06 | 웹 산출물 | `:ustra-hr-system-thymeleaf:bootWar` 실제 성공 | BUILD FAIL/BLOCKED |
| R-07 | 실행 테스트 서버 | OS·포트·경로·허용 외부 네트워크 확정 | RUNTIME HOLD |
| R-08 | 테스트 DB | MariaDB 11.4, 실제 Schema/필요 초기 데이터 및 읽기/쓰기 범위 승인 | DB HOLD |
| R-09 | 프로파일·암호화 | `core,web,api,local`, Jasypt·키/암호화 방식 승인 및 외부화 | CONFIG/SECURITY HOLD |
| R-10 | 인증·인가 | 테스트 계정과 법인/조직/역할/사용자 범위 검증 | SECURITY HOLD |
| R-11 | 외부 연동 | 메일·스케줄러·배치·파일·ERP/GW 호출이 격리되었음 | EGRESS HOLD |
| R-12 | 기본 UI | `/login` 표시 / 실제 테스트 사용자 로그인 / 화면→API→Service→DB | FUNCTION HOLD |
| R-13 | 데이터 그리드 | 본 Increment에서는 적용하지 않음; 추후 브라우저 엔진·권한 검증 | LATER |
| R-14 | 배포 방식 | 기존 Jenkins가 참조하는 `deploy.sh` 실물과 배포 대상 확인 | DEPLOY HOLD |

**개발자 제출물:** 실제 실행 명령, OS/JDK, Gradle 배포본/Repo 접근 상태, 종료 코드, 실패 위치, 비식별화 로그, 기동/로그인/DB 테스트, 빠진 라이브러리 목록과 공급 경로, 소스 수정 내역/해시. 테스트 자격증명·사원정보·고객 DB 덤프는 제출하지 않습니다.

### 위험한 단축 경로 금지
- `-x test`로 성공 선언, 운영 DB에 임시 연결, 인증/권한 제거, 암호화 해제, 고객 승인 없는 DDL 적용, Boot 4.1 전체 적용 금지.
- 소스의 `application-api.yml`에는 환경별 설정이 있으며 사용자 데이터가 들어 있는 DB의 상세 내용을 재사용하지 않습니다.
- 원본 `framework`·`core`·`thymeleaf`에 동시에 대량 패치를 적용하지 않습니다. 실패 사유별로 파일 단위 최소 Diff만 작성합니다.
- 테스트 계정·키·암호화 비밀을 인계 ZIP에 넣지 않습니다.
