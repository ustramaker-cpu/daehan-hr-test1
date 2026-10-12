package hr.system.api.app.appai_.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai_/appaiapprogressp")
@RequiredArgsConstructor
public class AppaiapprogresspRestController {

    private final AppaiapapprtargetService appaiapapprtargetService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 중간점검 평가진행관리(담당자) 조회
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappprogresspList(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraisementTargetList(appaiapapprindDto);
    }

    /**
     * 중간점검 진행관리(담당자) 상태 수정 - 오픈/오픈취소/최종마감/최종마감취소
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/indTarget")
    public int updateApprindFlag(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateApapprindInd(appaiapapprindDto.getAppaiapapprindList());
    }

//    /**
//     * 중간점검 평가진행현황관리(담당자) 상태 수정 (오픈)
//     * @param appaiapapprindDto
//     * @return int
//     */
//    @PostMapping("/open")
////    public int updateOpen(AppaiappprogresspDto appaiappprogresspDto) {
////        return appaiappprogresspService.updateStatusInd(appaiappprogresspDto.getAppaiappprogresspList(), "OPEN");
//    public int updateOpen(AppaiapapprindDto appaiapapprindDto) {
//        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "OPEN");
//    }
//
//    /**
//     * 중간점검 평가진행현황관리(담당자) 상태 수정 (오픈 취소)
//     * @param appaiapapprindDto
//     * @return int
//     */
//    @PostMapping("/opencancel")
////    public int updateOpenCancel(AppaiappprogresspDto appaiappprogresspDto) {
////        return appaiappprogresspService.updateStatusInd(appaiappprogresspDto.getAppaiappprogresspList(), "OPEN_CANCEL");
//    public int updateOpenCancel(AppaiapapprindDto appaiapapprindDto) {
//        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "OPEN_CANCEL");
//    }
//
//    /**
//     * 중간점검 평가진행현황관리(담당자) 상태 수정 (평가마감) *사용안함
//     * @param appaiappprogresspDto
//     * @return int
//     */
//    @PostMapping("/close")
//    public int updateClose(AppaiappprogresspDto appaiappprogresspDto) {
//        return appaiappprogresspService.updateStatusInd(appaiappprogresspDto.getAppaiappprogresspList(), "CLOSE");
//    }
//
//    /**
//     * 중간점검 평가진행현황관리(담당자) 상태 수정 (평가마감 취소) *사용안함
//     * @param appaiappprogresspDto
//     * @return int
//     */
//    @PostMapping("/closecancel")
//    public int updateCloseCancel(AppaiappprogresspDto appaiappprogresspDto) {
//        return appaiappprogresspService.updateStatusInd(appaiappprogresspDto.getAppaiappprogresspList(), "CLOSE_CANCEL");
//    }
//
//    /**
//     * 중간점검 평가진행현황관리(담당자) 상태 수정 (최종마감)
//     * @param appaiapapprindDto
//     * @return int
//     */
//    @PostMapping("/closelast")
////    public int updateCloseLast(AppaiappprogresspDto appaiappprogresspDto) {
////        return appaiappprogresspService.updateStatusInd(appaiappprogresspDto.getAppaiappprogresspList(), "CLOSE_LAST");
//    public int updateCloseLast(AppaiapapprindDto appaiapapprindDto) {
//        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_LAST");
//    }
//
//    /**
//     * 중간점검 평가진행현황관리(담당자) 상태 수정 (최종마감 취소)
//     * @param appaiapapprindDto
//     * @return int
//     */
//    @PostMapping("/closelastcancel")
////    public int updateCloseLastCancel(AppaiappprogresspDto appaiappprogresspDto) {
////        return appaiappprogresspService.updateStatusInd(appaiappprogresspDto.getAppaiappprogresspList(), "CLOSE_LAST_CANCEL");
//    public int updateCloseLastCancel(AppaiapapprindDto appaiapapprindDto) {
//        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_LAST_CANCEL");
//    }
}

