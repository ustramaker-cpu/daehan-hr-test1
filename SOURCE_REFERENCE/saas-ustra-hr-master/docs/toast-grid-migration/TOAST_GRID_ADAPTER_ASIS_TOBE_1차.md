# feature/toast 1차 Toast 관련 뷰페이지 AS-IS / TO-BE

작성 기준: `feature/toast` 브랜치
비교 기준: 초기 커밋 `3fd63ac` → `feature/toast`
기준일: 2026-09-18

## 1. 범위와 판정 기준

이 문서는 `feature/toast` 브랜치에서 Toast Grid 전환·호환 작업으로 확인되는 뷰페이지와 팝업만 정리한다. 일반 업무 수정, 단순 프로젝트명 치환, Toast와 무관한 레이아웃 변경은 제외했다.

- Toast 명시 커밋: 27개
- Toast 관련 뷰 파일: **34개**
- `AS-IS`: 초기 기준 커밋에 있던 삭제(-) 코드
- `TO-BE`: `feature/toast`에 추가된 (+) 코드
- 실제 diff hunk만 수록하며, 파일 전체를 복제하지 않는다.

## 2. 1차 브랜치의 주요 Toast 커밋

| 커밋 | 주요 내용 |
|---|---|
| `1dbbe3f` | 우클릭 메뉴, ExcelJS 쓰기, 재조회·정렬 유지 등 어댑터 동기화 |
| `f457f6c` | 근태내역관리 삭제 400, 빈 양식 파일, Excel 불러오기 |
| `0bdfdbd` | 교육 Excel 등록 보조 Grid 숨김 |
| `a797b2e` | 원천세 Toast 동작·레이아웃 |
| `10dc5c4` | Toast Grid와 RealGrid 호환 결함 보완 |
| `2429378` | 헤더·폭·편집 커밋 계약 정합화 |
| `82fc39a` | 헤더 스크롤바·룩업·정렬 포커스 정합화 |
| `b181b5f` | 업로드 날짜 형식·저장 재조회·삭제 정합화 |
| `4f44548` | 근태내역관리 Toast 삭제 순서 정합화 |

## 3. 공통 처리와 뷰 예외 구분

공통 API 변환은 `ustra.grid.compat.js`가 담당한다. 뷰페이지에는 Toast에서만 필요한 팝업 DOM 숨김, 화면별 저장·삭제 순서, 계산 콜백 시점, 특정 레이아웃 보정만 둔다. 실제 코드에서 Toast 여부는 주로 `getToastGrid` 존재 여부로 판정한다.

## 4. 실제 코드 비교 (34개 파일)

### 4.1 `ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngcourseupload.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngcourseupload.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngcourseupload.html
@@ -152,4 +152,9 @@
             ;

+            // Toast는 양식 생성용 보조 그리드도 일반 그리드처럼 렌더링하므로 화면에서만 숨긴다.
+            // RealGrid는 기존 DOM/레이아웃 동작을 그대로 유지한다.
+            if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                document.getElementById(_prefix+'ExcelGrid').style.display = 'none';
+            }
             edumngcourseuploadExcel = new UstraRealGrid(_prefix+'ExcelGrid', columns, {}).build();

@@ -448,3 +453,3 @@
 </div>
 <!-- [ edumngcourseupload ] end -->
-</html>
+</html>
```

### 4.2 `ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngedursltmngupload.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngedursltmngupload.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/edu/edumng/edumngedursltmngupload.html
@@ -200,4 +200,9 @@
             ;

+            // Toast는 양식 생성용 보조 그리드도 일반 그리드처럼 렌더링하므로 화면에서만 숨긴다.
+            // RealGrid는 기존 DOM/레이아웃 동작을 그대로 유지한다.
+            if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                document.getElementById(_prefix+'ExcelGrid').style.display = 'none';
+            }
             edumngedursltmnguploadExcel = new UstraRealGrid(_prefix+'ExcelGrid', columns, {}).build();
         }
@@ -479,3 +484,3 @@
 </div>
 <!-- [ edumngedursltmngupload ] end -->
-</html>
+</html>
```

### 4.3 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstupload100.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstupload100.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstupload100.html
@@ -218,4 +218,21 @@
         }

+        // 인사업로드 저장 완료 후 재조회
+        // Toast는 저장 성공 직후 현재 탭의 목록을 직접 다시 읽는다. 업로드 양식용 숨김
+        // 그리드(/codegrid)의 완료 콜백에 목록 조회를 연결하면 저장 직후 실제 목록 API가
+        // 호출되지 않을 수 있다. RealGrid는 기존 alert 확인 후 Setting()을 호출하는 흐름을
+        // 그대로 유지해 Toast 보완이 RealGrid 동작에 영향을 주지 않도록 한다.
+        function completePsmstuploadSave(grid, gridApiUrl, result, realGridReload) {
+            var message = "총 " + result + "건 저장되었습니다.";
+
+            if (grid && typeof grid.getToastGrid === 'function') {
+                grid.load(gridApiUrl, $('#searchTable').getData(), function() {});
+                $.alert(message, "알림");
+                return;
+            }
+
+            $.alert(message, "알림", realGridReload);
+        }
+
         function searchTabs(){
             var params = $('#searchTable').getData();
@@ -257,3 +274,3 @@

 </div>
-</html>
+</html>
```

