# feature/toast_2차 Toast 관련 뷰페이지 AS-IS / TO-BE

작성 기준: `feature/toast` → `feature/toast_2차` 고유 변경분
기준일: 2026-09-18

## 1. 범위와 판정 기준

이 문서는 세 브랜치 전체가 아니라 `feature/toast_2차`에만 추가된 커밋 중 Toast Grid 동작·호환과 직접 연결된 뷰페이지를 정리한다. 일반 업무 규칙, 문구·권한·CSS 수정은 제외했다.

- 비교 기준: `git diff feature/toast feature/toast_2차`
- 브랜치 고유 커밋: 24개
- Toast 관련 뷰 파일: **10개**
- `AS-IS`: diff의 `-` 라인
- `TO-BE`: diff의 `+` 라인

## 2. 2차 브랜치의 Toast 관련 커밋

| 커밋 | 뷰 영향 | 내용 |
|---|---|---|
| `8b16569` | `atypical100.html`, `pgmbas.html` | 셀렉트 표시, 정렬 기준, 행 포커스, 삭제 후 현재 행 정합 |
| `a275bd2` | `ororghier200.html`, `wrkleavemng100.html` | RealGrid 동작 정합, Toast 레이아웃·Excel 숫자 형식 보정 |
| `415b265`, `877bcfa` | `atypical100.html`, `atypical200.html` | 신규행 삭제/선택, Toast 셀렉트 레이어 보정 |
| 관련 화면 커밋 | `orareamng250.html`, `aprvtransfer.html`, `comng100.html`, `programs.html`, `wrkbasecode100.html` | Toast 상태값·신규행·삭제 페이로드를 고려한 뷰 예외 |

## 3. 처리 원칙

공통 어댑터에서 처리할 수 있는 API 변환은 `ustra.grid.compat.js`에 두고, 뷰에서는 해당 메뉴의 저장 순서·상세 연동·팝업 레이어·Toast 전용 레이아웃처럼 업무 화면에 종속된 부분만 조건 분기한다. 대표적인 조건은 다음과 같다.

`typeof grid.getToastGrid === 'function'` 또는 `typeof UstraRealGrid.prototype.getToastGrid === 'function'`가 참일 때만 Toast 보정을 실행하며, RealGrid 경로는 기존 코드를 유지한다.

## 4. 실제 코드 비교

아래는 2차 브랜치 고유 변경 중 Toast 관련으로 판정한 10개 파일의 실제 변경 hunk다.

### 4.1 `ustra-hr-system-thymeleaf/src/main/resources/templates/atypical/atypical100.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/atypical/atypical100.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/atypical/atypical100.html
@@ -31,9 +31,9 @@
                                     <span>관련업무</span>
                                 </th>
                                 <td class="overflow-visible">
-                                    <div class="field">
+                                    <div class="field field-select">
                                         <div class="control is-select is-medium" style="max-width: 170rem;">
-                                            <select id="arrBusnMidClCode" name="arrBusnMidClCode" multiple placeholder="전체" style="max-width: 170rem;">
+                                            <select id="arrBusnMidClCode" name="arrBusnMidClCode" data-container="body" multiple placeholder="전체" style="max-width: 170rem;">
                                             </select>
                                         </div>
                                     </div>
@@ -119,6 +119,58 @@

     var $comSelect = [];

