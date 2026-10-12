package hr.system.api.app.appai.rest;

import java.util.List;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsitm.framework.core.type.CamelCaseMap;

import hr.system.api.app.appai.dto.AppaiappprogresssDto;
import hr.system.api.app.appai.service.AppaiappprogresssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappprogresss")
@RequiredArgsConstructor
public class AppaiappprogresssRestController {

    private final AppaiappprogresssService appaiappprogresssService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 자기신고서 평가진행현황관리(담당자) 조회
     * @param appaiappprogresssDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappprogresssList(AppaiappprogresssDto appaiappprogresssDto) {
        return appaiappprogresssService.findAppaiappprogresssList(appaiappprogresssDto);
    }

    /**
     * 자기신고서 평가진행현황관리(담당자) 상태 수정 (오픈)
     * @param appaiappprogresssDto
     * @return int
     */
    @PostMapping("/open")
    public int updateOpen(AppaiappprogresssDto appaiappprogresssDto) {
        return appaiappprogresssService.updateStatusInd(appaiappprogresssDto.getAppaiappprogresssList(), "OPEN");
    }

    /**
     * 자기신고서 평가진행현황관리(담당자) 상태 수정 (오픈 취소)
     * @param appaiappprogresssDto
     * @return int
     */
    @PostMapping("/opencancel")
    public int updateOpenCancel(AppaiappprogresssDto appaiappprogresssDto) {
        return appaiappprogresssService.updateStatusInd(appaiappprogresssDto.getAppaiappprogresssList(), "OPEN_CANCEL");
    }

    /**
     * 자기신고서 평가진행현황관리(담당자) 상태 수정 (평가마감)
     * @param appaiappprogresssDto
     * @return int
     */
    @PostMapping("/close")
    public int updateClose(AppaiappprogresssDto appaiappprogresssDto) {
        return appaiappprogresssService.updateStatusInd(appaiappprogresssDto.getAppaiappprogresssList(), "CLOSE");
    }

    /**
     * 자기신고서 평가진행현황관리(담당자) 상태 수정 (평가마감 취소)
     * @param appaiappprogresssDto
     * @return int
     */
    @PostMapping("/closecancel")
    public int updateCloseCancel(AppaiappprogresssDto appaiappprogresssDto) {
        return appaiappprogresssService.updateStatusInd(appaiappprogresssDto.getAppaiappprogresssList(), "CLOSE_CANCEL");
    }

    /**
     * 자기신고서 평가진행현황관리(담당자) 상태 수정 (최종마감)
     * @param appaiappprogresssDto
     * @return int
     */
    @PostMapping("/closelast")
    public int updateCloseLast(AppaiappprogresssDto appaiappprogresssDto) {
        return appaiappprogresssService.updateStatusInd(appaiappprogresssDto.getAppaiappprogresssList(), "CLOSE_LAST");
    }

    /**
     * 자기신고서 평가진행현황관리(담당자) 상태 수정 (최종마감 취소)
     * @param appaiappprogresssDto
     * @return int
     */
    @PostMapping("/closelastcancel")
    public int updateCloseLastCancel(AppaiappprogresssDto appaiappprogresssDto) {
        return appaiappprogresssService.updateStatusInd(appaiappprogresssDto.getAppaiappprogresssList(), "CLOSE_LAST_CANCEL");
    }

}

