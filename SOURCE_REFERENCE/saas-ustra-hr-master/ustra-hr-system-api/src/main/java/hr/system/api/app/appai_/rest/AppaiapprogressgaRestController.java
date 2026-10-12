package hr.system.api.app.appai_.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapappraiserDto;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.service.AppaiapappraiserService;
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
@RequestMapping("/app/appai_/appaiapprogressga")
@RequiredArgsConstructor
public class AppaiapprogressgaRestController {

    private final AppaiapapprtargetService appaiapapprtargetService;
    private final AppaiapappraiserService appaiapappraiserService;

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
     * 목표설정 진행관리(평가자) 상태 수정  - 목표승인/목표승인취소
     * @param appaiapappraiserDto
     * @return int
     */
    @PostMapping("/indAppraiser")
    public int updateApappraiserInd(AppaiapappraiserDto appaiapappraiserDto) {
        return appaiapappraiserService.updateApappraiserInd(appaiapappraiserDto.getAppaiapappraiserList());
    }

}