+    // ★★★ [추가일: 2026-09-07] 추가이유: RealGrid 는 removeRow 뒤에도 **현재 행 인덱스**가 남아
+    //   그 자리의 행으로 onCurrentRowChanged 가 발생하지만(실측: 추가한 신규행 삭제 후
+    //   포커스가 1행으로 돌아오고 하단 3개 탭이 그 행으로 다시 조회된다),
+    //   tui-grid 의 포커스는 고유 rowKey 라 지운 행의 포커스만 해제되고 이벤트가 오지 않는다.
+    //   그래서 Toast 에서만 미저장 신규행을 삭제하면 선택이 사라지고 하단이 빈 상태로 남았다
+    //   (서버 삭제가 없어 재조회도 돌지 않는다).
+    // 추가내용: Toast 엔진에서만 삭제 직전의 현재 행 인덱스를 기억해 두고 아래 crudOptions.remove
+    //   의 onRemove 에서 같은 인덱스 행으로 포커스를 되돌린다. getToastGrid 는
+    //   ustra.grid.compat.js 에만 있으므로 RealGrid 경로는 이 보정을 타지 않는다.
+    var $atyDelIndex = -1;
+
+    function isToastGridEngine() {
+        return typeof UstraRealGrid.prototype.getToastGrid === 'function';
+    }
+
+    function atyRowIndexOf(grid, rowKey) {
+        var rows = grid.getData() || [];
+        for (var i = 0; i < rows.length; i++) {
+            if (rows[i] && rows[i].rowKey === rowKey) return i;
+        }
+        return -1;
+    }
+
+    // 삭제 버튼의 클릭 핸들러(그리드 공통 CRUD)보다 먼저 실행되도록 mousedown 에 건다.
+    $(document).on('mousedown', '#btnDelAty', function () {
+        $atyDelIndex = -1;
+        if (!isToastGridEngine() || !atypicalbasGrid) {
+            return;
+        }
+        var focusData = atypicalbasGrid.getFocusData();
+        if (focusData) {
+            $atyDelIndex = atyRowIndexOf(atypicalbasGrid, focusData.rowKey);
+        }
+    });
+
+    // 미저장 신규행 삭제(서버 호출 없음) 뒤 RealGrid 와 같은 현재 행/하단 상태로 되돌린다.
+    function afterRemoveRowLocal(hasRegisteredDelete) {
+        if (!isToastGridEngine() || hasRegisteredDelete) {
+            return;   // 등록된 데이터 삭제는 onSuccess → search() 가 처리한다
+        }
+        var rows = atypicalbasGrid.getData() || [];
+        if (rows.length === 0) {
+            $atySeqNo.val('');
+            atypicalRoleGrid.clear();
+            atypicalInputGrid.clear();
+            atypicalOutputGrid.clear();
+            return;
+        }
+        var index = $atyDelIndex < 0 ? 0 : Math.min($atyDelIndex, rows.length - 1);
+        atypicalbasGrid.focus(rows[index].rowKey);
+    }
+
     Ustra.docReady(function () {
         initCode();
     }, document);
@@ -211,7 +263,8 @@
                 },
                 remove: {
                     button: 'btnDelAty', fieldName: 'atyprocList', url: apiUrl, btnEnable: true,
-                    onSuccess: afterRemoveRow, onError: afterRemoveRowError
+                    onSuccess: afterRemoveRow, onError: afterRemoveRowError,
+                    onRemove: afterRemoveRowLocal
                 }
             }
         }).build();