### 4.4 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload110.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload110.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload110.html
@@ -107,5 +107,5 @@
         $.post(apiUrl110, param, function(result){
             if(result && result > 0){
-                $.alert("총 "+result+"건 저장되었습니다.", "알림", function() {
+                completePsmstuploadSave(emppsmstupload110Grid, apiUrl110, result, function() {
                     console.log('$curCode.val()'+ $curCode.val())
                     emppsmstupload110Setting({'emplNo' : $emplNo.val(), 'curCode' : $curCode.val()})
@@ -400,3 +400,3 @@
     }
 </script>
-</html>
+</html>
```

### 4.5 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload120.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload120.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload120.html
@@ -81,5 +81,5 @@
         $.post(apiUrl120, param, function(result){
             if(result && result > 0){
-                $.alert("총 "+result+"건 저장되었습니다.", "알림", function() {
+                completePsmstuploadSave(emppsmstupload120Grid, apiUrl120, result, function() {
                     emppsmstupload120Setting({'emplNo' : $emplNo.val(), 'curCode' : $curCode.val()})
                 });
@@ -296,3 +296,3 @@

 </script>
-</html>
+</html>
```

### 4.6 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload130.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload130.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload130.html
@@ -81,5 +81,5 @@
         $.post(apiUrl130, param, function(result){
             if(result && result > 0){
-                $.alert("총 "+result+"건 저장되었습니다.", "알림", function() {
+                completePsmstuploadSave(emppsmstupload130Grid, apiUrl130, result, function() {
                     emppsmstupload130Setting({'emplNo' : $emplNo.val(), 'curCode' : $curCode.val()})
                 });
@@ -292,3 +292,3 @@

 </script>
-</html>
+</html>
```

### 4.7 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload140.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload140.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload140.html
@@ -81,5 +81,5 @@
         $.post(apiUrl140, param, function(result){
             if(result && result > 0){
-                $.alert("총 "+result+"건 저장되었습니다.", "알림", function() {
+                completePsmstuploadSave(emppsmstupload140Grid, apiUrl140, result, function() {
                     emppsmstupload140Setting({'emplNo' : $emplNo.val(), 'curCode' : $curCode.val()})
                 });
@@ -293,3 +293,3 @@

 </script>
-</html>
+</html>
```

### 4.8 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload150.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload150.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload150.html
@@ -81,5 +81,5 @@
         $.post(apiUrl150, param, function(result){
             if(result && result > 0){
-                $.alert("총 "+result+"건 저장되었습니다.", "알림", function() {
+                completePsmstuploadSave(emppsmstupload150Grid, apiUrl150, result, function() {
                     emppsmstupload150Setting({'emplNo' : $emplNo.val(), 'curCode' : $curCode.val()})
                 });
@@ -274,3 +274,3 @@

 </script>
-</html>
+</html>
```

### 4.9 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload160.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload160.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload160.html
@@ -81,5 +81,5 @@
         $.post(apiUrl160, param, function(result){
             if(result && result > 0){
-                $.alert("총 "+result+"건 저장되었습니다.", "알림", function() {
+                completePsmstuploadSave(emppsmstupload160Grid, apiUrl160, result, function() {
                     emppsmstupload160Setting({'emplNo' : $emplNo.val(), 'curCode' : $curCode.val()})
                 });
@@ -270,3 +270,3 @@

 </script>
-</html>
+</html>
```

### 4.10 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload170.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload170.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload170.html
@@ -81,5 +81,5 @@
         $.post(apiUrl170, param, function(result){
             if(result && result > 0){
-                $.alert("총 "+result+"건 저장되었습니다.", "알림", function() {
+                completePsmstuploadSave(emppsmstupload170Grid, apiUrl170, result, function() {
                     emppsmstupload170Setting({'emplNo' : $emplNo.val(), 'curCode' : $curCode.val()})
                 });
@@ -239,3 +239,3 @@

 </script>
-</html>
+</html>
```

### 4.11 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload180.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload180.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload180.html
@@ -81,5 +81,5 @@
         $.post(apiUrl180, param, function(result){
             if(result && result > 0){
-                $.alert("총 "+result+"건 저장되었습니다.", "알림", function() {
+                completePsmstuploadSave(emppsmstupload180Grid, apiUrl180, result, function() {
                     emppsmstupload180Setting({'emplNo' : $emplNo.val(), 'curCode' : $curCode.val()})
                 });
@@ -223,3 +223,3 @@

 </script>
-</html>
+</html>
```

### 4.12 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload190.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload190.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/psmstupload/emppsmstuploadtab/emppsmstupload190.html
@@ -81,5 +81,5 @@
         $.post(apiUrl190, param, function(result){
             if(result && result > 0){
-                $.alert("총 "+result+"건 저장되었습니다.", "알림", function() {
+                completePsmstuploadSave(emppsmstupload190Grid, apiUrl190, result, function() {
                     emppsmstupload190Setting({'emplNo' : $emplNo.val(), 'curCode' : $curCode.val()})
                 });
@@ -226,3 +226,3 @@

 </script>
-</html>
+</html>
```

### 4.13 `ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgupload/ororgexcelupload.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgupload/ororgexcelupload.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgupload/ororgexcelupload.html
@@ -191,4 +191,9 @@
                 ;

+                // Toast는 양식 다운로드용 보조 그리드도 일반 그리드처럼 렌더링한다.
+                // Toast 실행에서만 숨기고 RealGrid DOM/동작은 그대로 둔다.
+                if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                    document.getElementById(_prefix+'ExcelGrid').style.display = 'none';
+                }
                 ororgexceldownload = new UstraRealGrid(_prefix+'ExcelGrid', columns, {}).build();

@@ -368,7 +373,18 @@
                 ororgexcelupload.setRowStates(data, "created");

-                for(var i =0; i<ororgexcelupload.getRowCount(); i++){
-                    if(!ororgexcelupload._grid.getValue(i,"orgCode")){
-                        ororgexcelupload.removeRow(i);
+                if (typeof ororgexcelupload.getToastGrid === 'function') {
+                    // Toast rowKey는 삭제 뒤 표시 인덱스와 같다는 보장이 없다. 실제 rowKey를
+                    // 먼저 수집해 삭제해야 연속 빈 행도 건너뛰지 않고 모두 제거된다.
+                    ororgexcelupload.getData()
+                        .filter(function(row) { return !row.orgCode; })
+                        .map(function(row) { return row.rowKey; })
+                        .forEach(function(rowKey) { ororgexcelupload.removeRow(rowKey); });
+                    $dataCheck = false;
+                } else {
+                    // RealGrid 기존 실행 경로는 변경하지 않는다.
+                    for(var i =0; i<ororgexcelupload.getRowCount(); i++){
+                        if(!ororgexcelupload._grid.getValue(i,"orgCode")){
+                            ororgexcelupload.removeRow(i);
+                        }
                     }
                 }
@@ -442,3 +458,3 @@
 </div>
 <!-- [ ororgexcelupload ] end -->
-</html>
+</html>
```

### 4.14 `ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgupload/ororgexcelupload120.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgupload/ororgexcelupload120.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororgupload/ororgexcelupload120.html
@@ -119,4 +119,11 @@
             ;

+            // Toast Grid는 div의 type="hidden"을 숨김 속성으로 처리하지 않으므로
+            // 양식 다운로드 전용 보조 그리드가 화면에 렌더링되지 않도록 한다.
+            // RealGrid 엔진의 기존 렌더링/엑셀 다운로드 동작은 변경하지 않는다.
+            if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                document.getElementById(_prefix+'ExcelGrid').style.display = 'none';
+            }
+
             ororgexceldownload120 = new UstraRealGrid(_prefix+'ExcelGrid', columns, {}).build();
         }
@@ -329,3 +336,3 @@
 </div>
 <!-- [ ororgexcelupload120 ] end -->
-</html>
+</html>
```

### 4.15 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paypbas/paypbas140.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paypbas/paypbas140.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paypbas/paypbas140.html
@@ -101,4 +101,5 @@
         var apiUrl = '/pay/paypbas/paypbas140';
         var paypbas140Grid;
+        var paypbas140ToastFocusFallback;
         var paypbas141Grid;
         var $baseDate = $('input[name=baseDate]');
@@ -400,4 +401,36 @@
         // 상세조회
         function getPaypbas141(param) {
+            // Toast는 현재 행을 삭제하면 포커스를 비우지만 RealGrid는 같은 dataRow에 남은 행을
+            // 현재 행으로 유지한다. 삭제 성공 콜백에서 null을 그대로 상세조회에 넘기면
+            // targetKindCode 접근 예외가 발생하고 공통 AJAX 로딩 종료 처리도 중단된다.
+            // Toast에서만 삭제 전 행 순서에 따른 다음/직전 잔존 행으로 포커스를 복원하고,
+            // 잔존 행이 없으면 상세를 비운다.
+            if (!param && typeof paypbas140Grid.getToastGrid === 'function') {
+                var masterRows = paypbas140Grid.getData();
+                if (masterRows.length > 0) {
+                    var focusRow = masterRows[0];
+                    if (paypbas140ToastFocusFallback) {
+                        for (var fi = 0; fi < paypbas140ToastFocusFallback.length; fi++) {
+                            for (var mi = 0; mi < masterRows.length; mi++) {
+                                if (masterRows[mi].rowKey === paypbas140ToastFocusFallback[fi]) {
+                                    focusRow = masterRows[mi];
+                                    fi = paypbas140ToastFocusFallback.length;
+                                    break;
+                                }
+                            }
+                        }
+                    }
+                    paypbas140ToastFocusFallback = null;
+                    paypbas140Grid.focus(focusRow.rowKey);
+                } else {
+                    paypbas140ToastFocusFallback = null;
+                    paypbas141Grid.getGrid().cancel();
+                    paypbas141Grid.clear();
+                    $('#btnAddDtl').prop('disabled', true);
+                }
+                return;
+            }
+            paypbas140ToastFocusFallback = null;
+
             var targetKindCd = param.targetKindCode;
             setPayObjCombo(targetKindCd);
@@ -560,4 +593,24 @@

             $.confirm('체크된 ' + length + '개의 행을 삭제하시겠습니까?', null, function (e) {
+                if (typeof paypbas140Grid.getToastGrid === 'function') {
+                    var focusedRow = paypbas140Grid.getFocusData();
+                    var rowsBeforeDelete = paypbas140Grid.getData();
+                    var focusedIndex = -1;
+                    for (var ri = 0; focusedRow && ri < rowsBeforeDelete.length; ri++) {
+                        if (rowsBeforeDelete[ri].rowKey === focusedRow.rowKey) {
+                            focusedIndex = ri;
+                            break;
+                        }
+                    }
+                    if (focusedIndex >= 0) {
+                        paypbas140ToastFocusFallback = [];
+                        for (var ni = focusedIndex + 1; ni < rowsBeforeDelete.length; ni++) {
+                            paypbas140ToastFocusFallback.push(rowsBeforeDelete[ni].rowKey);
+                        }
+                        for (var pi = focusedIndex - 1; pi >= 0; pi--) {
+                            paypbas140ToastFocusFallback.push(rowsBeforeDelete[pi].rowKey);
+                        }
+                    }
+                }
                 for (var i = 0; i < length; i++) {
                     paypbas140Grid.removeRow(paypbas140Grid.getCheckedRows()[0].rowKey);
@@ -650,3 +703,3 @@
     </script>
 </div>
-</html>
+</html>
```

### 4.16 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paypbas/paypbas210.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paypbas/paypbas210.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paypbas/paypbas210.html
@@ -296,5 +296,5 @@
                 });

-            payCodeGrid = new UstraRealGrid('payCodeGrid', columns, {
+            var gridOptions = {
                 state: true,
                 check: true,
@@ -304,5 +304,14 @@
                     resizable: true
                 }
-            }).build();
+            };
+
+            // RealGrid 기본 헤더는 40px이다. 줄바꿈 헤더가 있는 Toast 화면에 적용되는
+            // 공통 66px 보정 대신 이 화면은 실제 RealGrid와 같은 높이를 명시한다.
+            // Toast 어댑터가 없는 RealGrid 실행 경로의 옵션 객체는 기존 값 그대로 유지한다.
+            if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                gridOptions.header = { height: 40 };
+            }
+
+            payCodeGrid = new UstraRealGrid('payCodeGrid', columns, gridOptions).build();

             payCodeGrid.getGrid().displayOptions.fitStyle = 'fill';
```

### 4.17 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/payupload/payuploadpopup100.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/payupload/payuploadpopup100.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/payupload/payuploadpopup100.html
@@ -173,4 +173,10 @@
             ;

+            // Toast는 양식 다운로드용 보조 그리드도 일반 그리드로 렌더링한다.
+            // Toast 실행에서만 숨기고 RealGrid DOM과 양식 다운로드 동작은 그대로 유지한다.
+            if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                document.getElementById(_prefix+'ExcelGrid').style.display = 'none';
+            }
+
             payuploadpopoup100Excel = new UstraRealGrid(_prefix+'ExcelGrid', columns, {
                 state: true,
@@ -422,3 +428,3 @@
 </script>
 </div>
-</html>
+</html>
```

### 4.18 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr630.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr630.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr630.html
@@ -701,10 +701,24 @@
                     id = (parseInt(lastRow.id) + 1).toString();
                 }
-                pr630Grid.insertFocusRow({id: id});
-                pr630Grid.focus(id, 1);
+                var insertedRowKey = pr630Grid.insertFocusRow({id: id});
+                // [수정일: 2026-09-01] TOAST 전용 보정.
+                // 이 화면의 id는 선언 컬럼/조회 필드가 아니어서 기존 데이터가 있으면 "NaN"이 된다.
+                // RealGrid는 잘못된 focus(id, 1)를 무시한 채 삽입 dataRow를 유지하지만,
+                // Toast는 고유 rowKey를 사용하므로 삽입 API가 돌려준 실제 키로 상세 대상을 고정한다.
+                // getToastGrid는 ustra.grid.compat.js에만 존재하므로 RealGrid 경로는 종전 호출 그대로다.
+                if (typeof pr630Grid.getToastGrid === 'function') {
+                    pr630Grid.focus(insertedRowKey, 1);
+                } else {
+                    pr630Grid.focus(id, 1);
+                }
                 disableAll(false);
                 isNew = true;

                 $('#pr630Detail').clear();
+                // sendForm은 update 상태일 때 PUT을 사용한다. 서버 신규 API도 @PutMapping이므로
+                // TOAST 신규 상세 폼만 전송 상태를 맞춘다(RealGrid 폼 상태는 변경하지 않는다).
+                if (typeof pr630Grid.getToastGrid === 'function') {
+                    $('#pr630Detail').data('state', 'update');
+                }

             }
@@ -855,3 +869,3 @@
 </div>

-</html>
+</html>
```

### 4.19 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr632.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr632.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr632.html
@@ -672,10 +672,21 @@
                             id = (parseInt(lastRow.id) + 1).toString();
                         }
-                        pr632Grid.insertFocusRow({id: id});
-                        pr632Grid.focus(id, 1);
+                        var insertedRowKey = pr632Grid.insertFocusRow({id: id});
+                        // [수정일: 2026-09-02] TOAST 전용 보정.
+                        // id는 선언 컬럼/조회 필드가 아니므로 Toast는 삽입 API가 반환한 실제 rowKey를 쓴다.
+                        // getToastGrid가 없는 RealGrid 경로는 기존 focus(id, 1)를 그대로 유지한다.
+                        if (typeof pr632Grid.getToastGrid === 'function') {
+                            pr632Grid.focus(insertedRowKey, 1);
+                        } else {
+                            pr632Grid.focus(id, 1);
+                        }
                         disableAll(false);
                         isNew = true;

                         $('#pr632Detail').clear();
+                        // sendForm은 update 상태에서 PUT을 사용한다. 서버 신규 API도 @PutMapping이다.
+                        if (typeof pr632Grid.getToastGrid === 'function') {
+                            $('#pr632Detail').data('state', 'update');
+                        }

                     }
@@ -849,3 +860,3 @@
     </div>
 </div>
-</html>
+</html>
```

### 4.20 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr633.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr633.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr633.html
@@ -663,10 +663,21 @@
                             id = (parseInt(lastRow.id) + 1).toString();
                         }
-                        pr633Grid.insertFocusRow({id: id});
-                        pr633Grid.focus(id, 1);
+                        var insertedRowKey = pr633Grid.insertFocusRow({id: id});
+                        // [수정일: 2026-09-02] TOAST 전용 보정.
+                        // id는 선언 컬럼/조회 필드가 아니므로 Toast는 삽입 API가 반환한 실제 rowKey를 쓴다.
+                        // getToastGrid가 없는 RealGrid 경로는 기존 focus(id, 1)를 그대로 유지한다.
+                        if (typeof pr633Grid.getToastGrid === 'function') {
+                            pr633Grid.focus(insertedRowKey, 1);
+                        } else {
+                            pr633Grid.focus(id, 1);
+                        }
                         disableAll(false);
                         isNew = true;

                         $('#pr633Detail').clear();
+                        // sendForm은 update 상태에서 PUT을 사용한다. 서버 신규 API도 @PutMapping이다.
+                        if (typeof pr633Grid.getToastGrid === 'function') {
+                            $('#pr633Detail').data('state', 'update');
+                        }

                     }
@@ -810,3 +821,3 @@
     </div>
 </div>
-</html>
+</html>
```

### 4.21 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr640.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr640.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr640.html
@@ -64,11 +64,11 @@
             </div>

-            <div class="columns has-gap">
-                <div class="column has-custom-width is-grown-vertically" style="width: 33%">
+            <div class="columns has-gap paywht-payment-layout">
+                <div class="column has-custom-width is-grown-vertically paywht-payment-master" style="width: 33%">
                     <!-- 대상자(master) -->
                     <div class="card is-sub is-grown-vertically">
                         <div class="card-body is-grown-vertically">
                             <div class="form is-grown-vertically">
-                                <div class="columns has-table-title">
+                                <div class="columns has-table-title paywht-payment-toolbar">
                                     <h2 class="table-title">
                                         <span>대상자</span>
@@ -102,5 +102,5 @@
                 <!-- 대상자(master) 끝 -->

-                <div class="column">
+                <div class="column paywht-payment-detail">
                     <div class="card is-sub">
                         <div class="card-body">
@@ -112,7 +112,7 @@
                                             <colgroup>
                                                 <col style="width: 100rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col style="width: 100rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col>
                                                 <col style="width: 250rem;">
@@ -142,5 +142,5 @@
                                                     ></th:block>
                                                 </td>
-                                                <td colspan="2">
+                                                <td colspan="2" class="paywht-payment-actions">
                                                     <th:block th:replace="components/security-button :: securityButton
                                                         (id='btnFileDownload', class='button is-outline is-secondary is-medium', text='간이지급명세서', role='u')"/>
@@ -327,4 +327,10 @@
                     }).build();

+                    const toastPaymentLayout = document.querySelector(".paywht-payment-layout");
+                    if (typeof earnerGrid.getToastGrid === "function" && toastPaymentLayout) {
+                        toastPaymentLayout.classList.add("is-toast-grid");
+                        toastPaymentLayout.style.setProperty("overflow-y", "visible", "important");
+                    }
+
                     earnerGrid.on("onCurrentRowChanged", function (_grid, _oldRow, _newRow) {
                         const focusedRow = earnerGrid.getFocusData();
@@ -1180,3 +1186,3 @@
         </script>
 </div>
-</html>
+</html>
```

### 4.22 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641.html
@@ -65,11 +65,11 @@
             </div>

-            <div class="columns has-gap">
-                <div class="column has-custom-width is-grown-vertically" style="width: 33%">
+            <div class="columns has-gap paywht-payment-layout">
+                <div class="column has-custom-width is-grown-vertically paywht-payment-master" style="width: 33%">
                     <!-- 대상자(master) -->
                     <div class="card is-sub is-grown-verticallyb">
                         <div class="card-body is-grown-vertically">
                             <div class="form is-grown-vertically">
-                                <div class="columns has-table-title">
+                                <div class="columns has-table-title paywht-payment-toolbar">
                                     <h2 class="table-title">
                                         <span>대상자</span>
@@ -114,5 +114,5 @@
                 <!-- 대상자(master) 끝 -->

-                <div class="column">
+                <div class="column paywht-payment-detail">
                     <div class="card is-sub">
                         <div class="card-body">
@@ -124,7 +124,7 @@
                                             <colgroup>
                                                 <col style="width: 100rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col style="width: 100rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col>
                                                 <col style="width: 250rem;">
@@ -154,5 +154,5 @@
                                                     ></th:block>
                                                 </td>
-                                                <td colspan="2">
+                                                <td colspan="2" class="paywht-payment-actions">
                                                     <input type="hidden" id="taxRateCd" name="taxRate"/>
                                                     <th:block
@@ -407,4 +407,10 @@
                     }).build();

+                    const toastPaymentLayout = document.querySelector(".paywht-payment-layout");
+                    if (typeof earnerGrid.getToastGrid === "function" && toastPaymentLayout) {
+                        toastPaymentLayout.classList.add("is-toast-grid");
+                        toastPaymentLayout.style.setProperty("overflow-y", "visible", "important");
+                    }
+
                     earnerGrid.on("onCurrentRowChanged", function (_grid, _oldRow, _newRow) {
                         const focusedRow = earnerGrid.getFocusData();
@@ -1617,3 +1623,3 @@
         </script>
     </div>
-</html>
+</html>
```

### 4.23 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641b.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641b.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641b.html
@@ -65,11 +65,11 @@
             </div>

-            <div class="columns has-gap">
-                <div class="column has-custom-width is-grown-vertically" style="width: 33%">
+            <div class="columns has-gap paywht-payment-layout">
+                <div class="column has-custom-width is-grown-vertically paywht-payment-master" style="width: 33%">
                     <!-- 대상자(master) -->
                     <div class="card is-sub is-grown-verticallyb">
                         <div class="card-body is-grown-vertically">
                             <div class="form is-grown-vertically">
-                                <div class="columns has-table-title">
+                                <div class="columns has-table-title paywht-payment-toolbar">
                                     <h2 class="table-title">
                                         <span>대상자</span>
@@ -114,5 +114,5 @@
                 <!-- 대상자(master) 끝 -->

-                <div class="column">
+                <div class="column paywht-payment-detail">
                     <!-- 개인별 급여정보 -->
                     <div class="card is-sub">
@@ -127,7 +127,7 @@
                                             <colgroup>
                                                 <col style="width: 130rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col style="width: 130rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col style="width: 250rem;">
                                             </colgroup>
@@ -156,5 +156,5 @@
                                                     ></th:block>
                                                 </td>
-                                                <td>
+                                                <td class="paywht-payment-actions">
                                                     <th:block
                                                             th:replace="components/security-button :: securityButton
@@ -433,4 +433,10 @@
                     }).build();

+                    const toastPaymentLayout = document.querySelector(".paywht-payment-layout");
+                    if (typeof earnerGrid.getToastGrid === "function" && toastPaymentLayout) {
+                        toastPaymentLayout.classList.add("is-toast-grid");
+                        toastPaymentLayout.style.setProperty("overflow-y", "visible", "important");
+                    }
+
                     earnerGrid.on("onCurrentRowChanged", function (_grid, _oldRow, _newRow) {
                         const focusedRow = earnerGrid.getFocusData();
@@ -1525,3 +1531,3 @@
         </script>
     </div>
-</html>
+</html>
```

### 4.24 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641byear.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641byear.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr641byear.html
@@ -62,9 +62,9 @@
             <div class="card is-sub">
                 <div class="card-body">
-                    <div class="columns has-gap">
+                    <div class="columns has-gap paywht-year-layout">
                         <div class="column">
                             <div class="form">
-                                <div class="columns has-table-title">
-                                    <table id="defaultSet" class="table is-search is-fullwidth" style="margin:0 !important;">
+                                <div class="columns has-table-title paywht-year-summary-toolbar">
+                                    <table id="defaultSet" class="table is-search is-fullwidth paywht-year-summary" style="margin:0 !important;">
                                         <colgroup>
                                             <col style="width: 25%;">
@@ -76,11 +76,11 @@
                                         <tbody>
                                         <tr>
-                                            <th colspan="2" style="text-align: left;">
+                                            <th colspan="2" class="paywht-year-summary-title" style="text-align: left;">
                                                 <h2 class="table-title">
                                                     <span>원천징수의무자 인적사항 및 지급내용 합계사항</span>
                                                 </h2>
                                             </th>
-                                            <th><span>제출일자</span></th>
-                                            <td>
+                                            <th class="paywht-year-submit-label"><span>제출일자</span></th>
+                                            <td class="paywht-year-submit-date">
                                                 <th:block th:replace="components/datepicker :: datepicker(
                                             id='payDateinput',
@@ -93,6 +93,6 @@
                                                 ></th:block>
                                             </td>
-                                            <td style="padding-right:10px; vertical-align: middle;">
-                                                <div style="display: flex; align-items: center; justify-content: flex-end; gap: 8px; height: 100%;">
+                                            <td class="paywht-year-summary-actions" style="padding-right:10px; vertical-align: middle;">
+                                                <div class="paywht-year-summary-buttons" style="display: flex; align-items: center; justify-content: flex-end; gap: 8px; height: 100%;">
                                                     <th:block
                                                             th:replace="components/security-button :: securityButton
@@ -121,8 +121,8 @@
                                     </table>
                                 </div>
-                                <div id="officeInfoGrid" class="realgrid" style="height: 150rem; white-space: pre;"></div>
+                                <div id="officeInfoGrid" class="realgrid paywht-year-grid" style="height: 150rem; white-space: pre;"></div>

                                 <div class="form">
-                                    <div class="columns has-table-title">
+                                    <div class="columns has-table-title paywht-year-detail-toolbar">
                                         <h2 class="table-title">
                                             <span>소득자 인적사항 및 연간 소득내용</span>
@@ -140,5 +140,5 @@
                                         </div>
                                     </div>
-                                    <div id="cRecordGrid" class="realgrid" style="height: 600rem; white-space: pre;"></div>
+                                    <div id="cRecordGrid" class="realgrid paywht-year-grid" style="height: 600rem; white-space: pre;"></div>
                                 </div>
                             </div>
@@ -334,4 +334,10 @@

             officeInfoGrid.setHeaderLayout(layout);
+            if (typeof officeInfoGrid.getToastGrid === 'function') {
+                const officeInfoToastGrid = officeInfoGrid.getToastGrid();
+                const officeInfoGridHeight = document.getElementById('officeInfoGrid').clientHeight;
+                officeInfoGrid.getGrid().header.height = 80;
+                officeInfoToastGrid.setBodyHeight(Math.max(40, officeInfoGridHeight - 80));
+            }
             officeInfoGrid.getGrid().displayOptions.fitStyle = 'fill';
         }
@@ -368,10 +374,22 @@
             ;

-            cRecordGrid = new UstraRealGrid('cRecordGrid', columns, {
+            const cRecordGridOptions = {
                 check: false,
                 state: false,
                 rownum: true,
                 headerHeight: 60
-            }).build();
+            };
+            if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                cRecordGridOptions.header = {height: cRecordGridOptions.headerHeight};
+            }
+
+            cRecordGrid = new UstraRealGrid('cRecordGrid', columns, cRecordGridOptions).build();
+
+            if (typeof cRecordGrid.getToastGrid === 'function') {
+                const toastYearLayout = document.querySelector('.paywht-year-layout');
+                if (toastYearLayout) {
+                    toastYearLayout.classList.add('is-toast-grid');
+                }
+            }

             cRecordGrid.getGrid().displayOptions.fitStyle = 'fill';
@@ -491,3 +509,3 @@
     </script>
 </div>
-</html>
+</html>
```

### 4.25 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr642.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr642.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr642.html
@@ -64,11 +64,11 @@
             </div>

-            <div class="columns has-gap" style="overflow-y: auto">
-                <div class="column has-custom-width is-grown-vertically" style="width: 33%">
+            <div class="columns has-gap paywht-payment-layout" style="overflow-y: auto">
+                <div class="column has-custom-width is-grown-vertically paywht-payment-master" style="width: 33%">
                     <!-- 대상자(master) -->
                     <div class="card is-sub is-grown-vertically">
                         <div class="card-body is-grown-vertically">
                             <div class="form is-grown-vertically">
-                                <div class="columns has-table-title">
+                                <div class="columns has-table-title paywht-payment-toolbar">
                                     <h2 class="table-title">
                                         <span>대상자</span>
@@ -108,5 +108,5 @@
                 <!-- 대상자(master) 끝 -->

-                <div class="column">
+                <div class="column paywht-payment-detail">
                     <div class="card is-sub">
                         <div class="card-body">
@@ -118,7 +118,7 @@
                                             <colgroup>
                                                 <col style="width: 100rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col style="width: 100rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col>
                                                 <col style="width: 250rem;">
@@ -148,5 +148,5 @@
                                                     ></th:block>
                                                 </td>
-                                                <td colspan="2" style="padding-right: 20rem">
+                                                <td colspan="2" class="paywht-payment-actions" style="padding-right: 20rem">
                                                     <input id="taxRate" name="taxRate" type="hidden"/>

@@ -348,4 +348,10 @@
                     }).build();

+                    const toastPaymentLayout = document.querySelector(".paywht-payment-layout");
+                    if (typeof earnerGrid.getToastGrid === "function" && toastPaymentLayout) {
+                        toastPaymentLayout.classList.add("is-toast-grid");
+                        toastPaymentLayout.style.setProperty("overflow-y", "visible", "important");
+                    }
+
                     earnerGrid.on("onCurrentRowChanged", function (_grid, _oldRow, _newRow) {
                         const focusedRow = earnerGrid.getFocusData();
@@ -1209,3 +1215,3 @@
         </script>
 </div>
-</html>
+</html>
```

### 4.26 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr643.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr643.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr643.html
@@ -64,11 +64,11 @@
             </div>

-            <div class="columns has-gap" style="overflow-y: auto">
-                <div class="column has-custom-width is-grown-vertically" style="width: 33%">
+            <div class="columns has-gap paywht-payment-layout" style="overflow-y: auto">
+                <div class="column has-custom-width is-grown-vertically paywht-payment-master" style="width: 33%">
                     <!-- 대상자(master) -->
                     <div class="card is-sub is-grown-vertically">
                         <div class="card-body is-grown-vertically">
                             <div class="form is-grown-vertically">
-                                <div class="columns has-table-title">
+                                <div class="columns has-table-title paywht-payment-toolbar">
                                     <h2 class="table-title">
                                         <span>대상자</span>
@@ -108,5 +108,5 @@
                 <!-- 대상자(master) 끝 -->

-                <div class="column">
+                <div class="column paywht-payment-detail">
                     <div class="card is-sub">
                         <div class="card-body">
@@ -118,7 +118,7 @@
                                             <colgroup>
                                                 <col style="width: 100rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col style="width: 100rem;">
-                                                <col>
+                                                <col class="paywht-payment-date-column">
                                                 <col>
                                                 <col style="width: 250rem;">
@@ -148,5 +148,5 @@
                                                     ></th:block>
                                                 </td>
-                                                <td colspan="2" style="padding-right: 20rem">
+                                                <td colspan="2" class="paywht-payment-actions" style="padding-right: 20rem">
                                                     <input id="taxRate" name="taxRate" type="hidden"/>

@@ -371,4 +371,10 @@
                 }).build();

+                const toastPaymentLayout = document.querySelector(".paywht-payment-layout");
+                if (typeof earnerGrid.getToastGrid === "function" && toastPaymentLayout) {
+                    toastPaymentLayout.classList.add("is-toast-grid");
+                    toastPaymentLayout.style.setProperty("overflow-y", "visible", "important");
+                }
+
                 earnerGrid.on("onCurrentRowChanged", function (_grid, _oldRow, _newRow) {
                     const focusedRow = earnerGrid.getFocusData();
@@ -1185,3 +1191,3 @@
     </script>
 </div>
-</html>
+</html>
```

### 4.27 `ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr671.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr671.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/pay/paywht/pr671.html
@@ -6,4 +6,11 @@

 <div layout:fragment="content">
+    <style>
+        /* Toast 전용 표지 클래스는 initGrid에서만 붙인다. RealGrid 레이아웃에는 적용되지 않는다. */
+        .columns.has-gap > .column.pr671-toast-summary-column {
+            flex: 1 1 0;
+            min-width: 0;
+        }
+    </style>
     <div class="page">
         <div class="page-body">
@@ -368,4 +375,16 @@
         // 그리드 초기화
         function initGrid() {
+            var isToastGrid = typeof UstraRealGrid.prototype.getToastGrid === 'function';
+            // RealGrid는 build 후 setFooter 콜백으로 합계 숫자 형식을 #,##0으로 덮어쓴다.
+            // Toast summary는 빌드 시점에만 설정할 수 있으므로 같은 최종 형식을 미리 적용한다.
+            var footerNumberFormat = isToastGrid ? '#,##0' : '#,##0.##';
+            if (isToastGrid) {
+                // Toast는 내부 컬럼 폭이 flex-basis:auto 계산에 반영되어 과세/비과세 영역 폭이
+                // 달라질 수 있다. 두 합계 영역을 RealGrid 화면처럼 남은 폭의 1:1로 고정한다.
+                $('#pr660GridTax, #pr660GridNotax')
+                    .closest('.column')
+                    .addClass('pr671-toast-summary-column');
+            }
+
             // 과세
             let columns = new UstraRealGridColumn()
@@ -382,5 +401,5 @@
                     footer: {
                         expression: 'sum',
-                        numberFormat: '#,##0.##',
+                        numberFormat: footerNumberFormat,
                     },
                 });
@@ -393,11 +412,13 @@
             }).build();

-            pr660GridTax._grid.setFooter({
-                cellStyleCallback: function () {
-                    return {
-                        numberFormat: '#,##0'
-                    };
-                },
-            });
+            if (!isToastGrid) {
+                pr660GridTax._grid.setFooter({
+                    cellStyleCallback: function () {
+                        return {
+                            numberFormat: '#,##0'
+                        };
+                    },
+                });
+            }
             pr660GridTax.getGrid().displayOptions.fitStyle = 'fill';

@@ -416,5 +437,5 @@
                     footer: {
                         expression: 'sum',
-                        numberFormat: '#,##0.##',
+                        numberFormat: footerNumberFormat,
                     },
                 });
@@ -427,12 +448,22 @@
             }).build();

-            pr660GridNotax._grid.setFooter({
-                cellStyleCallback: function () {
-                    return {
-                        numberFormat: '#,##0'
-                    };
-                },
-            });
+            if (!isToastGrid) {
+                pr660GridNotax._grid.setFooter({
+                    cellStyleCallback: function () {
+                        return {
+                            numberFormat: '#,##0'
+                        };
+                    },
+                });
+            }
             pr660GridNotax.getGrid().displayOptions.fitStyle = 'fill';
+
+            if (isToastGrid) {
+                // 두 그리드를 최종 flex 폭으로 다시 측정하고 동일한 fill 옵션을 재적용한다.
+                pr660GridTax.refreshLayout();
+                pr660GridNotax.refreshLayout();
+                pr660GridTax.getGrid().displayOptions.fitStyle = 'fill';
+                pr660GridNotax.getGrid().displayOptions.fitStyle = 'fill';
+            }
         }

```

### 4.28 `ustra-hr-system-thymeleaf/src/main/resources/templates/popup/psmst/address-pop.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/popup/psmst/address-pop.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/popup/psmst/address-pop.html
@@ -78,9 +78,17 @@
                 ;

-            _addressPopGrid = new UstraRealGrid(_prefix+'Grid', columns, {
+            var gridOptions = {
                 rownum: true,
                 check: false,
                 editable: false,
-            }).build();
+            };
+            // Toast는 주소 3개 필드를 한 행 안에 세로로 표시하므로 기본 40px에서는
+            // 각 주소 줄의 위아래 여백이 부족하다. 400px 팝업에서 약 5개 결과가 보이도록
+            // Toast에만 66px을 적용한다. RealGrid는 기존 옵션과 행 높이를 그대로 사용한다.
+            if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                gridOptions.rowHeight = 66;
+            }
+
+            _addressPopGrid = new UstraRealGrid(_prefix+'Grid', columns, gridOptions).build();

             //그리드 채우기
@@ -265,3 +273,3 @@
 </div>
 <!-- [ psmstr-pop ] end -->
-</html>
+</html>
```

### 4.29 `ustra-hr-system-thymeleaf/src/main/resources/templates/popup/psmst/cdbase-pop.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/popup/psmst/cdbase-pop.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/popup/psmst/cdbase-pop.html
@@ -91,5 +91,5 @@
             .add('codeNameHan', '코드명', 'i18n', 200, true);

-            _cdbasePopGrid = new UstraRealGrid(_prefix+'Grid', columns, {
+            var gridOptions = {
              /*    rownum: false,
                 check:true
@@ -99,5 +99,16 @@
                 check: false,
                 editable: false,
-            }).build();
+            };
+
+            // [수정일: 2026-09-02] TOAST 전용 보정.
+            // 이 팝업은 2개 컬럼을 fitStyle='fill'로 폭에 맞추지만 ToastGrid의 기본값
+            // scrollX:true가 컬럼 폭과 무관하게 가로 스크롤 영역을 항상 만든다.
+            // 생성 후 내부 dimension을 변경하지 않고 공개 생성 옵션을 사용하며,
+            // getToastGrid가 없는 RealGrid 경로의 옵션과 동작은 그대로 둔다.
+            if (typeof UstraRealGrid.prototype.getToastGrid === 'function') {
+                gridOptions.scrollX = false;
+            }
+
+            _cdbasePopGrid = new UstraRealGrid(_prefix+'Grid', columns, gridOptions).build();

             //그리드 채우기
@@ -216,3 +227,3 @@
 </div>
 <!-- [ cdbase-pop ] end -->
-</html>
+</html>
```

### 4.30 `ustra-hr-system-thymeleaf/src/main/resources/templates/system/aprvtransfer.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/system/aprvtransfer.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/system/aprvtransfer.html
@@ -300,6 +300,26 @@
         let msg = "체크된 " + length + "개의 행을 삭제하시겠습니까?".replace('__::ROW::__', length);

-            $.confirm(msg,null, function (e) {
-          let param = transferGrid.toDataJson('dataList', dataList);
+        $.confirm(msg,null, function (e) {
+          let deleteDataList = dataList;
+
+          // Toast는 아직 저장하지 않은 신규 행도 선택 행(rowStatus=C)으로 반환한다.
+          // DB에 없는 신규 행을 /delete로 보내면 필수 시간값이 없어 서버 삭제 로직에서 오류가 난다.
+          // Toast 신규 행만 로컬에서 제거하고, RealGrid 및 기존 행의 서버 삭제 흐름은 유지한다.
+          if (typeof transferGrid.getToastGrid === 'function') {
+            let createdDataList = _.filter(dataList, function (row) {
+              return row.rowStatus === 'C';
+            });
+            deleteDataList = _.filter(dataList, function (row) {
+              return row.rowStatus !== 'C';
+            });
+
+            $.each(createdDataList, function (i, row) {
+              transferGrid.removeRow(row.rowKey);
+            });
+
+            if (deleteDataList.length <= 0) return;
+          }
+
+          let param = transferGrid.toDataJson('dataList', deleteDataList);
           if (param) {
             $.post(apiUrl + "/delete", param, function (res) {
```

### 4.31 `ustra-hr-system-thymeleaf/src/main/resources/templates/system/deptinquiry.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/system/deptinquiry.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/system/deptinquiry.html
@@ -166,4 +166,12 @@

         const btnSave2Click = () => {
+            // Toast는 알림 모달로 포커스가 이동하면 현재 행이 비워질 수 있으므로
+            // 저장 시작 시점의 사용자를 보관해 후속 부서 조회에 사용한다.
+            const isToastGrid = typeof roleDeptUserGrid.getToastGrid === 'function';
+            const focusData = isToastGrid ? roleDeptUserGrid.getFocusData() : null;
+            if (isToastGrid && isNull(focusData)) {
+                $.alert("권한에 등록된 사용자를 선택해주세요.");
+                return;
+            }
             var param = orgGrid.getModifiedDataJson('deptInquiryDtoList');

@@ -182,8 +190,8 @@
                     if (result && result > 0) {
                         $.alert('저장 되었습니다.');
-                        searchOrgGrid();
+                        searchOrgGrid(isToastGrid ? focusData : null);
                     } else {
                         $.error('저장에 실패하였습니다.');
-                        searchOrgGrid();
+                        searchOrgGrid(isToastGrid ? focusData : null);
                     }
                 });
@@ -192,4 +200,10 @@

         const btnRemove2Click = () => {
+            const isToastGrid = typeof roleDeptUserGrid.getToastGrid === 'function';
+            const focusData = isToastGrid ? roleDeptUserGrid.getFocusData() : null;
+            if (isToastGrid && isNull(focusData)) {
+                $.alert("권한에 등록된 사용자를 선택해주세요.");
+                return false;
+            }
             var length = orgGrid.getCheckedRows().length;
             if (length <= 0) {
@@ -206,5 +220,5 @@
                     $.alert('삭제 되었습니다.');
                 }).always(function (data, textStatus) {
-                    searchOrgGrid();
+                    searchOrgGrid(isToastGrid ? focusData : null);
                 });
             });
@@ -451,9 +465,28 @@
         }

-        const searchOrgGrid = () => {
-            const focusData = roleDeptUserGrid.getFocusData();
+        const searchOrgGrid = (selectedUser) => {
+            const focusData = selectedUser || roleDeptUserGrid.getFocusData();
+            if (isNull(focusData)) {
+                orgGrid.clear();
+                return;
+            }
             getPrograms(focusData.roleId, focusData.userId);
         }

+        // RealGrid reload() 경로는 그대로 두고, Toast에서만 저장/삭제 시작 시 보관한
+        // 권한과 사용자 검색 조건으로 명시적으로 다시 조회한다.
+        const reloadRoleDeptUserGrid = (roleData, userNm) => {
+            if (typeof roleDeptUserGrid.getToastGrid === 'function') {
+                if (isNull(roleData)) {
+                    roleDeptUserGrid.clear();
+                    orgGrid.clear();
+                    return;
+                }
+                getRoleDeptUser(roleData, userNm);
+                return;
+            }
+            roleDeptUserGrid.reload();
+        }
+
         function getPrograms(roleId, emplNo) {
             orgGrid.load(apiUrl + '/deptinquiry', {roleId: roleId, emplNo: emplNo});
@@ -484,4 +517,7 @@

         $('#btnSave').on('click', function (e) {
+            var isToastGrid = typeof roleDeptUserGrid.getToastGrid === 'function';
+            var selectedRole = isToastGrid ? roleGrid.getFocusData() : null;
+            var userNm = isToastGrid ? $('input[name = emplName]').val() : null;
             var param = roleDeptUserGrid.getModifiedDataJson('roleDeptUserDtoList');
             if (param == null) {
@@ -495,5 +531,5 @@
                     if ((data == '' || data == null)) {
                         $.alert('저장 되었습니다.', '알림', function () {
-                            roleDeptUserGrid.reload();
+                            reloadRoleDeptUserGrid(selectedRole, userNm);
                         });

@@ -506,4 +542,7 @@

         $('#btnRemove').on('click', function (e) {
+            var isToastGrid = typeof roleDeptUserGrid.getToastGrid === 'function';
+            var selectedRole = isToastGrid ? roleGrid.getFocusData() : null;
+            var userNm = isToastGrid ? $('input[name = emplName]').val() : null;
             var checkedRows = roleDeptUserGrid.getCheckedRows();
             var length = checkedRows.length;
@@ -520,5 +559,5 @@
                     if ((data == '' || data == null)) {
                         $.alert('삭제 되었습니다.');
-                        roleDeptUserGrid.reload();
+                        reloadRoleDeptUserGrid(selectedRole, userNm);
                     } else {
                         $.error('삭제 실패하였습니다.');
```

### 4.32 `ustra-hr-system-thymeleaf/src/main/resources/templates/system/roles/deptuser.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/system/roles/deptuser.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/system/roles/deptuser.html
@@ -441,4 +441,18 @@
         }

+        // RealGrid는 기존 reload() 동작을 유지하고, Toast만 저장/삭제 시작 시 보관한
+        // 권한과 사용자 검색 조건으로 명시적으로 다시 조회한다.
+        function reloadRoleDeptUserGrid(roleData, userNm) {
+            if (typeof roleDeptUserGrid.getToastGrid === 'function') {
+                if (roleData == null) {
+                    roleDeptUserGrid.clear();
+                    return;
+                }
+                getRoleDeptUser(roleData, userNm);
+                return;
+            }
+            roleDeptUserGrid.reload();
+        }
+
         function loadRole() {
             var params = $('#searchTable').getData();
@@ -505,8 +519,9 @@
             $.confirm('저장히시겠습니까?', null, function (e) {
                 var focusedCell = roleGrid.getFocusedCell();
+                var userNm = $('input[name = emplName]').val();
                 $.post('/system/roles/deptusers', param, function (data) {
                     if ((data == '' || data == null)) {
                         $.alert('저장 되었습니다.', '알림', function () {
-                            roleDeptUserGrid.reload();
+                            reloadRoleDeptUserGrid(focusedCell, userNm);
                         });

@@ -529,9 +544,10 @@
                 var param = roleDeptUserGrid.toDataJson('roleDeptUserDtoList', checkedRows);
                 var focusedCell = roleGrid.getFocusedCell();
+                var userNm = $('input[name = emplName]').val();

                 $.delete('/system/roles/deptusers', param, function (data) {
                     if ((data == '' || data == null)) {
                         $.alert('삭제 되었습니다.');
-                        roleDeptUserGrid.reload();
+                        reloadRoleDeptUserGrid(focusedCell, userNm);
                     } else {
                         $.error('삭제 실패하였습니다.');
```

### 4.33 `ustra-hr-system-thymeleaf/src/main/resources/templates/wrk/wrkupload/wrkuploaddesc100.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/wrk/wrkupload/wrkuploaddesc100.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/wrk/wrkupload/wrkuploaddesc100.html
@@ -427,5 +427,6 @@
         //그리드 내용 삭제
         $(document).on('click', '#btnRemove', function (e) {
-            var length = wrkdescUploadGrid.getCheckedRows().length;
+            var checkedRows = wrkdescUploadGrid.getCheckedRows();
+            var length = checkedRows.length;
             if (length <= 0) {
                 $.alert('삭제할 데이터를 선택해주세요.');
@@ -434,4 +435,39 @@

             $.confirm('체크된 ' + length + '개의 행을 삭제하시겠습니까?', null, function (e) {
+                // Toast는 행을 먼저 제거한 뒤 삭제 데이터를 다시 읽는다. 그래서 요청이 실패해도
+                // 화면에서만 행이 사라지고, 재조회하면 DB의 원래 행이 다시 보였다. 선택 시점의
+                // RealGrid 호환 행으로 D 페이로드를 먼저 만들고 서버 삭제 성공 후 재조회한다.
+                if (typeof wrkdescUploadGrid.getToastGrid === 'function') {
+                    var createdRows = checkedRows.filter(function (row) {
+                        return row.rowStatus === 'C';
+                    });
+                    var persistedRows = checkedRows.filter(function (row) {
+                        return row.rowStatus !== 'C';
+                    }).map(function (row) {
+                        return $.extend({}, row, {rowStatus: 'D'});
+                    });
+
+                    if (createdRows.length > 0) {
+                        wrkdescUploadGrid.removeRows(createdRows.map(function (row) {
+                            return row.rowKey;
+                        }));
+                    }
+
+                    if (persistedRows.length === 0) {
+                        return;
+                    }
+
+                    var toastParam = wrkdescUploadGrid.toDataJson('wrkUploadDtoList', persistedRows);
+                    $.delete(apiUrl, toastParam, function (result) {
+                        search();
+                        if (result && result > 0) {
+                            $.alert('삭제 되었습니다.');
+                        } else {
+                            $.error('삭제에 실패하였습니다.');
+                        }
+                    });
+                    return;
+                }
+
                 wrkdescUploadGrid.removeRows(wrkdescUploadGrid.getCheckedRowKeys());
                 var param = wrkdescUploadGrid.getRemovedDataJson("wrkUploadDtoList");
```

### 4.34 `ustra-hr-system-thymeleaf/src/main/resources/templates/wrk/wrkupload/wrkuploadyear100.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/wrk/wrkupload/wrkuploadyear100.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/wrk/wrkupload/wrkuploadyear100.html
@@ -221,5 +221,6 @@
         //그리드 내용 삭제
         $(document).on('click', '#btnRemove', function (e) {
-            var length = wrkyearUploadGrid.getCheckedRows().length;
+            var checkedRows = wrkyearUploadGrid.getCheckedRows();
+            var length = checkedRows.length;
             if (length <= 0) {
                 $.alert('삭제할 데이터를 선택해주세요.');
@@ -228,4 +229,39 @@

             $.confirm('체크된 ' + length + '개의 행을 삭제하시겠습니까?', null, function (e) {
+                // Toast는 행을 먼저 제거한 뒤 삭제 데이터를 다시 읽는 과정에서 화면만 사라지고
+                // 요청 데이터가 비는 구형 경로가 있었다. 선택 시점의 RealGrid 호환 행으로
+                // D 페이로드를 먼저 만들고, 서버 삭제 성공 후 재조회한다.
+                if (typeof wrkyearUploadGrid.getToastGrid === 'function') {
+                    var createdRows = checkedRows.filter(function (row) {
+                        return row.rowStatus === 'C';
+                    });
+                    var persistedRows = checkedRows.filter(function (row) {
+                        return row.rowStatus !== 'C';
+                    }).map(function (row) {
+                        return $.extend({}, row, {rowStatus: 'D'});
+                    });
+
+                    if (createdRows.length > 0) {
+                        wrkyearUploadGrid.removeRows(createdRows.map(function (row) {
+                            return row.rowKey;
+                        }));
+                    }
+
+                    if (persistedRows.length === 0) {
+                        return;
+                    }
+
+                    var toastParam = wrkyearUploadGrid.toDataJson('wclvedayList', persistedRows);
+                    $.delete(apiUrl, toastParam, function (result) {
+                        search();
+                        if (result && result > 0) {
+                            $.alert('삭제 되었습니다.');
+                        } else {
+                            $.error('삭제에 실패하였습니다.');
+                        }
+                    });
+                    return;
+                }
+
                 wrkyearUploadGrid.removeRows(wrkyearUploadGrid.getCheckedRowKeys());
                 var param = wrkyearUploadGrid.getRemovedDataJson("wclvedayList");
```

## 5. 제외한 변경

- `sch` → `saas` 프로젝트명 치환
- Toast 엔진과 무관한 일반 업무 규칙·필수값·문구 수정
- 공통 어댑터 자체 변경(뷰 코드가 아니므로 이 문서의 뷰 비교에서는 제외)
- 조직도 자체 엔진 등 Toast Grid를 사용하지 않는 화면

## 6. 참고 파일

- `docs/toast-grid-migration/PROGRESS.md`
- `docs/toast-grid-migration/TOAST_GRID_ADAPTER_ASIS_TOBE.md`
- `ustra-hr-system-thymeleaf/src/main/resources/static/assets/js/ustra-utils/ustra.grid.compat.js`

