# feature/toast_3차 Toast 관련 뷰페이지 AS-IS / TO-BE

작성 기준: `feature/toast_2차` → `feature/toast_3차` 고유 변경분
기준일: 2026-09-18

## 1. 범위와 판정 기준

이 문서는 3차 브랜치에만 추가된 변경 중 Toast Grid 동작·호환과 직접 연결된 뷰페이지 예외처리를 정리한다. 공통 어댑터에서 처리하는 API 변환은 제외하고, 메뉴별 조회·포커스·합계·레이아웃처럼 뷰에 종속된 코드만 포함했다. 프로젝트명(`sch^...` → `saas^...`), 권한·문구·일반 업무 규칙 변경은 제외했다.

- 비교 기준: `git diff feature/toast_2차 feature/toast_3차`
- 3차 브랜치 고유 커밋: 24개
- Toast 관련 뷰 파일: **7개**
- `AS-IS`: diff의 삭제(`-`) 라인
- `TO-BE`: diff의 추가(`+`) 라인

## 2. 3차 브랜치의 Toast 관련 커밋

| 커밋 | 뷰 영향 | Toast 예외 내용 |
|---|---|---|
| `c79d2bf` | `edu/edumng/edumngcourse.html` | 추가 시 현재 행을 해제해 재선택 이벤트가 발생하도록 보정 |
| `bf863b8` | `pay/paywht/pr640.html`, `pay/paywht/pr641.html`, `system/standard.html` | Toast 편집 콜백의 중복 합계 방지, 정렬 후 표시 첫 행 포커스 |
| `daa7ee2` | `org/ororgempl/ororgempl270.html` | Toast 본문 높이·세로 스크롤을 RealGrid와 정렬 |
| `30577da` | `org/ororghier/ororghier200.html`, `org/ororglst/ororglst280.html` | 조직도 스크롤, 초기 포커스·합성 클릭·빈 결과 예외 |

`87232ca`는 공통 어댑터/CSS만 변경되어 뷰 예외는 없다.

## 3. 공통 어댑터로 처리하기 어려운 뷰 예외

공통 어댑터는 `getToastGrid()` 존재 여부로 엔진을 판별하고 API·이벤트 차이를 흡수한다. 다음은 메뉴별 DOM, 상세 조회, 합계 계산, iframe 높이와 결합되어 공통화하기 어려워 뷰에서 조건 분기한 부분이다.

- 교육과정: 추가 버튼에서 `clearDtl()` 후 `clearCurrent()`를 호출해 같은 행 재클릭 이벤트를 복원한다.
- 조직도/조직목록: Toast의 상시 스크롤 예약 폭을 `overflow-y:auto`로 바꾸고, Toast가 처리한 포커스에 합성 클릭을 중복 실행하지 않는다. 빈 결과면 상세·하위 그리드를 비운다.
- 조직원 조회: Toast에서만 `bodyHeight: 592`를 지정하고 본문 스크롤을 자동으로 해 우측 그리드와 높이·폭을 맞춘다.
- 지급명세: Toast의 `beforeChange` 시점에는 셀 값이 이미 반영되지 않으므로 `newValue`를 계산에만 선반영하고 `setValue` 재호출을 막아 합계 중복을 방지한다.
- 업무기준관리: 정렬된 표시 순서의 첫 행 데이터 행을 `getDataRow(0)`로 구해 포커스한다.

## 4. 실제 코드 비교 (7개)

아래 코드는 각 파일의 2차 대비 실제 Toast 관련 hunk이다. 각 블록에서 삭제 라인이 AS-IS, 추가 라인이 TO-BE다.

### 4.1 `ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngcourse.html`

