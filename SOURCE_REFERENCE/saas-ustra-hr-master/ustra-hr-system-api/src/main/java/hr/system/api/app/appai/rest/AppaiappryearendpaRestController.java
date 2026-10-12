package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.*;
import hr.system.api.app.appai.service.*;
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
@RequestMapping("/app/appai/appaiappryearendpa")
@RequiredArgsConstructor
public class AppaiappryearendpaRestController {

    private final AppaiappryearendpaService appaiappryearendpaService;

    private final AppaiappryearendService appaiappryearendService;

    private final AppaiapapprtargetService appaiapapprtargetService;
    private final AppaiapapprdetailService appaiapapprdetailService;
    /**
     * 중간점검(관리자) 조회
     * @param appaiapapprcapaDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
//    public List<CamelCaseMap> findAppaiappryearendpaList(AppaiappryearendDto appaiappryearendDto) {
//        return appaiappryearendService.findAppaiappryearendList(appaiappryearendDto);
    public List<CamelCaseMap> findAppaiappryearendpaList(AppaiapapprcapaDto appaiapapprcapaDto) {
        return appaiapapprdetailService.findAppaiapprdetailList(appaiapapprcapaDto);
    }

    /**
     * 중간점검(관리자) 오픈여부
     * @param appaiapapprindDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/checkind")
//    public List<CamelCaseMap> checkInd(AppaiappryearendpDto appaiappryearendpDto) {
//        return appaiappryearendpaService.checkInd(appaiappryearendpDto);
    public List<CamelCaseMap> checkInd(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.checkApprInd(appaiapapprindDto);
    }

    /**
     * 중간점검(관리자) 평가자 조회
     * @param appaiappraiserDto
     * @return List<CamelCaseMap>s
     */
    @GetMapping("/apprser")
//    public List<CamelCaseMap> checkApprserList(AppaiappryearendDto appaiappryearendDto) {
//        return appaiappryearendService.checkApprserList(appaiappryearendDto);
    public List<CamelCaseMap> checkApprserList(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.checkApprserList(appaiappraiserDto);
    }

    /**
     * 중간점검(관리자) 저장/추가/삭제
     * @param appaiapapprcapaDto
     * @return int
     */
    @PostMapping
//    public int saveAppaiappryearendp(AppaiappryearendDto appaiappryearendDto) {
//        return appaiappryearendService.saveAppaiappryearend(appaiappryearendDto);
    public int saveAppaiappryearendp(AppaiapapprcapaDto appaiapapprcapaDto) {
        return appaiapapprdetailService.saveAppaiapapprcapa(appaiapapprcapaDto);
    }
    
    /**
     * 등급코드 조회 (공통)
     * */
    @GetMapping("/getgrade")
    public List<CamelCaseMap> getGrade(AppmngapgradeDto appmngapgradeDto) {
        return appaiappryearendService.findGradeList(appmngapgradeDto);
    }

    /**
     * 중간점검(개인용) 전송
     * @param appaiappraiserDto
     * @return int
     */
    @PostMapping("/submit")
//    public int updateSubmitStatus(AppaiappryearendDto appaiappryearendDto) {
//        return appaiappryearendService.updateSubmitStatus(appaiappryearendDto, "SUB");
    public int updateSubmitStatus(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.updateSubmitStatus(appaiappraiserDto, "SUB");
    }

    /**
     * 중간점검(개인용) 전송 취소
     * @param appaiappraiserDto
     * @return int
     */
    @PostMapping("/submitcancel")
//    public int updateSubmitStatusCancel(AppaiappryearendDto appaiappryearendDto) {
//        return appaiappryearendService.updateSubmitStatus(appaiappryearendDto, "CANCEL");
    public int updateSubmitStatusCancel(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.updateSubmitStatus(appaiappraiserDto, "CANCEL");
    }

}

