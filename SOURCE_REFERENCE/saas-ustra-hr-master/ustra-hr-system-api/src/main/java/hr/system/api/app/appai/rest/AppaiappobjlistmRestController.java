package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.dto.AppaiappobjlistmDto;
import hr.system.api.app.appai.service.AppaiappobjlistgService;
import hr.system.api.app.appai.service.AppaiappobjlistmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappobjlistm")
@RequiredArgsConstructor
public class AppaiappobjlistmRestController {

    private final AppaiappobjlistmService appaiappobjlistmService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 다면평가 평가대상자 조회
     * @param appaiappobjlistmDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/obj")
    public List<CamelCaseMap> findAppaiappobjlistmObjList(AppaiappobjlistmDto appaiappobjlistmDto) {
        return appaiappobjlistmService.findAppaiappobjlistmObjList(appaiappobjlistmDto);
    }

    /**
     * 다면평가 평가자 조회
     * @param appaiappobjlistmDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/ser")
    public List<CamelCaseMap> findAppaiappobjlistmSerList(AppaiappobjlistmDto appaiappobjlistmDto) {
        return appaiappobjlistmService.findAppaiappobjlistmSerList(appaiappobjlistmDto);
    }

    /**
     * 다면평가 평가대상자 저장/추가/삭제
     * @param appaiappobjlistmDto
     * @return int
     */
    @PostMapping("/obj")
    public int saveApapprobj(AppaiappobjlistmDto appaiappobjlistmDto) {
        return appaiappobjlistmService.saveApapprobj(appaiappobjlistmDto.getAppaiappobjlistmList());
    }

    /**
     * 다면평가 평가자 저장/추가/삭제
     * @param appaiappobjlistmDto
     * @return int
     */
    @PostMapping("/ser")
    public int saveApapprserm(AppaiappobjlistmDto appaiappobjlistmDto) {
        return appaiappobjlistmService.saveApapprserm(appaiappobjlistmDto.getAppaiappobjlistmList());
    }

    /**
     * 다면평가 평가대상자 상태 수정 (대상자 확정)
     * @param appaiappobjlistmDto
     * @return int
     */
    @PostMapping("/confirmtarget")
    public int updateConfirmTarget(AppaiappobjlistmDto appaiappobjlistmDto) {
        return appaiappobjlistmService.updateConfirm(appaiappobjlistmDto.getAppaiappobjlistmList(), "CONFM");
    }

    /**
     * 다면평가 평가대상자 상태 수정 (대상자 확정 취소)
     * @param appaiappobjlistmDto
     * @return int
     */
    @PostMapping("/canceltarget")
    public int updateCancelTarget(AppaiappobjlistmDto appaiappobjlistmDto) {
        return appaiappobjlistmService.updateConfirm(appaiappobjlistmDto.getAppaiappobjlistmList(), "CONFM_CANCEL");
    }

    /**
     * 다면평가 평가대상자 상태 수정 (담당 확인)
     * @param appaiappobjlistmDto
     * @return int
     */
    @PostMapping("/confirm")
    public int updateConfirm(AppaiappobjlistmDto appaiappobjlistmDto) {
        return appaiappobjlistmService.updateConfirm(appaiappobjlistmDto.getAppaiappobjlistmList(), "CONFM_SEND");
    }

    /**
     * 다면평가 평가대상자 상태 수정 (담당 확인 취소)
     * @param appaiappobjlistmDto
     * @return int
     */
    @PostMapping("/confirmcancel")
    public int updateConfirmCancel(AppaiappobjlistmDto appaiappobjlistmDto) {
        return appaiappobjlistmService.updateConfirm(appaiappobjlistmDto.getAppaiappobjlistmList(), "CONFM_SEND_CANCEL");
    }

}

