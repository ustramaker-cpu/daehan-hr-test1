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
@RequestMapping("/app/appai_/appaiapprogressg")
@RequiredArgsConstructor
public class AppaiapprogressgRestController {

    private final AppaiapapprtargetService appaiapapprtargetService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 목표설정 평가진행관리(담당자) 조회
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappprogressgList(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraisementTargetList(appaiapapprindDto);
    }


    /**
     * 목표설정 진행관리(담당자) 상태 수정 - 오픈/오픈취소/최종마감/최종마감취소
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/indTarget")
    public int updateApprindFlag(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateApapprindInd(appaiapapprindDto.getAppaiapapprindList());
    }

//    /**
//     * 목표설정 평가진행현황관리(담당자) 상태 수정 (1차평가전송)
//     * @param appaiapapprindDto
//     * @return int
//     */
//    @PostMapping("/execindsend")
//    public int updateExecIndSend(AppaiapapprindDto appaiapapprindDto) {
//        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_EXEC_IND");
//    }
//
//    /**
//     * 목표설정 평가진행현황관리(담당자) 상태 수정 (1차평가취소)
//     * @param appaiapapprindDto
//     * @return int
//     */
//    @PostMapping("/execindcancel")
//    public int updateExecIndCancel(AppaiapapprindDto appaiapapprindDto) {
//        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_EXEC_IND_CANCEL");
//    }
}