@@ -219,17 +272,15 @@

         atypicalbasGrid.on("onCurrentRowChanged", function (_grid, _oldRow, _newRow) {
             var data = this.getRow(_newRow);
+            console.log("##########newData", data);
             setTimeout(function () {
-                if (_newRow != -1) { // 조회된 값이 없으면 script 오류가 발생하므로 필히 체크한다.
+                if (data.rowStatus !== "C" && _newRow !== -1) { // 조회된 값이 없으면 script 오류가 발생하므로 필히 체크한다.
                     var params = {atySeqNo: data.atySeqNo};
                     $atySeqNo.val(data.atySeqNo);
                     atypicalbas10Setting(params);
                     atypicalbas20Setting(params);
                     atypicalbas30Setting(params);

-                    atypicalbasGrid.uncheckAll();
-                    atypicalbasGrid.check(data.rowKey);
-
                     $rowStatus = atypicalbasGrid.getRowState(data.rowKey);
                     switch ($rowStatus) {
                         case "C":
@@ -249,6 +300,9 @@
                     atypicalInputGrid.clear();
                     atypicalOutputGrid.clear();
                 }
+
+                atypicalbasGrid.uncheckAll();
+                atypicalbasGrid.check(data.rowKey);
             }, 200);
         });

@@ -267,7 +321,22 @@
         $(activeTab).fadeIn();

         var params = $('#searchTable').getData();
-        atypicalbasGrid.load(apiUrl, params, function () {
+        atypicalbasGrid.load(apiUrl, params, function (data) {
+            // ★★★ [수정일: 2026-09-07] 수정이유: 제보 "삭제 이후 하단 데이터 정보가 그대로 남아있음".
+            //   기준목록이 0건이 되면 포커스를 옮길 행이 없어 Toast 는 onCurrentRowChanged 가
+            //   아예 오지 않고(tui-grid 는 focus(0) 이 대상 행이 없으면 이벤트를 발생시키지 않는다),
+            //   상단 onCurrentRowChanged 의 else 분기(하단 3개 그리드 clear)가 실행되지 않으며
+            //   지워진 기준의 대상자등록·입력설정·출력설정 데이터와 건수가 화면에 그대로 남았다.
+            // 수정내용: Toast 엔진(getToastGrid 존재)에서만 조회 결과가 0건이면
+            //   선택 일련번호와 하단 3개 그리드를 바로 버린다. RealGrid 경로는 이 분기를 타지 않으므로
+            //   기존 동작을 그대로 유지한다.
+            if ((!data || data.length === 0) && isToastGridEngine()) {
+                $atySeqNo.val('');
+                atypicalRoleGrid.clear();
+                atypicalInputGrid.clear();
+                atypicalOutputGrid.clear();
+                return;
+            }
             setTimeout(function () {
                 atypicalbasGrid.focus(0);
                 /* 이안에 코딩하지 마세요  상위 onCurrentRowChanged Event 참조 */
@@ -281,7 +350,10 @@
         rowIndex = rowIndex + "0";

         initButtonSet10();
+
         atypicalbasGrid.insertFocusRow({openInd: "Y", displayOrder: rowIndex});
+        atypicalbasGrid.uncheckAll();
+        atypicalbasGrid.check(atypicalbasGrid.getFocusData().rowKey);
     });

     //탭 클릭 시 조회
```

### 4.2 `ustra-hr-system-thymeleaf/src/main/resources/templates/atypical/atypical200.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/atypical/atypical200.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/atypical/atypical200.html
@@ -32,7 +32,7 @@
                                 <td class="overflow-visible">
                                     <div class="field">
                                         <div class="control is-select is-medium" style="max-width: 170rem;">
-                                            <select id="arrBusnMidClCode" name="arrBusnMidClCode" multiple placeholder="전체" style="max-width: 170rem;">
+                                            <select id="arrBusnMidClCode" name="arrBusnMidClCode" data-container="body" multiple placeholder="전체" style="max-width: 170rem;">
                                             </select>
                                         </div>
                                     </div>
```

### 4.3 `ustra-hr-system-thymeleaf/src/main/resources/templates/emp/pscntrc/pscntrcdist.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/pscntrc/pscntrcdist.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/emp/pscntrc/pscntrcdist.html
@@ -384,7 +384,15 @@
                             listItems : getComCombo(codeData[0], '선택')
                         }
                     })
-                    .addDate('stdDate', '기준일자', 'i18n', 130, false, {align: "center", sortable: true, required: true, resizable: true})
+                    .addDate('stdDate', '기준일자', 'i18n', 130, false, {
+                        align: "center",
+                        sortable: true,
+                        required: true,
+                        resizable: true,
+                        editable: function (grid, cell) {
+                            return this.getRow(cell.index).rowStatus === "C";
+                        }
+                    })
                     .addCheck('distYn', '배포', 'i18n', 50, true, {
                         editOptions: {
                             type: 'check',
```

### 4.4 `ustra-hr-system-thymeleaf/src/main/resources/templates/org/orareamng/orareamng250.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/orareamng/orareamng250.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/orareamng/orareamng250.html
@@ -237,13 +237,21 @@
 <script>
     // 행 추가
     $(document).on('click', '#btnAddRow', function(e) {
-        var lastRow = orareamng240Grid.getLastRow();
-        var id = '1';
-        if (lastRow != null) {
-            id = (parseInt(lastRow.id) + 1).toString();
+        const gridDatas = orareamng240Grid.getData();
+
+        //하나만 등록/삭제/수정이 가능하기 때문에 등록할 수 있는 row가 있는지 체크
+        if (!gridDatas.some(i => i.rowStatus === "C")) {
+            var lastRow = orareamng240Grid.getLastRow();
+            var id = '1';
+            if (lastRow != null) {
+                id = (parseInt(lastRow.id) + 1).toString();
+            }
+            orareamng240Grid.insertFocusRow({id: id, hdqtYn: 'N'});
+            $('#orareamng240Detail').clear();
+        } else {
+            //다른 row에 포커싱 되어있을 수 있기 때문에 새로운 row로 이동
+            orareamng240Grid.focus(gridDatas.find(i => i.rowStatus === "C").rowKey);
         }
-        orareamng240Grid.insertFocusRow({ id: id, hdqtYn: 'N'});
-        $('#orareamng240Detail').clear();
     });

     //근무지역 추가 또는 수정
```

### 4.5 `ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororghier/ororghier200.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororghier/ororghier200.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/org/ororghier/ororghier200.html
@@ -576,6 +576,29 @@
                     return false;
                 }

+                syncToastOrgLayoutWithRealGrid();
+
+            }
+
+            /**
+             * [수정일: 2026-09-07] Toast 전용 레이아웃 보정.
+             *
+             * RealGrid 는 우측 flex item 의 auto basis 를 제목(건수 포함)의 intrinsic width 로
+             * 계산한다. Toast 는 렌더 완료 폭을 다시 basis 로 사용해 좌측 조직도명이 200px로
+             * 줄고 우측만 남는 폭을 모두 차지했다. 우측 basis 를 같은 제목 실측값으로 고정하면
+             * 세 영역의 flex 배분이 RealGrid 와 같아진다. RealGrid 엔진에는 getToastGrid 가 없어
+             * 이 분기를 타지 않는다.
+             */
+            function syncToastOrgLayoutWithRealGrid() {
+                if (!unrelatedOrgGrid || typeof unrelatedOrgGrid.getToastGrid !== 'function') return;
+
+                const gridElement = document.getElementById('unrelatedOrgGrid');
+                const area = gridElement ? gridElement.closest('.column') : null;
+                const title = area ? area.querySelector('.table-title') : null;
+                if (!area || !title) return;
+
+                const titleWidth = title.getBoundingClientRect().width;
+                if (titleWidth > 0) area.style.flexBasis = `${titleWidth}px`;
             }

             function addRows(target, source, jsonRow, rowKey){
@@ -661,7 +684,9 @@

             function searchUnrelatedOrg(){
                 let params =  {'changeDate': orgMapGrid.getFocusData().changeDate};
-                unrelatedOrgGrid.load(apiUrl + '/unrelatedorg', params, function () {});
+                unrelatedOrgGrid.load(apiUrl + '/unrelatedorg', params, function () {
+                    syncToastOrgLayoutWithRealGrid();
+                });
             }

             $('#saveConn').on('click', function (e){
@@ -698,4 +723,4 @@
             })
         </script>
  </div>
-</html>
+</html>
```

### 4.6 `ustra-hr-system-thymeleaf/src/main/resources/templates/system/aprvtransfer.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/system/aprvtransfer.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/system/aprvtransfer.html
@@ -321,6 +321,7 @@
           }

           let param = transferGrid.toDataJson('dataList', deleteDataList);
+
           if (param) {
             $.post(apiUrl + "/delete", param, function (res) {
               if (res && res > 0) $.alert('삭제되었습니다');
@@ -328,8 +329,12 @@

               search();
             });
+          } else {
+              $.alert('삭제되었습니다');
+              search();
           }
         });
+
       }

       function clickBtnSave() {
```

### 4.7 `ustra-hr-system-thymeleaf/src/main/resources/templates/system/comng/comng100.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/system/comng/comng100.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/system/comng/comng100.html
@@ -511,14 +511,19 @@
             }

             $.confirm('선택한 행을 삭제하시겠습니까?', null, function (e) {
-                $.delete(apiUrl + '/grid1', focusData, function (response) {
-                    if (response && response > 0) {
-                        $.alert('삭제되었습니다.');
-                        search();
-                    } else {
-                        $.error("삭제 실패하였습니다.");
-                    }
-                });
+                if(focusData.rowStatus !== "C") {
+                    $.delete(apiUrl + '/grid1', focusData, function (response) {
+                        if (response && response > 0) {
+                            $.alert('삭제되었습니다.');
+                            search();
+                        } else {
+                            $.error("삭제 실패하였습니다.");
+                        }
+                    });
+                } else {
+                    $.alert('삭제되었습니다.');
+                    search();
+                }
             });
         }

```

### 4.8 `ustra-hr-system-thymeleaf/src/main/resources/templates/system/pgmbas.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/system/pgmbas.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/system/pgmbas.html
@@ -325,6 +325,23 @@
                 $('#pgmId').enabled(false);
                 $('#btnNewChild').enabled(_.isEmpty(data.uprPgmId));
             });
