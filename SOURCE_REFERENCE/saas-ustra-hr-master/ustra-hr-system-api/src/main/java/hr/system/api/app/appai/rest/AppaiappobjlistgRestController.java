package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.dto.AppaiappryearendDto;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import hr.system.api.app.appai.service.AppaiappobjlistgService;
import hr.system.api.app.appai.service.AppaiappryearendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappobjlistg")
@RequiredArgsConstructor
public class AppaiappobjlistgRestController {

    private final AppaiappobjlistgService appaiappobjlistgService;

    private final AppaiapapprtargetService appaiapapprtargetService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 목표설정 대상자관리 조회
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppraisementTargetList(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraisementTargetList(appaiapapprindDto);
    }

    /**
     * 목표설정 대상자관리 저장/추가/삭제
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping
    public int saveAppaiapapprindlist(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.saveAppaiapapprindlist(appaiapapprindDto.getAppaiapapprindList());
    }

    /**
     * 목표설정 대상자관리 상태 수정 (대상자 확정)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/confirmtarget")
    public int updateConfirmTarget(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CONFM");
    }

    /**
     * 목표설정 대상자관리 상태 수정 (대상자 확정 취소)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/canceltarget")
    public int updateCancelTarget(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CONFM_CANCEL");
    }

    /**
     * 목표설정 대상자관리 상태 수정 (담당 확인)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/confirm")
    public int updateConfirm(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CONFM_SEND");
    }

    /**
     * 목표설정 대상자관리 상태 수정 (담당 확인 취소)
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/confirmcancel")
    public int updateConfirmCancel(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CONFM_SEND_CANCEL");
    }

}

