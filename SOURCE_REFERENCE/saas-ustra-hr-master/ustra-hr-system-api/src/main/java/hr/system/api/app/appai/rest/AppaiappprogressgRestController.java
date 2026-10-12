package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import hr.system.api.app.appai.service.AppaiappprogressgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappprogressg")
@RequiredArgsConstructor
public class AppaiappprogressgRestController {

    private final AppaiappprogressgService appaiappprogressgService;

    private final AppaiapapprtargetService appaiapapprtargetService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

//    /**
//     * 목표설정 평가진행현황관리(담당자) 조회
//     * @param appaiappprogressgDto
//     * @return List<CamelCaseMap>
//     */
//    @GetMapping
//    public List<CamelCaseMap> findAppaiappprogressgList(AppaiappprogressgDto appaiappprogressgDto) {
//        return appaiappprogressgService.findAppaiappprogressgList(appaiappprogressgDto);
//    }

    /**
     * 목표설정 평가진행현황관리(담당자) 조회
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappprogressgList(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraisementTargetList(appaiapapprindDto);
    }

    /**
     * 목표설정 평가진행현황관리(담당자) 상태 수정 (오픈)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/open")
    public int updateOpen(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "OPEN");
    }

    /**
     * 목표설정 평가진행현황관리(담당자) 상태 수정 (오픈 취소)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/opencancel")
    public int updateOpenCancel(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "OPEN_CANCEL");
    }

    /**
     * 목표설정 평가진행현황관리(담당자) 상태 수정 (평가마감)
     * @param appaiappprogressgDto
     * @return int
     */
    @PostMapping("/close")
    public int updateClose(AppaiappprogressgDto appaiappprogressgDto) {
        return appaiappprogressgService.updateStatusInd(appaiappprogressgDto.getAppaiappprogressgList(), "CLOSE");
    }

    /**
     * 목표설정 평가진행현황관리(담당자) 상태 수정 (평가마감 취소)
     * @param appaiappprogressgDto
     * @return int
     */
    @PostMapping("/closecancel")
    public int updateCloseCancel(AppaiappprogressgDto appaiappprogressgDto) {
        return appaiappprogressgService.updateStatusInd(appaiappprogressgDto.getAppaiappprogressgList(), "CLOSE_CANCEL");
    }

    /**
     * 목표설정 평가진행현황관리(담당자) 상태 수정 (최종마감)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/closelast")
    public int updateCloseLast(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_LAST");
    }

    /**
     * 목표설정 평가진행현황관리(담당자) 상태 수정 (최종마감 취소)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/closelastcancel")
    public int updateCloseLastCancel(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_LAST_CANCEL");
    }

    /**
     * 목표설정 평가진행현황관리(담당자) 상태 수정 (1차평가전송)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/execindsend")
    public int updateExecIndSend(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_EXEC_IND");
    }

    /**
     * 목표설정 평가진행현황관리(담당자) 상태 수정 (1차평가취소)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/execindcancel")
    public int updateExecIndCancel(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_EXEC_IND_CANCEL");
    }
}