+
+            // Toast 전용: 정렬 보정으로 같은 표시 위치의 행이 다시 선택되면 상세도 그 행으로 맞춘다.
+            // RealGrid 자산/이벤트에는 손대지 않으며 getToastGrid가 있는 엔진에서만 실행한다.
+            if (typeof pgmGrid.getToastGrid === 'function') {
+                var toastPgmGrid = pgmGrid.getToastGrid();
+                var syncDetailAfterSort = function() {
+                    setTimeout(function() {
+                        var data = pgmGrid.getFocusData();
+                        if (_.isEmpty(data)) return;
+                        $('#pgmDetail').bindForm(data);
+                        $('#pgmId').enabled(false);
+                        $('#btnNewChild').enabled(_.isEmpty(data.uprPgmId));
+                    }, 0);
+                };
+                toastPgmGrid.on('afterSort', syncDetailAfterSort);
+                toastPgmGrid.on('afterUnsort', syncDetailAfterSort);
+            }
         }

         function search(selCell) {
```

### 4.9 `ustra-hr-system-thymeleaf/src/main/resources/templates/system/programs.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/system/programs.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/system/programs.html
@@ -245,7 +245,7 @@
             $.post('/system/programs/join', param, function(result){
                 if(result && result > 0){
                     $.alert('저장 되었습니다.', '알림', function(e){
-                        getMenusAll();
+                        getMenusAll(focusedCell);
                     });
                 }else{
                     $.error('저장에 실패하였습니다.');
@@ -262,27 +262,37 @@
         }

         $.confirm('체크된 '+ length +'개의 행을 삭제하시겠습니까?', '알림', function(e){
-            for (var i = 0; i < length; i++) {
-                programGrid.removeRow(programGrid.getCheckedRows()[0].rowKey);
+            const checkedRows = programGrid.getCheckedRows();
+
+            //DB에 저장된 데이터만 추출
+            const filteredRows = checkedRows.filter(e => e?.rowStatus !== "C");
+            const param = programGrid.toDataJson("pgmList", filteredRows);
+
+            const focusedCell = menuGrid.getFocusedCell();
+
+            //db에 삭제할 데이터가 있는 경우만 삭제
+            if(filteredRows[0]) {
+                $.delete('/system/programs/join', param, function(data){
+                    if(data.result){
+                        $.alert('삭제 되었습니다.', '알림', function(e){
+                            // programGrid.reload();
+                            getMenusAll(focusedCell);
+                        });
+                    }else{
+                        //$.error("권한에 등록된 프로그램이 있어 <font color='red'>삭제</font>할 수 없습니다.<br/>(등록된 권한 :"+data.roleId +" - "+data.roleNm+")", '알림', function(e) {
+                        $.alert("권한에 등록된 프로그램이 있어 <font color='red'>삭제</font>할 수 없습니다.<br/>(등록된 권한 :"+data.roleId +" - "+data.roleNm+")", '알림', function(e) {
+                            // programGrid.reload();
+                            getMenusAll(focusedCell);
+                        });
+                    }
+                });
+            } else {
+                //db에 삭제되는 데이터가 없으면 api 호출 할 필요 없음
+                $.alert('삭제 되었습니다.', '알림', function(e){
+                    getMenusAll(focusedCell);
+                });
             }

-            var param = programGrid.getModifiedDataJson('pgmList');
-            var focusedCell = menuGrid.getFocusedCell();
-
-            $.delete('/system/programs/join', param, function(data){
-                if(data.result){
-                    $.alert('삭제 되었습니다.', '알림', function(e){
-                        // programGrid.reload();
-                        getMenusAll();
-                    });
-                }else{
-                    //$.error("권한에 등록된 프로그램이 있어 <font color='red'>삭제</font>할 수 없습니다.<br/>(등록된 권한 :"+data.roleId +" - "+data.roleNm+")", '알림', function(e) {
-                    $.alert("권한에 등록된 프로그램이 있어 <font color='red'>삭제</font>할 수 없습니다.<br/>(등록된 권한 :"+data.roleId +" - "+data.roleNm+")", '알림', function(e) {
-                        // programGrid.reload();
-                        getMenusAll();
-                    });
-                }
-            });
         });
     });
 </script>
```

### 4.10 `ustra-hr-system-thymeleaf/src/main/resources/templates/wrk/wrkleav/wrkleavemng100.html`

```diff
--- a/ustra-hr-system-thymeleaf/src/main/resources/templates/wrk/wrkleav/wrkleavemng100.html
+++ b/ustra-hr-system-thymeleaf/src/main/resources/templates/wrk/wrkleav/wrkleavemng100.html
@@ -309,16 +309,21 @@
                     },
                     editable: true
                 })
