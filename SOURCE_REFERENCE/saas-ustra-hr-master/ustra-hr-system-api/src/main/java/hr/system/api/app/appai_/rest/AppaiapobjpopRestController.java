package hr.system.api.app.appai_.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.service.AppobjpopService;
import hr.system.core.code.dto.CdgradecdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai_/appaiapobj-pop")
@RequiredArgsConstructor

public class AppaiapobjpopRestController {

    private final AppobjpopService appobjpopService;

    /**
     * 목표설정 대상자 생성 조직코드
     * @param cdgradecdDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppobjList(CdgradecdDto cdgradecdDto) {
        return appobjpopService.findCdgradecd(cdgradecdDto);
    }

    /**
     * 대상자생성기준 조회
     * @param appaiappobjlistgDto
     * @return String
     * */
    @GetMapping("/standpoint")
    public String findApapprobjstandStandpoint(AppaiappobjlistgDto appaiappobjlistgDto) {
        return appobjpopService.findApapprobjstandStandpoint(appaiappobjlistgDto);
    }

    /**
     * 생성기준일자 조회
     * @param appaiappobjlistgDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/baseDate")
    public List<CamelCaseMap> findAppobjBaseDate(AppaiappobjlistgDto appaiappobjlistgDto) {
        return appobjpopService.findAppobjBaseDate(appaiappobjlistgDto);
    }

    /**
     * 목표설정 대상자 생성
     * @param appaiappobjlistgDto
     * @return int
     */
    @GetMapping("/objmaketarget-service")
    public int objTargetList(AppaiappobjlistgDto appaiappobjlistgDto) {
        return appobjpopService.objTargetList(appaiappobjlistgDto);
    }

    /**
     * 목표설정 대상자 생성 (프로시저)
     * @param appaiappobjlistgDto
     * @return int
     */
    @PostMapping("/objmaketarget")
    public int insertCrtWrkleavmstr100(AppaiappobjlistgDto appaiappobjlistgDto) {
        return appobjpopService.insertObjTargetList(appaiappobjlistgDto);
    }

}

