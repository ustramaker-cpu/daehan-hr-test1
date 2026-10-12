package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.dto.AppaiappprogressmDto;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import hr.system.api.app.appai.service.AppaiappprogressmService;
import hr.system.api.app.appai.service.AppaiappprogresspService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappprogressm")
@RequiredArgsConstructor
public class AppaiappprogressmRestController {

    private final AppaiappprogressmService appaiappprogressmService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 다면평가 평가진행현황관리(담당자) 조회
     * @param appaiappprogressmDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappprogressmList(AppaiappprogressmDto appaiappprogressmDto) {
        return appaiappprogressmService.findAppaiappprogressmList(appaiappprogressmDto);
    }

    /**
     * 다면평가 평가진행현황관리(담당자) 상태 수정 (오픈)
     * @param appaiappprogressmDto
     * @return int
     */
    @PostMapping("/open")
    public int updateOpen(AppaiappprogressmDto appaiappprogressmDto) {
        return appaiappprogressmService.updateStatusInd(appaiappprogressmDto.getAppaiappprogressmList(), "OPEN");
    }

    /**
     * 다면평가 평가진행현황관리(담당자) 상태 수정 (오픈 취소)
     * @param appaiappprogressmDto
     * @return int
     */
    @PostMapping("/opencancel")
    public int updateOpenCancel(AppaiappprogressmDto appaiappprogressmDto) {
        return appaiappprogressmService.updateStatusInd(appaiappprogressmDto.getAppaiappprogressmList(), "OPEN_CANCEL");
    }

    /**
     * 다면평가 평가진행현황관리(담당자) 상태 수정 (평가마감)
     * @param appaiappprogressmDto
     * @return int
     */
    @PostMapping("/close")
    public int updateClose(AppaiappprogressmDto appaiappprogressmDto) {
        return appaiappprogressmService.updateStatusInd(appaiappprogressmDto.getAppaiappprogressmList(), "CLOSE");
    }

    /**
     * 다면평가 평가진행현황관리(담당자) 상태 수정 (평가마감 취소)
     * @param appaiappprogressmDto
     * @return int
     */
    @PostMapping("/closecancel")
    public int updateCloseCancel(AppaiappprogressmDto appaiappprogressmDto) {
        return appaiappprogressmService.updateStatusInd(appaiappprogressmDto.getAppaiappprogressmList(), "CLOSE_CANCEL");
    }

    /**
     * 다면평가 평가진행현황관리(담당자) 상태 수정 (최종마감)
     * @param appaiappprogressmDto
     * @return int
     */
    @PostMapping("/closelast")
    public int updateCloseLast(AppaiappprogressmDto appaiappprogressmDto) {
        return appaiappprogressmService.updateStatusInd(appaiappprogressmDto.getAppaiappprogressmList(), "CLOSE_LAST");
    }

    /**
     * 다면평가 평가진행현황관리(담당자) 상태 수정 (최종마감 취소)
     * @param appaiappprogressmDto
     * @return int
     */
    @PostMapping("/closelastcancel")
    public int updateCloseLastCancel(AppaiappprogressmDto appaiappprogressmDto) {
        return appaiappprogressmService.updateStatusInd(appaiappprogressmDto.getAppaiappprogressmList(), "CLOSE_LAST_CANCEL");
    }

}

