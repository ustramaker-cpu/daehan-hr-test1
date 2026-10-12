# 실제 실행 가이드 (Gradle 환경이 있는 개발자 PC/서버에서 수행)

`SOURCE_REFERENCE/saas-ustra-hr-master.zip`은 **원본 그대로**입니다. 기존 `application-*.yml`과 사내 라이브러리를 임의로 바꾸지 마십시오.

## 1. 원본 검증과 안전한 작업복사본 추출

패키지 최상위에서 (Python 3 필요):

```bash
python3 01_EXECUTION_KIT/tools/preflight.py \
  --source-zip SOURCE_REFERENCE/saas-ustra-hr-master.zip \
  --extract-to ./workspace \
  --report ./evidence/source_preflight.json
```

정상: `PASS: source SHA and structural inventory checked`. 오류: 중단하고 원본/ZIP 경로를 다시 확인. **이 결과는 컴파일·기동 PASS가 아닙니다.** Windows에서는 `python` 명령을 사용합니다.

## 2. Framework 5개 모듈과 실제 웹 앱 컴파일

```bash
bash 01_EXECUTION_KIT/tools/run_compile.sh \
  "$(pwd)/workspace/saas-ustra-hr-master" \
  "$(pwd)/evidence"
```

Windows PowerShell:

```powershell
.\01_EXECUTION_KIT\tools\run_compile.ps1 -SourceDir "$PWD\workspace\saas-ustra-hr-master" -OutputDir "$PWD\evidence"
```

- Gradle 배포본/저장소에 접근하지 못하면 `GRADLE_WRAPPER_BLOCKED` 또는 `DEPENDENCY BLOCKED`로 보고합니다.
- Framework 5개 컴파일 완료 후에도 웹 `bootWar` 성공이 별도 필요합니다.
- `bootWar` 설정은 실제 `ustra-hr-system-thymeleaf/build.gradle`에 존재합니다. 기존 README에서 `bootJar` 언급도 확인되므로 배포산출물 선택은 실제 Gradle Task 결과로 최종 판정합니다.

## 3. 기동 (실제 승인 테스트 DB 및 외부 연동 격리 확인 후)

먼저 개발자는 테스트 DB와 모든 외부 API·이메일·스케줄러·파일 경로를 확인해야 합니다. `run_web_local.sh`는 기본적으로 실행을 거부하고 명시적 사전조건을 요구합니다. 첫 기동에서 시험 DB는 localhost만 허용합니다.

```bash
# 값은 회사가 승인한 비공개 환경에서만 주입합니다. 아래는 변수 이름만 보여 줍니다.
# SPRING_DATASOURCE_URL / SPRING_DATASOURCE_USERNAME / SPRING_DATASOURCE_PASSWORD
# DFNE_ISOLATED_SANDBOX_ACK=I_CONFIRM_NONPROD
# DFNE_EXTERNAL_ENDPOINTS_ISOLATED=YES
bash 01_EXECUTION_KIT/tools/run_web_local.sh "$(pwd)/workspace/saas-ustra-hr-master"
```

> 메인 애플리케이션은 `spring.config.location`을 직접 설정하는 커스텀 Bootstrap 경로가 있습니다. 따라서 시스템 환경변수로 테스트 DB를 덮어쓴다고 가정하지 말고 실제 사용 대상 DB를 개발자/DBA가 별도로 검증한 후 실행해야 합니다.

## 4. 기동 후 필수 확인

1. 접속: `http://localhost:8081/login` (프로젝트 README 명세; 실제 성공은 별도 기록).
2. 로그인 전/후 정책: 200/302/401/403 및 조직·법인·본인 범위를 서버에서 검증.
3. 첫 조회: 조직/사원 조회 화면 → Controller/API → Service → Mapper → 테스트 DB.
4. 테스트 DB의 읽기·수정 범위, 감사 기록, 예약 작업, 외부 호출 대상 확인.
5. 실제 Test Report를 생성한 뒤에만 `TESTED` 또는 `RUNTIME PASS` 표기.

## 5. 공유할 로그

`evidence/*.raw.log`에는 비밀정보가 있을 수 있으므로 사내 로컬 보관합니다. 개발조직 외부에 공유할 때는 아래를 실행하고 추가 수작업 검토합니다.

```bash
python3 01_EXECUTION_KIT/tools/redact_logs.py evidence/framework_five.raw.log evidence/framework_five.REDACTED.log
```

`evidence/*.REDACTED.log`, `*.exitcode`, 실제 빌드/기동/권한/DB 체크표를 다음 회차에 전달합니다.
