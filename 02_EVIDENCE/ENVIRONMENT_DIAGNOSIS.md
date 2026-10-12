# 실제 실행 증거 요약 (2026-10-12)

1. `java -version`: OpenJDK 21.0.12.1 — PASS, **프로젝트 빌드와 별도**.
2. `./gradlew --version`: Exit Code **1**, `java.net.UnknownHostException: services.gradle.org`. Gradle 8.14.5 배포본을 내려받지 못함.
3. `run_compile.sh`: Exit Code **20**, 사전 단계에서 환경 차단 보고, 5모듈 compileJava와 web bootWar 미진입.
4. `run_web_local.sh`: 승인값 없이 실행시 Exit Code **30**로 차단, 앱 및 DB 접근 없음.
5. `preflight.py`: 원본 ZIP 해시/파일 존재/모듈목록 PASS. 9,048개 ZIP 항목, 7,375개 일반 파일.
6. Python 도구 테스트: 4/4 PASS. 이는 운영/Java 단위 테스트와 무관.

**요약 판정:** `SOURCE PASS | TOOL STATIC PASS | GRADLE ENV BLOCKED | COMPILE NOT RUN | SERVER NOT RUN | DB NOT RUN`.
