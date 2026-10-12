package hr.system.api.app.appai_.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapappraiserDto;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.service.AppaiapappraiserService;
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
@RequestMapping("/app/appai_/appaiapprogresspa")
@RequiredArgsConstructor
public class AppaiapprogresspaRestController {

    private final AppaiapapprtargetService appaiapapprtargetService;
    private final AppaiapappraiserService appaiapappraiserService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 중간점검 평가진행현황관리(관리자) 조회
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappprogresspaList(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraisementTargetList(appaiapapprindDto);
    }

    /**
     * 중간점검 작성 여부 조회
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/findAppraise")
    public List<CamelCaseMap> findAppraiseInd(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraiseInd(appaiapapprindDto);
    }

    /**
     * 중간점검 진행관리(평가자) 상태 수정  - 전송/전송취소
     * @param appaiapappraiserDto
     * @return int
     */
    @PostMapping("/indAppraiser")
    public int updateApappraiserInd(AppaiapappraiserDto appaiapappraiserDto) {
        return appaiapappraiserService.updateApappraiserInd(appaiapappraiserDto.getAppaiapappraiserList());
    }

}