-                .addNumber('lveCnt', '부여개수', 'i18n', 120, false, {
+                .addNumber('lveCnt', '부여개수', 'i18n', 120, false, Object.assign({
                     required: true,
                     align: 'center',
                     numberFormat: "#,##0.##"
-                })
-                .addNumber('useCnt', '사용개수', 'i18n', 120, false, {
+                }, typeof UstraRealGrid.prototype.getToastGrid === "function" ? {
+                    // 화면은 0으로 유지하고 Excel에서만 RealGrid처럼 0.0(최대 두 자리)로 표시한다.
+                    excelFormat: "#,##0.0#"
+                } : {}))
+                .addNumber('useCnt', '사용개수', 'i18n', 120, false, Object.assign({
                     required: true,
                     align: 'center',
                     numberFormat: "#,##0.##",
-                })
+                }, typeof UstraRealGrid.prototype.getToastGrid === "function" ? {
+                    excelFormat: "#,##0.0#"
+                } : {}))
                 .add('remark', '비고', 'i18n', 300, false, {
                     align: 'left'
                 })
```

## 5. 제외한 변경

다음 유형은 2차 브랜치에 존재하지만 Toast 작업으로 분류하지 않아 제외했다.

- 계약서 배포의 일반 editable 요구사항
- 문서양식·공지·메일·권한그룹 등 일반 화면 업무 수정
- Toast API나 엔진 분기를 사용하지 않는 메뉴별 프로그램 변경
- 단순 로그 제거, 문구·스타일 변경

## 6. 참고

- `docs/toast-grid-migration/PROGRESS.md`
- `docs/toast-grid-migration/TOAST_GRID_ADAPTER_ASIS_TOBE.md`
- `ustra-hr-system-thymeleaf/src/main/resources/static/assets/js/ustra-utils/ustra.grid.compat.js`