```diff
diff --git a/ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngcourse.html b/ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngcourse.html
index 2652f9f..c8607f4 100644
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngcourse.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngcourse.html

@@ -502,8 +502,17 @@
         $('#btnSearch').on('click', search);
 
         // 추가 버튼
+        // [수정일: 2026-09-15] 수정이유: 제보 "리스트에 값이 있을 경우 추가 버튼 클릭 이후
+        //   (직전에 포커싱되어 있던) row 를 클릭하면 조회되지 않음".
+        //   추가가 상세 폼만 비우고 그리드의 현재 행은 그대로 두었다. 그래서 그 행을 다시
+        //   클릭해도 현재 행이 바뀌지 않아 onCurrentRowChanged 가 오지 않았다.
+        //   (RealGrid 2.9.5 / TOAST Grid 4.21.22 양쪽에서 같은 결과를 실측했다 — 엔진 차이가 아니다.
+        //    같은 구조의 복리후생신청 welfaremng110 등은 추가에서 clearCurrent() 를 부른다.)
+        // 수정내용: 상세를 비울 때 그리드의 현재 행도 함께 해제한다. 해제로 발생하는
+        //   onCurrentRowChanged(newRow = -1) 는 위 핸들러의 clearDtl() 분기라 결과가 같다.
         $('#btnAdd').on('click', function (e) {
             clearDtl();
+            edumngcourseGrid.getGrid().clearCurrent();
         });
 
         // 삭제 버튼
```

### 4.2 `ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgempl/ororgempl270.html`

```diff
diff --git a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgempl/ororgempl270.html b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgempl/ororgempl270.html
index 3ffd917..c9fe952 100644
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgempl/ororgempl270.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgempl/ororgempl270.html

@@ -143,6 +143,13 @@
             border: none !important;
             padding: 0 !important;
         }
+
+        /* tui-grid 기본 overflow:scroll 은 행이 적어도 세로 스크롤바 17px을 예약한다.
+           실제 RealGrid처럼 필요할 때만 세로 스크롤바가 생기게 해 렌더 폭을 맞춘다. */
+        #orgGrid .tui-grid-body-area,
+        #emplGrid .tui-grid-body-area {
+            overflow-y: auto;
+        }
     </style>
     <script type="application/javascript">
         let orgGrid;

@@ -202,7 +209,7 @@
                 .add('orgCodeUp', '상위조직코드', 'i18n', 100, true, {visible: false})
             ;
 
-            orgGrid = new UstraRealGrid('orgGrid', column, {
+            var orgGridOptions = {
                 check: false,
                 editable: false,
 

@@ -214,7 +221,16 @@
                 header: {
                     visible: false
                 },
-            }).build();
+            };
+
+            // Toast 의 fitToParent 높이는 iframe 자동 높이와 서로 영향을 주어 조직도만
+            // 196px 늘어난다. 오른쪽 600px 그리드와 같은 카드 하단에 맞는 실제 RealGrid
+            // 본문 높이(592px)를 Toast 생성 옵션에만 고정한다.
+            if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                orgGridOptions.bodyHeight = 592;
+            }
+
+            orgGrid = new UstraRealGrid('orgGrid', column, orgGridOptions).build();
 
             orgGrid._grid.displayOptions.fitStyle = "even";
```

### 4.3 `ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororghier/ororghier200.html`

```diff
diff --git a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororghier/ororghier200.html b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororghier/ororghier200.html
index 01e0b42..65cb595 100644
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororghier/ororghier200.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororghier/ororghier200.html

@@ -103,6 +103,13 @@
         </div>
     </div>
      <style>
+         /* Toast에만 적용: 행이 적으면 RealGrid와 동일한 컬럼 가용 폭을 사용한다. */
+         #orgMapGrid .tui-grid-body-area,
+         #targetGrid .tui-grid-body-area,
+         #unrelatedOrgGrid .tui-grid-body-area {
+             overflow-y: auto;
+         }
+
          #unrelatedOrgGrid .rg-body .rg-table,
          #targetGrid .rg-body .rg-table {
              cursor: pointer !important;
```

### 4.4 `ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororglst/ororglst280.html`

