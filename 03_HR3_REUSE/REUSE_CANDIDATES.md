# HR 3.0 / 기존 39개 소스 재사용 후보 — INC-00 기준

이번 Increment의 **소스 Target 병합 건수는 0**입니다. 다음 후보들은 실제 SaaS/HR3 소스 1:1 비교 후 코드 단위로 REUSE / ADAPT / HOLD 판정합니다.

| Capability | HR3 근거 또는 레거시 후보 | INC-00 조치 | 다음 Gate |
|---|---|---|---|
| Foundation Core | 기존 framework-core/data/security/security-jwt/web | 기본 빌드 기준으로 동결 | Gradle5 compile / 권한·로그·Transaction |
| 조직·사원 공통조회 | HR3 common-read/company-member/department-member/organization-read | 기존 Target 구현과 DTO/Mapper/권한 비교 예정 | 객체소유권·법인 범위·테스트 DB |
| 역할·법인 범위 | HR3 Ownership/Authority 자산 | 승인된 보안 설계만 참조 | 실제 Principal과 서버 범위 검증 |
| 근태·휴가 | 기존 SaaS/KBFPS/LBM/Shsec + HR3 예측 조회 Candidate | Core 기동 후 Source-first 도입 | 법인별 Rule/Golden 및 마감·취소 검증 |
| 연동 | Bonif IntegrationJobRun/Attempt/Recovery 후보 | 후보만 보관, 즉시 배포하지 않음 | 승인 Connector 계약/중복/재처리 |
| 그리드 UI | bcv에서 추출한 U.STRA RealGrid JS 래퍼 | 첫 로그인은 기존 UI, 대량 화면에서 단계적 적용 | 외부 RealGrid 엔진/사용권/브라우저/서버 권한 |
| HRP | 기존 고객 HRP | 이번 구축 및 통합 검증 범위에서 제외 | 고객 개발조직 후속 연계 |

**공통 원칙:** Legacy는 업무규칙 승인권한이 아님. HR3의 R18/W3E 복원 텍스트는 바이트 동일성이 검증되지 않았으므로 소스에 직접 적용 금지. 실제 실행되지 않은 Candidate를 완료라고 표시하지 않음.
