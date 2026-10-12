package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappobjlistyDto;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import hr.system.api.app.appai.service.AppaiappobjlistyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappobjlisty")
@RequiredArgsConstructor
public class AppaiappobjlistyRestController {

    private final AppaiappobjlistyService appaiappobjlistyService;
    private final AppaiapapprtargetService appaiapapprtargetService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 연말평가 대상자관리 조회
     * @param appaiappobjlistyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findappaiappobjlistyList(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraisementTargetList(appaiapapprindDto);
    }

    /**
     * 연말평가 대상자관리 저장/추가/삭제
     * @param appaiappobjlistyDto
     * @return int
     */
    @PostMapping
    public int saveappaiappobjlisty(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappobjlistyService.saveappaiappobjlisty(appaiappobjlistyDto.getAppaiappobjlistyList());
    }

    /**
     * 연말평가 대상자관리 상태 수정 (대상자 확정)
     * @param appaiappobjlistyDto
     * @return int
     */
    @PostMapping("/confirmtarget")
    public int updateConfirmTarget(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappobjlistyService.updateConfirm(appaiappobjlistyDto.getAppaiappobjlistyList(), "CONFM");
    }

    /**
     * 연말평가 대상자관리 상태 수정 (대상자 확정 취소)
     * @param appaiappobjlistyDto
     * @return int
     */
    @PostMapping("/canceltarget")
    public int updateCancelTarget(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappobjlistyService.updateConfirm(appaiappobjlistyDto.getAppaiappobjlistyList(), "CONFM_CANCEL");
    }

    /**
     * 연말평가 대상자관리 상태 수정 (담당 확인)
     * @param appaiappobjlistyDto
     * @return int
     */
    @PostMapping("/confirm")
    public int updateConfirm(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappobjlistyService.updateConfirm(appaiappobjlistyDto.getAppaiappobjlistyList(), "CONFM_SEND");
    }

    /**
     * 연말평가 대상자관리 상태 수정 (담당 확인 취소)
     * @param appaiappobjlistyDto
     * @return int
     */
    @PostMapping("/confirmcancel")
    public int updateConfirmCancel(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappobjlistyService.updateConfirm(appaiappobjlistyDto.getAppaiappobjlistyList(), "CONFM_SEND_CANCEL");
    }

}

