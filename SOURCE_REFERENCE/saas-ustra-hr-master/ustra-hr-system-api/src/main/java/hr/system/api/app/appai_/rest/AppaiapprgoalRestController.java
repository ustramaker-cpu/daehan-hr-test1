package hr.system.api.app.appai_.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapappraiserDto;
import hr.system.api.app.appai.dto.AppaiapapprcapaDto;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappraiserDto;
import hr.system.api.app.appai.service.AppaiapapprdetailService;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import hr.system.api.app.appai.service.AppaiappryearendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai_/appaiapprgoal")
@RequiredArgsConstructor
public class AppaiapprgoalRestController {

    private final AppaiapapprtargetService appaiapapprtargetService;
    private final AppaiapapprdetailService appaiapapprdetailService;

    /**
     * 목표설정 조회
     * @param appaiapapprcapaDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiapprgoalList(AppaiapapprcapaDto appaiapapprcapaDto) {
        return appaiapapprdetailService.findAppaiapprdetailList(appaiapapprcapaDto);
    }

    /**
     * 평자대상자에 따른 평가차수 조회(공통)
     * */
    @GetMapping("/getAppraiserSeqno")
    public Integer getAppraiserSeqno(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprdetailService.findApappraiserSeqno(appaiapapprindDto);
    }

    /**
     * 목표설정 오픈여부 확인 데이터
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/checkind")
    public List<CamelCaseMap> checkInd(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.checkApprInd(appaiapapprindDto);
    }

    /**
     * 목표설정 전송여부 확인 데이터
     * @param appaiappraiserDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/apprser")
    public List<CamelCaseMap> checkApprserList(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.checkApprserList(appaiappraiserDto);
    }

    /**
     * 목표설정 저장/추가/삭제
     * @param appaiapapprcapaDto
     * @return int
     */
    @PostMapping
    public int saveAppaiapprgoal(AppaiapapprcapaDto appaiapapprcapaDto) {
        return appaiapapprdetailService.saveAppaiapapprcapa(appaiapapprcapaDto);
    }

    /**
     * 목표설정 전송
     * @param appaiapappraiserDto
     * @return int
     */
    @PostMapping("/submit")
    public int updateDetailFlag(AppaiapappraiserDto appaiapappraiserDto) {
        return appaiapapprdetailService.updateDetailInd(appaiapappraiserDto);
    }

//    /**
//     * 목표설정 전송 취소
//     * @param appaiappraiserDto
//     * @return int
//     */
//    @PostMapping("/submitcancel")
//    public int updateSubmitStatusCancel(AppaiappraiserDto appaiappraiserDto) {
//        return appaiapapprdetailService.updateSubmitStatus(appaiappraiserDto, "CANCEL");
//    }

}