```diff
diff --git a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororglst/ororglst280.html b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororglst/ororglst280.html
index 7beb54f..fbd30be 100644
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororglst/ororglst280.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororglst/ororglst280.html

@@ -266,6 +266,12 @@
         </div>
     </div>
 
+    <style>
+        /* Toast의 상시 세로 스크롤바 예약 폭을 RealGrid처럼 필요할 때만 사용한다. */
+        #orgChartGrid .tui-grid-body-area {
+            overflow-y: auto;
+        }
+    </style>
     <script type="application/javascript">
             var orgChartGrid;
 

@@ -335,14 +341,30 @@
                     if (selCell == null) {
                         selCell = {rowKey: 0, columnName: "orgchartNm"};
                     }
+                    // 최초 호출은 셀 대신 검색 조건을 전달한다. RealGrid는 이 경우 첫 행을
+                    // 선택하지만 Toast는 undefined rowKey를 거절해 상세/이력이 비어 있었다.
+                    if (typeof orgChartGrid.getToastGrid === 'function' && selCell.rowKey == null) {
+                        selCell = {rowKey: 0, columnName: "orgNameHan"};
+                    }
                     setTimeout(function () {
                         orgChartGrid.expandAll();
 
 
                         orgChartGrid.focus(selCell.rowKey, selCell.columnName);
-                        $(orgChartGrid._grid.el).find('td[data-row-key=' + selCell.rowKey + ']:first').trigger('click');
+                        // Toast focus가 현재 행 변경을 이미 알린다. 합성 click은 공통
+                        // 레이아웃 이벤트까지 전파되어 초기 상세 조회를 중단시킨다.
+                        if (typeof orgChartGrid.getToastGrid !== 'function') {
+                            $(orgChartGrid._grid.el).find('td[data-row-key=' + selCell.rowKey + ']:first').trigger('click');
+                        }
 
                         var data = orgChartGrid.getFocusData();
+                        if (typeof orgChartGrid.getToastGrid === 'function' && !data) {
+                            $('#ororglst280').find('input').val('');
+                            ororglst290Grid.clear();
+                            ororglst300Grid.clear();
+                            ororglst310Grid.clear();
+                            return;
+                        }
                         $('#orgCode').val(data.orgCode);
                         $('#orgNameHan').val(data.orgNameHan);
                         //조직상세 조회
```

### 4.5 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr640.html`

```diff
diff --git a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr640.html b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr640.html
index 29983c4..9f547f8 100644
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr640.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr640.html

@@ -937,12 +938,20 @@
             }
 
             function setSumValues(itemIndex, column, grid, oldValue, newValue) {
-
-                grid.setValue(itemIndex, column, newValue);
+                // RealGrid의 onEditCommit은 편집값이 provider에 반영되기 전 호출되므로
+                // 계산에 사용할 현재 셀 값을 먼저 setValue 해 왔다. Toast도 같은 시점
+                // (beforeChange)에 콜백을 호출하지만 여기서 같은 셀을 갱신하면, 바깥 편집
+                // 커밋이 한 번 더 반영되면서 summary가 지급총액을 두 번 집계한다.
+                // Toast에서는 계산 변수에 newValue만 선반영하고 실제 셀 갱신은 바깥
+                // 커밋 한 번에 맡긴다. RealGrid 경로는 기존 동작을 그대로 유지한다.
+                const isToastGrid = typeof incomeGrid.getToastGrid === "function";
+                if (!isToastGrid) {
+                    grid.setValue(itemIndex, column, newValue);
+                }
                 const imputedYymm = grid.getValue(itemIndex, 'imputedYymm').replace('-', '') + '01';
-                let payAmt = grid.getValue(itemIndex, "payAmt");
-                let taxRate = grid.getValue(itemIndex, "taxRate");
-                let eiAmt = grid.getValue(itemIndex, "eiAmt");
+                let payAmt = isToastGrid && column === "payAmt" ? newValue : grid.getValue(itemIndex, "payAmt");
+                let taxRate = isToastGrid && column === "taxRate" ? newValue : grid.getValue(itemIndex, "taxRate");
+                let eiAmt = isToastGrid && column === "eiAmt" ? newValue : grid.getValue(itemIndex, "eiAmt");
                 eiAmt = Math.floor(eiAmt / 10) * 10
 
                 let icmTaxVal = Math.floor(((payAmt * taxRate) / 100) / 10) * 10;
```

### 4.6 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641.html`

