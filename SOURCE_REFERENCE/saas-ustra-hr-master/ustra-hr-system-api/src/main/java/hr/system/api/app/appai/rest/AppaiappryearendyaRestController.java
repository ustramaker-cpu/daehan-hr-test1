package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendDto;
import hr.system.api.app.appai.dto.AppaiappryearendyDto;
import hr.system.api.app.appai.service.AppaiappryearendService;
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
@RequestMapping("/app/appai/appaiappryearendya")
@RequiredArgsConstructor
public class AppaiappryearendyaRestController {

    private final AppaiappryearendyaService appaiappryearendyaService;
    private final AppaiappryearendService appaiappryearendService;
    /**
     * 연말평가(관리자) 조회
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappryearendyaList(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.findAppaiappryearendyaList(appaiappryearendyDto);
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
     * @param appaiappryearendDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/apprser")
    public List<CamelCaseMap> checkApprserList(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.checkApprserList(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 평가자 조회
     * @param appaiappryearendDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/appraiser")
    public List<CamelCaseMap> checkAppraiserList(AppaiappryearendDto appaiappryearendDto) {
        return appaiappryearendService.checkApprserList(appaiappryearendDto);
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

    @GetMapping("/getavgpoint")
    public List<CamelCaseMap> getAvgPoint(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaService.getAvgPoint(appaiappryearendyDto);
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
     * 연말평가(관리자) 전송
     * @param appaiappryearendDto
     * @return int
     */
    @PostMapping("/submit")
    public int updateSubmitStatus(AppaiappryearendDto appaiappryearendDto) {
        return appaiappryearendService.updateSubmitStatus(appaiappryearendDto, "SUB");
    }

    /**
     * 연말평가(관리자) 전송 취소
     * @param appaiappryearendDto
     * @return int
     */
    @PostMapping("/submitcancel")
    public int updateSubmitStatusCancel(AppaiappryearendDto appaiappryearendDto) {
        return appaiappryearendService.updateSubmitStatus(appaiappryearendDto, "CANCEL");
    }

}

