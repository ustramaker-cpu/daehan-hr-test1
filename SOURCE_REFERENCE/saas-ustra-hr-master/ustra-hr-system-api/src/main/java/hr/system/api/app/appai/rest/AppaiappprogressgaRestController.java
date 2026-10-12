package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import hr.system.api.app.appai.service.AppaiappprogressgaService;
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
@RequestMapping("/app/appai/appaiappprogressga")
@RequiredArgsConstructor
public class AppaiappprogressgaRestController {

    private final AppaiappprogressgaService appaiappprogressgaService;

    private final AppaiapapprtargetService appaiapapprtargetService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 목표설정 평가진행현황관리(평가자) 조회
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappprogressgList(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraisementTargetList(appaiapapprindDto);
    }

    /**
     * 목표설정 평가진행현황관리(평가자) 상태 수정 (목표승인)
     * @param appaiappprogressgDto
     * @return int
     */
    @PostMapping("/approve")
    public int updateApprove(AppaiappprogressgDto appaiappprogressgDto) {
        return appaiappprogressgaService.updateStatusInd(appaiappprogressgDto.getAppaiappprogressgList(), "APPROVE");
    }

    /**
     * 목표설정 평가진행현황관리(평가자) 상태 수정 (목표승인 취소)
     * @param appaiappprogressgDto
     * @return int
     */
    @PostMapping("/approvecancel")
    public int updateApproveCancel(AppaiappprogressgDto appaiappprogressgDto) {
        return appaiappprogressgaService.updateStatusInd(appaiappprogressgDto.getAppaiappprogressgList(), "APPROVE_CANCEL");
    }

    /**
     * 목표설정 평가진행현황관리(평가자) 평가자 R 업데이트 [2023.07.31삭제]
     * @param appaiappprogressgDto
     * @return int
     */
    @PostMapping("/reading")
    public int updateReading(AppaiappprogressgDto appaiappprogressgDto) {
        return appaiappprogressgaService.updateStatus(appaiappprogressgDto, "READING");
    }

}

