package hr.system.api.app.appai.rest;

import java.util.List;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsitm.framework.core.type.CamelCaseMap;

import hr.system.api.app.appai.dto.AppaiappobjlistsDto;
import hr.system.api.app.appai.service.AppaiappobjlistsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappobjlists")
@RequiredArgsConstructor
public class AppaiappobjlistsRestController {

    private final AppaiappobjlistsService appaiappobjlistsService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 자기신고서 대상자관리 조회
     * @param AppaiappobjlistsDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappobjlistsList(AppaiappobjlistsDto AppaiappobjlistsDto) {
        return appaiappobjlistsService.findAppaiappobjlistsList(AppaiappobjlistsDto);
    }

    /**
     * 자기신고서 대상자관리 저장/추가/삭제
     * @param AppaiappobjlistsDto
     * @return int
     */
    @PostMapping
    public int saveAppaiappobjlists(AppaiappobjlistsDto AppaiappobjlistsDto) {
        return appaiappobjlistsService.saveAppaiappobjlists(AppaiappobjlistsDto.getAppaiappobjlistsList());
    }

    /**
     * 자기신고서 대상자관리 상태 수정 (대상자 확정)
     * @param AppaiappobjlistsDto
     * @return int
     */
    @PostMapping("/confirmtarget")
    public int updateConfirmTarget(AppaiappobjlistsDto AppaiappobjlistsDto) {
        return appaiappobjlistsService.updateConfirm(AppaiappobjlistsDto.getAppaiappobjlistsList(), "CONFM");
    }

    /**
     * 자기신고서 대상자관리 상태 수정 (대상자 확정 취소)
     * @param AppaiappobjlistsDto
     * @return int
     */
    @PostMapping("/canceltarget")
    public int updateCancelTarget(AppaiappobjlistsDto AppaiappobjlistsDto) {
        return appaiappobjlistsService.updateConfirm(AppaiappobjlistsDto.getAppaiappobjlistsList(), "CONFM_CANCEL");
    }

    /**
     * 자기신고서 대상자관리 상태 수정 (담당 확인)
     * @param AppaiappobjlistsDto
     * @return int
     */
    @PostMapping("/confirm")
    public int updateConfirm(AppaiappobjlistsDto AppaiappobjlistsDto) {
        return appaiappobjlistsService.updateConfirm(AppaiappobjlistsDto.getAppaiappobjlistsList(), "CONFM_SEND");
    }

    /**
     * 자기신고서 대상자관리 상태 수정 (담당 확인 취소)
     * @param AppaiappobjlistsDto
     * @return int
     */
    @PostMapping("/confirmcancel")
    public int updateConfirmCancel(AppaiappobjlistsDto AppaiappobjlistsDto) {
        return appaiappobjlistsService.updateConfirm(AppaiappobjlistsDto.getAppaiappobjlistsList(), "CONFM_SEND_CANCEL");
    }

}

