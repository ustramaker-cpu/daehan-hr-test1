# saas-ustra-hr

**U.STRA SaaS HR** — Ustra 프레임워크 기반 인사·급여·근태 통합 관리 시스템(e-HR).

Spring Boot 3.5 / Java 21 멀티모듈 프로젝트이며, 서버 사이드 렌더링(Thymeleaf) 웹 애플리케이션과 REST API 두 개의 실행 산출물을 만든다.

---

## 목차

- [기술 스택](#기술-스택)
- [모듈 구조](#모듈-구조)
- [업무 도메인](#업무-도메인)
- [로컬 실행](#로컬-실행)
- [환경 프로파일](#환경-프로파일)
- [빌드 및 배포](#빌드-및-배포)
- [프론트엔드](#프론트엔드)
- [그리드 엔진](#그리드-엔진)
- [화면 테마](#화면-테마)
- [코드 컨벤션](#코드-컨벤션)
- [알아둘 점](#알아둘-점)

---

## 기술 스택

| 구분 | 사용 기술 |
|---|---|
| 언어 / 런타임 | Java 21 (Gradle toolchain 자동 다운로드) |
| 프레임워크 | Spring Boot 3.5.16, Spring Security, Spring Data JPA |
| 빌드 | Gradle 8.14.5 (wrapper 포함) |
| DB | MariaDB (`log4jdbc` 로 SQL 로깅) |
| 영속성 | MyBatis 3.0.5 + mybatis-paginator |
| 인증 | JWT (`jjwt` 0.13) 기반 폼 로그인 |
| 뷰 | Thymeleaf + thymeleaf-layout-dialect |
| 로깅 | Log4j2 (Logback 은 전역 exclude) |
| 설정 암호화 | Jasypt |
| 보안 필터 | Naver Lucy XSS Servlet Filter |
| 캐시 | Ehcache 3 (jakarta) |
| 문서화 | springdoc-openapi (REST API 모듈) |

> Log4j2 사용을 위해 루트 `build.gradle` 에서 `spring-boot-starter-logging`·`logback-classic`·`commons-logging` 을 전역 exclude 한다. 이 exclude 가 빠지면 Logback 과 충돌한다.

---

## 모듈 구조

### 프레임워크 계층 (`com.gsitm.framework.*`)

공통 기반 모듈. 업무 로직을 포함하지 않는다.

| 모듈 | 역할 |
|---|---|
| `framework-core` | 공통 DTO, 유틸(ModelMapper/CamelCase/Jwt 키), 기동 배너(`StartupMessage`) |
| `framework-data` | MyBatis 설정, 공통 `BaseDomain`, 커스텀 TypeHandler |
| `framework-security` | Spring Security 설정 팩토리, 인증 프로바이더, OAuth2 핸들러 |
| `framework-security-jwt` | JWT 토큰 발급·검증, 로그인 성공/실패/로그아웃 핸들러 |
| `framework-web` | 예외 로깅, 로그 필터, 메시지 유틸, ControllerAdvice |

### 업무 계층 (`hr.system.*`)

| 모듈 | 산출물 | 역할 |
|---|---|---|
| `ustra-hr-system-core` | 라이브러리 | 공통코드·부서·사원·조직도·파일·메일·스케줄러·엑셀 등 공통 업무 기반 |
| `ustra-hr-system-api` | 라이브러리 | 업무 API 본체. 최대 모듈(Java 약 1,700 / MyBatis XML 약 435) |
| `ustra-hr-system-pay` | 라이브러리 | 급여 — 지급표준, 연말정산 기초, 계약, 회계연동, 4대보험 |
| `ustra-hr-system-pay-yeta` | 라이브러리 | 연말정산 전용 |
| `ustra-hr-system-auth` | 라이브러리 | 로그인·인증 설정(`WebSecurityConfig`), JWT 핸들러 커스터마이징 |
| `ustra-hr-system-report` | (비어 있음) | 리포트 예약 모듈 — 현재 소스 없음 |
| **`ustra-hr-system-thymeleaf`** | **`ustra-hr.jar`** | **메인 웹 애플리케이션.** 화면(HTML 약 1,490) + 정적 자원 |
| **`ustra-hr-system-restapi`** | **`ustra-hr-restapi.jar`** | **외부 연동용 REST API.** Swagger UI 제공 |

> `@SpringBootApplication` 은 core·auth 모듈에도 있으나 `bootJar.enabled = false` 라 라이브러리로만 빌드된다. 실제 실행 가능한 산출물은 **thymeleaf·restapi 두 개**다.

### 의존 방향

```
framework-core / data / security / security-jwt / web
        ↑
ustra-hr-system-core
        ↑
   ┌────┴──────┬─────────┬──────────┐
  api        auth       pay      pay-yeta
   └────┬──────┴─────────┴──────────┘
        ↑                        ↑
  ustra-hr-system-thymeleaf   ustra-hr-system-restapi
      (ustra-hr.jar)          (ustra-hr-restapi.jar)
```

---

## 업무 도메인

`ustra-hr-system-thymeleaf/src/main/resources/templates/` 하위 디렉터리 기준 (괄호는 화면 수).

| 도메인 | 설명 |
|---|---|
| `pay` (221) | 급여 — 급여계산, 지급대장, 연말정산, 4대보험, 퇴직정산 |
| `wlf` (165) | 복리후생 |
| `emp` (127) | 인사 — 사원정보, 발령, 인사기록 |
| `wrk` (100) | 근태 — 근무계획, 출퇴근기록, 근무형태, 연차 |
| `empmenu` (89) | 개인 메뉴(사원용 셀프서비스) |
| `system` (60) | 시스템 관리 — 공통코드, 메뉴, 권한 |
| `popup` (60) | 공통 팝업 |
| `app` (59) | 신청 업무 |
| `one` (56) | 원(One) 서비스 |
| `uhr` (46) | 전자결재 연동 화면 |
| `doc` (41) | 문서·결재 |
| `mail` (37) | 메일 발송 |
| `mbl` (33) | 모바일 대응 화면 |
| `edu` (31) | 교육 |
| `org` (29) | 조직·조직도 |
| `statistics` (18) | 통계 |
| `layout`·`components`·`fragments` | 공통 레이아웃 및 재사용 조각 |

---

## 로컬 실행

### 사전 준비

- JDK 21 — 없으면 Gradle toolchain(`foojay-resolver`)이 자동으로 내려받는다
- MariaDB 접속 정보 (로컬 프로파일 기준, `ustra-hr-system-core/src/main/resources/application-api.yml`)

### 기동

```bash
# 메인 웹 애플리케이션 (기본 프로파일 = local)
./gradlew :ustra-hr-system-thymeleaf:bootRun

# REST API
./gradlew :ustra-hr-system-restapi:bootRun
```

| 애플리케이션 | 로컬 포트 | 진입 |
|---|---|---|
| 메인 웹 | `8081` | http://localhost:8081/login |
| REST API | `8083` | Swagger UI (springdoc) |

기동이 완료되면 `StartupMessage` 가 로고와 함께 기동 정보(프로파일·포트·Boot 버전·기동 시간)를 콘솔에 출력한다.

---

## 환경 프로파일

`spring.profiles.active` 는 **모듈 설정 로딩용**과 **환경별** 두 종류가 함께 쓰인다.

```yaml
active: core, web, api, local
#       └── 모듈 설정 로딩 (환경 무관, 항상 필요) ──┘  └ 환경 ┘
```

| 환경 프로파일 | 용도 | 웹 포트 |
|---|---|---|
| `local` | 로컬 개발 (기본값) | 8081 |
| `dev1` | 개발 서버 1 | 8081 |
| `dev2` | 개발 서버 2 | 8082 |
| `prod1` | 운영 1 | 8081 |
| `prod2` | 운영 2 | 8082 |

프로파일별로 DB 접속 정보·파일 저장 경로·포트가 모두 달라진다. 설정은 `ustra-hr-system-core/src/main/resources/application-api.yml` 한 파일에 프로파일 블록으로 나뉘어 있다.

배포 시에는 `--spring.profiles.active` 로 환경을 지정한다.

```bash
java -jar ustra-hr.jar --spring.profiles.active=core,web,api,dev1
```

> 과거 `application.yml` 에 `dev1,dev2,demo1,demo2,prod1,prod2` 가 모두 나열되어 있어 **마지막 `prod2` 가 이겨 기본값이 운영**이 되었고, `prod2` 전용 절대경로(`/home/ehradmin/uhrms/prod2/...`) 때문에 로컬 기동이 실패했다. 현재는 `local` 만 기본으로 둔다. 프로파일을 추가할 때 같은 실수를 반복하지 않도록 주의할 것.

DB 비밀번호 등 민감 값은 **Jasypt 로 암호화**되어 설정 파일에 들어간다. 평문으로 커밋하지 않는다.

---

## 빌드 및 배포

```bash
./gradlew clean                                   # 클린
./gradlew :ustra-hr-system-thymeleaf:bootJar      # ustra-hr.jar
./gradlew :ustra-hr-system-restapi:bootJar        # ustra-hr-restapi.jar
./gradlew :ustra-hr-system-thymeleaf:test         # 테스트
```

CI 는 Jenkins 를 사용한다.

| 파일 | 내용 |
|---|---|
| `jenkinsfile` | `:ustra-hr-system-thymeleaf:bootJar` 빌드 후 `./deploy.sh` 실행 |
| `jenkinsfile_dev` | 개발 서버 배포. `./deploy.sh ustra-hr` |
| `jenkinsfile_prod` | ⚠️ 타 프로젝트(`itsm-system-*`) 잔재로 현재 이 저장소와 맞지 않음 |

> `deploy.sh` 는 저장소에 포함되어 있지 않다(배포 서버에 존재). `jenkinsfile_prod` 는 정리가 필요하다.

---

## 프론트엔드

빌드된 번들이 `src/main/resources/static/assets/bundle/` 에 커밋되어 있어 **일반 개발 시 프론트 빌드는 필요 없다.** 소스 수정이 필요할 때만 사용한다.

```bash
cd ustra-hr-system-thymeleaf
npm run build   # webpack 번들 생성
npm run watch   # 감시 모드
```

> 저장소에 `package-lock.json`(npm)과 `pnpm-lock.yaml`(pnpm)이 **둘 다 커밋되어 있다.** 어느 쪽을 쓸지 정해 한쪽을 정리하는 편이 좋다.

주요 라이브러리 — jQuery 3.7, jQuery UI, RealGrid, TOAST UI(Grid·Chart·Calendar·Editor), FullCalendar, Lodash, Axios.

> 그리드는 **RealGrid 와 TOAST UI Grid 중 하나를 서버에서 택일 로드**한다. 현재 기본값은 TOAST 다. [그리드 엔진](#그리드-엔진) 참조.

### 정적 자원 배치

```
static/assets/
├── bundle/      desktop.css, desktop.js, mobile.* (webpack 산출물, 직접 수정 금지)
├── css/         custom.css  ← 프로젝트 전용 오버라이드 (bundle 뒤에 로드)
├── js/          common.js, guide.js, login-theme.js, ustra-utils/*
├── images/
└── vendors/
```

> `bundle/desktop.css` 는 디자인 시스템 컴파일 산출물이므로 직접 수정하면 재빌드 시 유실된다. **프로젝트 고유 스타일은 항상 `css/custom.css` 에 작성한다.**

### 레이아웃 구조

```
configlayout / mainconfiglayout   <head> 공통 (메타·CSS·JS·테마 FOUC 방지 스크립트)
defaultlayout                     상위 셸 (헤더 + 사이드 + MDI 탭 컨테이너)
  └ headerlayout / mainheaderlayout   GNB
  └ sidelayout                        좌측 메뉴 + MDI 탭 제어 + iframe 로딩
framelayout                       각 업무 화면이 iframe 안에서 사용하는 레이아웃
```

업무 화면은 `layout:decorate="~{layout/framelayout}"` 으로 선언하며, `sidelayout` 이 만든 **iframe 안에서 렌더링**된다. 화면은 자체 `<html>` 문서를 가진다.

---

## 그리드 엔진

업무 화면의 그리드는 **RealGrid(상용)** 에서 **TOAST UI Grid(MIT)** 로 전환 중이다.
화면 1,045개(`new UstraRealGrid` 1,267곳 / `new UstraRealGridColumn` 1,337곳)를 고치지 않기 위해,
화면 코드는 그대로 두고 **호환 어댑터**가 RealGrid 이름·API 를 받아 tui-grid 로 넘긴다.
즉 **업무 화면 HTML / Java / Mapper 는 변경되지 않았다.**

### 엔진 선택

```yaml
# ustra-hr-system-thymeleaf/src/main/resources/application.yml
ustra:
  grid:
    engine: toast      # toast | realgrid
```

우선순위는 **URL 파라미터 `?gridEngine=xxx` > `ustra.grid.engine` > 기본값 `realgrid`** 이며,
판정식은 `configlayout` · `mainconfiglayout` · `footerlayout` 세 곳에 동일하게 들어 있다.

- 전체 되돌리기 — 위 한 줄을 `realgrid` 로 바꾸고 재기동한다. **코드 변경은 필요 없다.**
- 화면 단위 대조 — URL 에 `?gridEngine=realgrid` 를 붙이면 그 화면만 예전 엔진으로 뜬다.

> ⚠️ **두 엔진을 동시에 로드할 수 없다.** `ustra.realgrid.js` 가 최상위에서 `let UstraRealGrid` 로 선언하는데,
> `let` 은 전역 렉시컬 바인딩이라 같은 이름을 다시 선언하면 `SyntaxError` 이고 `window.UstraRealGrid = ...` 로 덮어써도 무시된다.
> 그래서 클라이언트 분기가 아니라 **서버사이드(Thymeleaf) 택일 로드**로 처리한다.

### 파일 구성

| 경로 | 역할 |
|---|---|
| `js/ustra-utils/ustra.grid.compat.js` | **RealGrid 호환 어댑터.** `UstraRealGrid`/`UstraRealGridColumn` 이름으로 노출하고, 화면이 직접 호출하는 raw API(`.getGrid()` 1,957곳 / `.getProvider()` 119곳)를 shim 으로 흉내낸다 |
| `js/ustra-utils/ustra.toast.grid.js`<br>`js/ustra-utils/ustra.toast.grid.components.js` | tui-grid 기반 그리드 본체 · 셀 컴포넌트(콤보/체크/이미지 등) |
| `vendors/tui-grid/` | tui-grid 4.21.22 + date/time-picker, pagination (MIT). 상세·패치 내역은 같은 폴더 `VERSIONS.md` |
| `vendors/sheetjs/` | 엑셀 내보내기용 `xlsx.full.min.js` (0.20.3, Apache-2.0). 930KB 라 레이아웃에서 미리 받지 않고 엑셀 기능 사용 시 동적 로드 |
| `css/custom.css` | tui-grid 대응 규칙(콤보 화살표·드롭업 테두리·정렬 아이콘 등) |

### 로드 순서 (고정)

tui-grid 는 `tui.DatePicker` / `tui.Pagination` / `XLSX` 를 **UMD external** 로 참조하므로 순서를 바꾸면 깨진다.

```
tui-time-picker → tui-date-picker → tui-pagination → tui-grid
  → tui-grid.bootstrap.js            (window.Grid = tui.Grid 별칭, 이 프로젝트에서 작성)
  → ustra.toast.grid.components.js
  → ustra.toast.grid.js
  → ustra.grid.compat.js             (호환 어댑터)
```

디버깅 시 `footerlayout.html` 의 경로에서 `.min` 만 빼면 비압축본을 쓴다(같이 반입되어 있다).

### 어댑터에서 이미 해결한 RealGrid 동작 차이

| 항목 | 내용 |
|---|---|
| 헤더 정렬 | 헤더 셀 아무 곳이나 클릭 → 오름/내림/해제 3단계(RealGrid `SortStyle.EXCLUSIVE` 와 동일). 정렬 안 걸린 컬럼은 아이콘을 감추되 자리는 남긴다 |
| 헤더 관찰 | 고정(lside)·스크롤(rside) 헤더를 **모두** 관찰한다. lside 만 보면 가로 스크롤 후 라벨이 중복된다 |
| 컬럼 폭 | `setColumns` 에 내부 `ColumnInfo` 가 아니라 어댑터 정의 목록을 넘긴다. `ColumnInfo` 에는 `width` 가 없어 tui 가 균등 분배로 눌러 가로 스크롤이 사라진다 |
| 콤보 | `dropDownCount`(기본 8)만큼만 보이게 자르고, 아래 공간이 없으면 위로 편다(`.ustra-drop-up`). 목록에 없는 값은 deselect — 열었다 닫기만 해도 행 상태가 `U` 로 바뀌던 문제. 값 비교는 문자열로 한다(숫자는 `select(5)` 가 인덱스로 해석됨) |
| 문자열 렌더러 | `'check'` · `'image'` 를 어댑터가 흡수. tui-grid 는 생성자를 기대하는데 RealGrid 는 문자열이라 행 추가 시 `renderer.type is not a constructor` 로 죽었다 |
| 두 줄 헤더 | DOM 주입 대신 CSS `white-space:pre`. preact 재렌더와 주입이 스크롤 프레임마다 번갈아 흔들리던 원인 |

### 벤더 패치 (업그레이드 시 재적용 필수)

`tui-grid` 4.21.22 에 패치 1건이 들어가 있다 — 옵저버 `flush()` / `callObserver()` 에 `try/finally`.
원본은 관찰자가 예외를 던지면 `clearQueue()` 에 도달하지 못해 `pending` 이 `true` 로 고착되고,
**그 뒤에 생성되는 모든 그리드**가 `Cannot read properties of undefined (reading 'map')` 으로 죽는다.
증상이 원인과 멀리 떨어져 보이므로 주의. 예외 자체는 삼키지 않고 그대로 전파한다.
자세한 내용과 재현 절차는 `vendors/tui-grid/VERSIONS.md` 에 있다.

### 미구현 API 확인

매핑이 불확실한 지점은 조용히 틀리는 대신 **예외를 던진다**(`_notImplemented`).
파일럿 중 여기에 걸린 화면이 곧 추가 작업이 필요한 화면 목록이다.

```js
__gridCompatReport__()     // 콘솔에서 미구현 호출 집계 확인
window.__gridCompatMisses__
```

### 남은 일

- **화면 기동 검증이 아직 끝나지 않았다.** 어댑터 수정 대부분이 실제 화면을 열어 고친 것이라 재대조가 필요하다.
  주요 확인 화면: `wrkflextype100`(근무시간 상세 추가) · `emppsmstinfo140` / `onepiemplinfo140-popup`(체크 컬럼) ·
  `emppsmstinfolist300`(사진) · `paywork195`(두 줄 헤더) · `system/codes` · `pgmbas` · `standard`
- `ustra.toast.grid.js:31` 의 `Grid.applyTheme('stripped', ...)` 는 오타다. 유효값은 `'default'`/`'striped'`/`'clean'` 이라 현재는 default 로 폴백된다.
- 전환이 완료되면 RealGrid 관련 자원(`vendors/realgrid/`, `ustra.realgrid*.js`, 라이선스 키)을 정리할 수 있다.


---

## 화면 테마

헤더 우측 팔레트 버튼으로 테마를 전환한다. 단색 5종(Default·Blue·Green·Mocha·Violet) + 그라데이션 3종(Aurora·Sunset·Ocean).

- 선택값은 `localStorage` 의 **`THEME`** 키에 저장된다 (Default 선택 시 키 삭제)
- FOUC 방지를 위해 `configlayout`/`mainconfiglayout` 의 `<head>` 에서 렌더링 전에 `<html>` 에 `theme-{name}` 클래스를 부여한다
- iframe 은 자체 문서를 가지므로 `framelayout` 이 부모의 테마 클래스를 복제한다(실패 시 `localStorage` 폴백)
- 로그인 계열 화면(`login`·`passwordsearch`·`passwordsearchconfirm`·`passwordchange`)은 `login-theme.js` 가 **카드 요소에만** 테마 클래스를 부여한다

### 테마 관련 작업 시 주의

**1. 유효값 검사는 필수다.** 테마 클래스를 부여하는 코드는 반드시 화이트리스트를 검사해야 한다. 검사가 없으면 미설정 시 `theme-null` 이라는 존재하지 않는 클래스가 붙는다.

```js
var validThemes = ['blue','green','mocha','violet','aurora','sunset','ocean'];
if (t && validThemes.indexOf(t) !== -1) { ... }
```

**2. 클래스를 붙이는 위치가 동작을 바꾼다.** 로그인 화면에서 테마 클래스를 `<body>`/`<html>` 로 옮기면 `desktop.css` 의 로그인 "레이아웃 재설계" 규칙(카드 800→500rem, 로고 축소, `.caution` 보안문구 `display:none`)이 함께 되살아난다. 카드 요소에 붙이는 현재 배치는 **의도된 것**이다.

**3. `desktop.css` 의 테마 블록에는 `:focus` 변형이 없다.** 아래 두 규칙은 특이도가 **동일(0,3,0)** 해 파일 순서로 승부가 갈리고, 뒤에 있는 기본 규칙이 이긴다.

```
.theme-X .button.is-primary    클래스 3개              = 0,3,0
.button.is-primary:focus       클래스 2 + 의사클래스 1 = 0,3,0   ← 뒤에 위치, 승리
```

포커스된 primary 버튼이 디폴트 파란색으로 보이는 원인이며, `custom.css` 에서 테마별 `:focus`/`.is-focus`/`.is-active` 규칙(특이도 0,4,0)으로 보정하고 있다. **테마에 새 버튼 변형을 추가할 때 상태(focus/active/hover) 변형 누락 여부를 함께 확인할 것.**

---

## 코드 컨벤션

### 변경 이력 주석

기존 코드를 수정·삭제할 때는 사유와 내용을 주석으로 남긴다.

```html
<!-- [수정일: YYYY-MM-DD] 수정이유: ... / 수정내용: ... -->
<!-- [추가일: YYYY-MM-DD] 추가이유: ... / 추가내용: ... -->
<!-- [삭제일: YYYY-MM-DD] 삭제이유: ... / 삭제내용: ... -->
```

삭제 시 코드를 지우지 않고 주석 처리한 뒤 사유를 남기는 방식이 널리 쓰인다.

### 커밋 메시지

```
<type>: <제목>

<본문 — 증상 / 원인 / 수정 내용>
```

`feat` · `fix` · `chore` · `docs` 를 사용한다. 본문에는 **왜** 그렇게 고쳤는지(근거·특이도·타이밍 등)를 남긴다.

---

## 알아둘 점

작업 전에 알고 있으면 시간을 아낄 수 있는 항목들.

**Spring Boot 3 이관 관련**

- `spring.security.filter.dispatcher-types` 를 `async, error, request` 로 고정해 두었다. Boot 3 기본값은 `DispatcherType` 전체라 FORWARD 까지 시큐리티 필터를 태우는데, 로그인 실패 시 `/login` 으로 forward → 재인증 → 실패 → forward 가 무한 반복되어 `StackOverflowError` 로 죽는다. **이 값을 지우지 말 것.**
- MyBatis 는 `mybatis-spring-boot-starter` **3.0.x 필수** (2.x 는 javax 기반이라 Boot 3 에서 동작하지 않는다).
- REST API 의 Swagger 는 springfox 에서 **springdoc** 으로 교체되었다.

**모듈 / 빌드**

- `ustra-hr-system-report` 는 `settings.gradle` 에 등록되어 있으나 소스가 없다.
- 로그 관련 의존성을 추가할 때 Logback 이 딸려오지 않는지 확인한다(루트 exclude 와 충돌).

**프론트**

- 화면이 iframe 안에서 동작하므로, 부모 문서를 다뤄야 할 때는 `parent.document` 를 사용한다. 로딩 스피너(`#spin-progress`)와 모달이 대표적이다.
- `Ustra.modal`(`ustra.common.js`)은 `self != top` 이면 **부모 창으로 위임**한다. 알럿/컨펌은 항상 최상위 문서에 렌더링된다.

---

## 라이선스

사내 프로젝트이며 외부 배포용이 아니다.

RealGrid·SynapEditor·rMate Chart·UbiForm 등 **상용 라이브러리**를 사용하며, RealGrid 라이선스 키 파일이 저장소에 포함되어 있다.

그리드는 현재 MIT 라이선스인 TOAST UI Grid 로 전환 중이며(자세한 내용은 [그리드 엔진](#그리드-엔진)), `ustra.grid.engine: toast` 일 때는 RealGrid 라이선스 키를 아예 로드하지 않는다. 다만 되돌릴 수 있어야 하므로 RealGrid 자원은 그대로 남겨 둔다.

```
static/assets/vendors/realgrid/realgrid-lic.js        # 개발
static/assets/vendors/realgrid/realgrid-lic-prod.js   # 운영
```

저장소를 외부에 공개하지 않는다.
