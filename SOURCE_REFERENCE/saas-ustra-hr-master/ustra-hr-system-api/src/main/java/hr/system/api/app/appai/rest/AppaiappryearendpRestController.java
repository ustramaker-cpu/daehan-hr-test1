package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.*;
import hr.system.api.app.appai.service.AppaiapapprdetailService;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import hr.system.api.app.appai.service.AppaiappryearendService;
import hr.system.api.app.appai.service.AppaiappryearendpService;
import hr.system.api.app.appmng.dto.AppmngapgradeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappryearendp")
@RequiredArgsConstructor
public class AppaiappryearendpRestController {

    private final AppaiappryearendpService appaiappryearendpService;

    private final AppaiappryearendService appaiappryearendService;

    private final AppaiapapprtargetService appaiapapprtargetService;
    private final AppaiapapprdetailService appaiapapprdetailService;

    /**
     * 중간점검(담당자) 조회
     * @param appaiapapprcapaDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappryearendpList(AppaiapapprcapaDto appaiapapprcapaDto) {
        return appaiapapprdetailService.findAppaiapprdetailList(appaiapapprcapaDto);
    }

    /**
     * 중간점검(담당자) 오픈여부
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/checkInd")
    public List<CamelCaseMap> checkInd(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.checkApprInd(appaiapapprindDto);
    }

    /**
     * 등급코드 조회 (공통)
     * */
    @GetMapping("/getgrade")
    public List<CamelCaseMap> getGrade(AppmngapgradeDto appmngapgradeDto) {
        return appaiappryearendService.findGradeList(appmngapgradeDto);
    }

    /**
     * 중간점검(담당자) 평가자 조회
     * @param appaiappraiserDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/apprser")
    public List<CamelCaseMap> checkApprserList(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.checkApprserList(appaiappraiserDto);
    }

    /**
     * 중간점검(담당자) 저장/추가/삭제
     * @param appaiapapprcapaDto
     * @return int
     */
    @PostMapping
    public int saveAppaiappryearendp(AppaiapapprcapaDto appaiapapprcapaDto) {
        return appaiapapprdetailService.saveAppaiapapprcapa(appaiapapprcapaDto);
    }

    /**
     * 중간점검(담당자) 전송
     * @param appaiappraiserDto
     * @return int
     */
    @PostMapping("/submit")
    public int updateSubmitStatus(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.updateSubmitStatus(appaiappraiserDto, "SUB");
    }

    /**
     * 중간점검(담당자) 전송 취소
     * @param appaiappraiserDto
     * @return int
     */
    @PostMapping("/submitcancel")
    public int updateSubmitStatusCancel(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.updateSubmitStatus(appaiappraiserDto, "CANCEL");
    }
}