```diff
diff --git a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641.html b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641.html
index 0eff629..fc66e81 100644
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641.html

@@ -1209,15 +1209,23 @@
             }
 
             function setSumValues(itemIndex, column, grid, oldValue, newValue) {
+                // RealGrid의 onEditCommit은 편집값이 provider에 반영되기 전 호출되므로
+                // 계산에 사용할 현재 셀 값을 먼저 setValue 해 왔다. Toast도 같은 시점
+                // (beforeChange)에 콜백을 호출하지만 여기서 같은 셀을 갱신하면, 바깥 편집
+                // 커밋이 한 번 더 반영되면서 summary가 지급총액을 두 번 집계한다.
+                // Toast에서는 계산 변수에 newValue만 선반영하고 실제 셀 갱신은 바깥
+                // 커밋 한 번에 맡긴다. RealGrid 경로는 기존 동작을 그대로 유지한다.
+                const isToastGrid = typeof incomeGrid.getToastGrid === "function";
+                if (!isToastGrid) {
+                    grid.setValue(itemIndex, column, newValue);
+                }
 
-                grid.setValue(itemIndex, column, newValue);
-
-                let payAmt = grid.getValue(itemIndex, "payAmt");
-                let taxRate = grid.getValue(itemIndex, "taxRate");
-                let eiAmt = grid.getValue(itemIndex, "eiAmt");
+                let payAmt = isToastGrid && column === "payAmt" ? newValue : grid.getValue(itemIndex, "payAmt");
+                let taxRate = isToastGrid && column === "taxRate" ? newValue : grid.getValue(itemIndex, "taxRate");
+                let eiAmt = isToastGrid && column === "eiAmt" ? newValue : grid.getValue(itemIndex, "eiAmt");
                 eiAmt = Math.floor(eiAmt / 10) * 10;
 
-                let expRate = grid.getValue(itemIndex, "expRate");
+                let expRate = isToastGrid && column === "expRate" ? newValue : grid.getValue(itemIndex, "expRate");
                 let corpInd = grid.getValue(itemIndex, "corpInd");
                 let individual = '1'; //개인코드
                 let corporateBody = '2'; //법인코드
```

### 4.7 `ustra-hr-system-thymeleaf/src/main/resources/templates/system/standard.html`

```diff
diff --git a/ustra-hr-system-thymeleaf/src/main/resources/templates/system/standard.html b/ustra-hr-system-thymeleaf/src/main/resources/templates/system/standard.html
index 3b9d381..c0f74f3 100644
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/system/standard.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/system/standard.html

@@ -262,8 +262,18 @@
         var params    = $('#searchTable').getData();
         standardGrid.load(apiUrl, params, function() {
             setTimeout(function(){
-                standardGrid.focus(0);
-                /* 이안에 코딩하지 마세요  상위 onCurrentRowChanged Event 참조 */
+                /* [수정일: 2026-09-15] 수정이유: 정렬한 뒤 조회하면 포커스가 맨 위 행이 아니라
+                   엉뚱한 행에 갔다. `focus(0)` 은 RealGrid 에서 `setCurrent({dataRow:0})` —
+                   표시 순서 첫 행이 아니라 **조회 순서 첫 행**이다. 정렬은 조회 뒤에도 유지되므로
+                   그 행은 화면 중간이나 맨 아래에 그려진다(실측: 기준분류명 오름차순 정렬 후
+                   조회 — RealGrid·Toast 둘 다 표시 14번째 행에 포커스).
+                   수정내용: 표시 순서 첫 행의 dataRow 를 구해 그 행을 포커스한다.
+                   조회 결과가 없으면 지금까지처럼 focus(0) 을 그대로 불러
+                   onCurrentRowChanged(-1) 경로를 유지한다.
+                   ※ 이안에 코딩하지 마세요  상위 onCurrentRowChanged Event 참조 */
+                var grid = standardGrid.getGrid();
+                var firstDataRow = (grid.getItemCount() > 0) ? grid.getDataRow(0) : 0;
+                standardGrid.focus(firstDataRow);
             }, 100);
         });
     }
```

## 5. 제외한 3차 변경

다음 변경은 3차 브랜치 diff에 존재하지만 Toast Grid 뷰 예외가 아니므로 문서에서 제외했다.

- 인쇄 리포트의 프로젝트명 변경(`sch^emp`/`sch^pay` → `saas^...`)
- `layout/sidelayout.html`, `system/advice100.html`, `system/mail/co-mail.html`의 로그·파일 삭제·에디터 스타일 변경
- `pay/paypstd/paypstd220.html` 권한값 변경, `pay/paypbas/paypbas270.html` 필수값 변경
- `wlf/*`, `wrk/*`의 일반 업무 로직 수정

## 6. 참고

- 공통 어댑터: `ustra-hr-system-thymeleaf/src/main/resources/static/assets/js/ustra-utils/ustra.grid.compat.js`, `ustra-hr-system-thymeleaf/src/main/resources/static/assets/js/ustra-utils/ustra.toast.grid.js`
- 비교 브랜치: `feature/toast_2차`, `feature/toast_3차`

