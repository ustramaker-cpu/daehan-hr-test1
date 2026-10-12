package hr.system.api.app.appai_.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import hr.system.api.app.appai.service.AppaiappobjlistpService;
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
@RequestMapping("/app/appai_/appaiapobjlistp")
@RequiredArgsConstructor
public class AppaiapobjlistpRestController {

    private final AppaiappobjlistpService appaiappobjlistpService;
    private final AppaiapapprtargetService appaiapapprtargetService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 중간점검 대상자관리 조회
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findappaiappobjlistpList(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraisementTargetList(appaiapapprindDto);
    }

    /**
     * 중간점검 대상자관리 저장/추가/삭제
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping
    public int saveappaiappobjlistp(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.saveAppaiapapprindlist(appaiapapprindDto.getAppaiapapprindList());
    }

    /**
     * 목표설정 대상자관리 상태 수정
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/indTarget")
    public int updateApprindFlag(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateApapprindInd(appaiapapprindDto.getAppaiapapprindList());
    }

//    /**
//     * 목표설정 대상자관리 상태 수정 (대상자 확정)
//     * @param appaiapapprindDto
//     * @return int
//     */
//    @PostMapping("/confirmtarget")
////    public int updateConfirmTarget(AppaiappobjlistpDto appaiappobjlistpDto) {
////        return appaiappobjlistpService.updateConfirm(appaiappobjlistpDto.getAppaiappobjlistpList(), "CONFM");
//    public int updateConfirmtTarget(AppaiapapprindDto appaiapapprindDto) {
//        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CONFM");
//    }
//
//    /**
//     * 목표설정 대상자관리 상태 수정 (대상자 확정 취소)
//     * @param appaiapapprindDto
//     * @return int
//     */
//    @PostMapping("/canceltarget")
////    public int updateCancelTarget(AppaiappobjlistpDto appaiappobjlistpDto) {
////        return appaiappobjlistpService.updateConfirm(appaiappobjlistpDto.getAppaiappobjlistpList(), "CONFM_CANCEL");
//    public int updateCancelTarget(AppaiapapprindDto appaiapapprindDto) {
//            return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CONFM_CANCEL");
//    }

    /** 호출x */
//    /**
//     * 목표설정 대상자관리 상태 수정 (담당 확인)
//     * @param appaiappobjlistpDto
//     * @return int
//     */
//    @PostMapping("/confirm")
//    public int updateConfirm(AppaiappobjlistpDto appaiappobjlistpDto) {
//        return appaiappobjlistpService.updateConfirm(appaiappobjlistpDto.getAppaiappobjlistpList(), "CONFM_SEND");
//    }
//
//    /**
//     * 목표설정 대상자관리 상태 수정 (담당 확인 취소)
//     * @param appaiappobjlistpDto
//     * @return int
//     */
//    @PostMapping("/confirmcancel")
//    public int updateConfirmCancel(AppaiappobjlistpDto appaiappobjlistpDto) {
//        return appaiappobjlistpService.updateConfirm(appaiappobjlistpDto.getAppaiappobjlistpList(), "CONFM_SEND_CANCEL");
//    }

}

