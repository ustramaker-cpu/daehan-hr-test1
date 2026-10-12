package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendgDto;
import hr.system.api.app.appai.dto.AppaiappryearendmDto;
import hr.system.api.app.appai.dto.AppaiappryearendpDto;
import hr.system.api.app.appai.service.AppaiappryearendgService;
import hr.system.api.app.appai.service.AppaiappryearendmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappryearendm")
@RequiredArgsConstructor
public class AppaiappryearendmRestController {

    private final AppaiappryearendmService appaiappryearendmService;

    /**
     * 다면평가(담당자) 조회
     * @param appaiappryearendmDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappryearendmList(AppaiappryearendmDto appaiappryearendmDto) {
        return appaiappryearendmService.findAppaiappryearendmList(appaiappryearendmDto);
    }

    /**
     * 역량테이블 조회
     * @param appaiappryearendmDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/capa")
    public List<CamelCaseMap> findCapaList(AppaiappryearendmDto appaiappryearendmDto) {
        return appaiappryearendmService.findCapaList(appaiappryearendmDto);
    }

    /**
     * 평가등급코드 조회
     * @param appaiappryearendmDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/grade")
    public List<CamelCaseMap> findGradeList(AppaiappryearendmDto appaiappryearendmDto) {
        return appaiappryearendmService.findGradeList(appaiappryearendmDto);
    }

    /**
     * 오픈여부 조회
     * @param appaiappryearendmDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/checkInd")
    public List<CamelCaseMap> checkIndObj(AppaiappryearendmDto appaiappryearendmDto) {
        return appaiappryearendmService.checkIndObj(appaiappryearendmDto);
    }

    /**
     * 평가자 전송여부 조회
     * @param appaiappryearendmDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/apprser")
    public List<CamelCaseMap> checkApprserList(AppaiappryearendmDto appaiappryearendmDto) {
        return appaiappryearendmService.checkApprserList(appaiappryearendmDto);
    }

    /**
     * 다면평가(담당자) 저장/추가/삭제
     * @param appaiappryearendmDto
     * @return int
     */
    @PostMapping
    public int saveAppaiappryearendm(AppaiappryearendmDto appaiappryearendmDto) {
        return appaiappryearendmService.saveAppaiappryearendm(appaiappryearendmDto.getAppaiappryearendmList());
    }

    /**
     * 다면평가(담당자) 전송
     * @param appaiappryearendmDto
     * @return int
     */
    @PostMapping("/submit")
    public int updateSubmitStatus(AppaiappryearendmDto appaiappryearendmDto) {
        return appaiappryearendmService.updateSubmitStatus(appaiappryearendmDto, "SUB");
    }

    /**
     * 다면평가(담당자) 전송 취소
     * @param appaiappryearendmDto
     * @return int
     */
    @PostMapping("/submitcancel")
    public int updateSubmitStatusCancel(AppaiappryearendmDto appaiappryearendmDto) {
        return appaiappryearendmService.updateSubmitStatus(appaiappryearendmDto, "CANCEL");
    }

}

