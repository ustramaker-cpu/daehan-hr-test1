package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprcapaDto;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappraiserDto;
import hr.system.api.app.appai.dto.AppaiappryearendDto;
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
@RequestMapping("/app/appai/appaiappryearendg")
@RequiredArgsConstructor
public class AppaiappryearendgRestController {

    private final AppaiappryearendService appaiappryearendService;

    private final AppaiapapprtargetService appaiapapprtargetService;
    private final AppaiapapprdetailService appaiapapprdetailService;

    /**
     * 목표설정 조회
     * @param appaiapapprcapaDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappryearendgList(AppaiapapprcapaDto appaiapapprcapaDto) {
        return appaiapapprdetailService.findAppaiapprdetailList(appaiapapprcapaDto);
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
    public int saveAppaiappryearendg(AppaiapapprcapaDto appaiapapprcapaDto) {
        return appaiapapprdetailService.saveAppaiapapprcapa(appaiapapprcapaDto);
    }

    /**
     * 목표설정 전송
     * @param appaiappraiserDto
     * @return int
     */
    @PostMapping("/submit")
    public int updateSubmitStatus(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.updateSubmitStatus(appaiappraiserDto, "SUB");
    }

    /**
     * 목표설정 전송 취소
     * @param appaiappraiserDto
     * @return int
     */
    @PostMapping("/submitcancel")
    public int updateSubmitStatusCancel(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.updateSubmitStatus(appaiappraiserDto, "CANCEL");
    }

}

