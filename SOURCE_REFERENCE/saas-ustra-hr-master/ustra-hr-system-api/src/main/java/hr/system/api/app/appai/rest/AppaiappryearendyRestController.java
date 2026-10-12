package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendyDto;
import hr.system.api.app.appai.service.AppaiappryearendyService;
import hr.system.api.app.appai.service.AppaiappryearendyaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappryearendy")
@RequiredArgsConstructor
public class AppaiappryearendyRestController {

    private final AppaiappryearendyService appaiappryearendyService;
    private final AppaiappryearendyaService appaiappryearendyaService;

    /**
     * 중간점검(담당자) 조회
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappryearendyList(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyService.findAppaiappryearendyList(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 오픈여부
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/checkind")
    public List<CamelCaseMap> checkInd(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.checkInd(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 등급가져오기..
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/getgrade")
    public List<CamelCaseMap> getGrade(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.getGrade(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 직무수행능력및 태도
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/workevaluation")
    public CamelCaseMap getWorkEvaluation(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.getWorkEvaluation(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 직무수행능력및 태도
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/evaluation")
    public List<CamelCaseMap> getEvaluation(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.getEvaluation(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 평가자 조회
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/apprser")
    public List<CamelCaseMap> checkApprserList(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.checkApprserList(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 평가자 조회
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/apprser2")
    public List<CamelCaseMap> checkApprserList2(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.checkApprserList2(appaiappryearendyDto);
    }

    @GetMapping("/getfactwgt")
    public CamelCaseMap getFactwgt(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.getFactwgt(appaiappryearendyDto);
    }


    @GetMapping("/getaprswgt")
    public CamelCaseMap getAprswgt(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.getAprswgt(appaiappryearendyDto);
    }

    @GetMapping("/getpoint")
    public List<CamelCaseMap> getPoint(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.getPoint(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 저장1
     * @param appaiappryearendyDto
     * @return int
     */
    @PostMapping
    public int saveAppaiappryearendy(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.saveAppaiappryearendy(appaiappryearendyDto, appaiappryearendyDto.getAppaiappryearendyList());
    }

    /**
     * 연말평가(관리자) 저장2
     * @param appaiappryearendyDto
     * @return int
     */
    @PostMapping("/save")
    public int saveAppaiappryearendy2(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.saveAppaiappryearendy2(appaiappryearendyDto, appaiappryearendyDto.getAppaiappryearendyList());
    }

    /**
     * 연말평가(관리자) 0차 저장..
     * @param appaiappryearendyDto
     * @return int
     */
    @PostMapping("/save0")
    public int saveAppaiappryearendy0(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyService.saveAppaiappryearendy0(appaiappryearendyDto.getAppaiappryearendyList());
    }

    /**
     * 연말평가(관리자) 전송
     * @param appaiappryearendyDto
     * @return int
     */
    @PostMapping("/submit")
    public int updateSubmitStatus(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyService.updateSubmitStatus(appaiappryearendyDto, "SUB");
    }

    /**
     * 연말평가(관리자) 전송 취소
     * @param appaiappryearendyDto
     * @return int
     */
    @PostMapping("/submitcancel")
    public int updateSubmitStatusCancel(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyService.updateSubmitStatus(appaiappryearendyDto, "CANCEL");
    }

}

