package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappobjlistyDto;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import hr.system.api.app.appai.mapper.AppaiapapprtargetMapper;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import hr.system.api.app.appai.service.AppaiappprogressyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappprogressy")
@RequiredArgsConstructor
public class AppaiappprogressyRestController {

    private final AppaiappprogressyService appaiappprogressyService;
    private final AppaiapapprtargetService appaiapapprtargetService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 연말평가 평가진행현황관리(담당자) 조회
     * @param appaiappobjlistyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappprogressyList(AppaiapapprindDto appaiapapprindDto) {
        //return appaiappprogressyService.findAppaiappprogressyList(appaiappobjlistyDto);
        return appaiapapprtargetService.findAppraisementTargetList(appaiapapprindDto);
    }

    /**
     * 연말평가 평가진행현황관리(담당자) 상태 수정 (오픈)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/open")
    public int updateOpen(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "OPEN");
    }

    /**
     * 연말평가 평가진행현황관리(담당자) 상태 수정 (오픈 취소)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/opencancel")
    public int updateOpenCancel(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "OPEN_CANCEL");
    }

    @PostMapping("/grouping")
    public int updateGrouping(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "GROUPING");
    }


    /**
     * 연말평가 평가진행현황관리(담당자) 상태 수정 (평가마감)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/close")
    public int updateClose(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "CLOSE");
    }

    /**
     * 연말평가 평가진행현황관리(담당자) 상태 수정 (평가마감 취소)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/closecancel")
    public int updateCloseCancel(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "CLOSE_CANCEL");
    }

    /**
     * 연말평가 평가진행현황관리(담당자) 상태 수정 (최종마감)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/closelast")
    public int updateCloseLast(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "CLOSE_LAST");
    }

    /**
     * 연말평가 평가진행현황관리(담당자) 상태 수정 (최종마감 취소)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/closelastcancel")
    public int updateCloseLastCancel(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "CLOSE_LAST_CANCEL");
    }

    /**
     * 연말평가진행관리(담당자) 최종마감&마감취소
     * @param appaiappprogressyDto
     * @return
     */
    @PostMapping("/closeIndYn")
    public int closeIndYn(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyService.closeIndYn(appaiappprogressyDto);
    }

}

