# 대한제분 HR 구현2 INC-00 — 개발조직 인계서

발행: 2026-10-12 | 상태: **SOURCE PREPARED / ENV BLOCKED / COMPILE NOT RUN / BOOT NOT RUN**

## 1. 이번 회차 목적과 범위
기존 SaaS 3.5.16 원본 13개 모듈을 대상으로 첫 실제 기동에 필요한 의존성을 찾고, 원본 무결성과 재현 가능한 작업 복사본을 검증했다. HR3 Product Core 및 39개 자산은 모듈별 후보/설계 의미를 선별 적용한다. HRP 구현은 이번 범위에서 제외한다. 기존 업무 Rule, Schema, 인증/권한, 급여/근태 마감은 변경하지 않았다.

## 2. 원본 및 변경 분류
- 원본 기준: `saas-ustra-hr-master.zip`, 148,996,506B, SHA256 `0681abdd0a320315e0a3eea49468bd524f0faa131f47680eff952c9538a4b026`.
- 소스 파일 변경: **0건**. 기존 Gradle/Java/MyBatis/Thymeleaf 파일 일괄 개정 없음.
- 신규 파일: 실행 복구·SHA 점검 도구, OS별 빌드/기동 스크립트, 테스트, 로그 마스킹, 검증표·업무 범위 추적표, 이 인계서.
- HR3 재사용: 후보만 관리, 실제 Target 병합 0건. 기존 Core R18/W3E 복원 텍스트는 바이트 동일성이 확인되지 않아 자동 적용 금지.

## 3. 이번 회차 실제 테스트 결과
| 항목 | 결과 | Evidence |
|---|---|---|
| 원본 ZIP 해시 | PASS | `02_EVIDENCE/source_preflight.json` |
| ZIP 구조·필수 Wrapper/13모듈 | PASS | `source_preflight.json` |
| 별도 폴더 안전 추출 | PASS | 7,375개 실제 파일 (디렉터리 제외) |
| Python 검사 스크립트 테스트 | PASS 4/4 | 동일/탈출경로/루트이탈/링크 검증 |
| Gradle Wrapper `--version` | BLOCKED, Exit 1 | `actual_gradle_wrapper.REDACTED.log` — DNS services.gradle.org |
| 실행 가이드 `run_compile.sh` | ENV BLOCKED, Exit 20 | Wrapper 다운로드 실패; 컴파일 작업 미진입 |
| Framework 5모듈 compileJava | NOT RUN | Gradle 배포본 불가 |
| 실제 웹 bootWar / bootRun | NOT RUN | 위 환경 차단 |
| Login/DB/권한/그리드 Runtime | NOT RUN | 승인 테스트 DB·인증·Grid 엔진 미확보 |
| Sandbox 기동 안전가드 | PASS (기본 차단) | 환경 승인값 없으면 Exit 30, 실제 서버 기동 없음 |

## 4. 발견한 최초 실행의 실제 전제조건
- `gradlew`, `gradlew.bat`, `gradle-wrapper.jar` 및 버전 지정은 포함. **Gradle 8.14.5 바이너리 배포본**은 Wrapper가 네트워크 또는 회사 내부 Mirror에서 확보해야 한다.
- `build.gradle`에는 사내 Maven 저장소와 Homtax·전자서명 계열 의존성이 있다. 개발조직의 접근권한과 SSL 인증을 검증해야 한다. 보안과 라이선스 승인 없이 JAR를 ZIP에 복제하지 않는다.
- Thymeleaf 실행클래스는 `spring.config.location`을 설정하는 **커스텀 Bootstrap**이다. 프로파일 `core,web,api,local` 및 격리 DB를 실제로 확인한다.
- `application-api.yml`에 다중 환경·DB/Jasypt/메일/파일 설정이 있으며 안전하지 않은 대상 연결 우려를 제거해야 한다. 비밀번호/운영 데이터는 패키지에 재기록하지 않는다.
- `ustra-hr-system-thymeleaf/build.gradle`에 `bootWar`가 정의되어 있다. CI가 참조하는 `deploy.sh`는 소스 ZIP에 없으므로 실제 개발/배포서버 파일을 확인한다.
- 그리드는 향후 데이터밀도 높은 업무에 U.STRA RealGrid Wrapper 적용; 별도 엔진/라이선스/브라우저 통합 확인 필요. 일반 화면의 첫 기동은 기존 Thymeleaf 로그인 화면으로 한다.

## 5. 개발조직 최소 실행 작업 지시
1. 보안 승인된 개발 PC/내부 CI에 JDK21, Gradle 8.14.5 배포본, 사내 Repo 접근을 준비.
2. 본 패키지 풀고 `preflight.py`로 원본 SHA를 확인해 **격리된 작업복사본** 생성.
3. `run_compile.sh`(Windows: `run_compile.ps1`) 실행. Wrapper→Framework 5개→웹 `bootWar` 단계별 Exit Code·로그 확보.
4. 승인된 MariaDB11.4 시험 DB·테스트 계정·Secrets 공급·전용 경로 및 모든 외부 Endpoint 격리 후 처음으로 `bootRun` 시도.
5. `/login`→서버측 인증·인가→조직·사원 조회→실제 시험 DB를 확인. 수정/배포한 파일은 Diff와 SHA를 기록.
6. 민감정보를 제거한 로그와 재현 명령/실제 누락 의존성을 인계; 오류 원인과 수정 사유를 분류. 원본은 변경하지 않음.

## 6. Next Increment (INC-01)
- 현재 검증된 소스 기준으로 조직·사원 조회 1개 수직 업무 흐름을 선정. 실제 existing Controller/Service/Mapper/DB/권한을 Source-first로 검토하고 최소 변경.
- 요구사항과 수용기준, 법인·조직 Scope 및 실 Schema가 확인된 범위만 운영 후보 구현.
- Framework·보안·DB, 고객 정책 미확정은 HOLD, 독립 가능 UI/계약 도식은 병렬로 설계하되 완료율을 올리지 않음.
- 다음 통합 ZIP에는 수정된 소스만 누적하고 변경 파일/테스트 Evidence를 Manifest에 추가. 이 패키지의 원본 ZIP SHA는 그대로 고정.

## 7. 진행 상태 및 GDrive
- 현재: Phase 0/INC-00, 기능 구현률 미산정(대상 BPD 중 HRP 제외 분모 확정 필요), 검증 완료율 미산정, 실행 준비도 `SOURCE_PREPARED / GRADLE_BLOCKED`.
- Google Drive `1.대한제분 인사시스템/4.구현2/01_최신통합본`에 최신 통합 후보 ZIP, `02_개발인계`에 이 인계서를 보관한다. 이전 패키지는 삭제하지 않고 `03_이전버전`에 별도 이력 보존.
- 이 인계서와 ZIP 자체는 운영 반영 승인·빌드성공·서버 기동성공 증명이 아니다.
