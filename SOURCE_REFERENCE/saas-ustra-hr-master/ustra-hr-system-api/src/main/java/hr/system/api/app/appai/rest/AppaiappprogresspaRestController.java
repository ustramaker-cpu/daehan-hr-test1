package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import hr.system.api.app.appai.dto.AppaiappraiserDto;
import hr.system.api.app.appai.service.AppaiapapprdetailService;
import hr.system.api.app.appai.service.AppaiapapprtargetService;
import hr.system.api.app.appai.service.AppaiappprogresspaService;
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
@RequestMapping("/app/appai/appaiappprogresspa")
@RequiredArgsConstructor
public class AppaiappprogresspaRestController {

    private final AppaiappprogresspaService appaiappprogresspaService;
    private final AppaiapapprtargetService appaiapapprtargetService;
    private final AppaiapapprdetailService appaiapapprdetailService;

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
    @GetMapping("/appryearendpa")
//    public List<CamelCaseMap> appaiappryearendpaList(AppaiappprogresspDto appaiappprogresspDto) {
//        return appaiappprogresspaService.appaiappryearendpaList(appaiappprogresspDto);
    public List<CamelCaseMap> appaiappryearendpaList(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.findAppraiseInd(appaiapapprindDto);
    }

    /**
     * 중간점검 평가진행현황관리(관리자) 평가자 R 업데이트 [2023.07.31삭제]
     * @param appaiappraiserDto
     * @return int
     */
    @PostMapping("/apprser1ststatus")
//    public int update1stStatus(AppaiappprogresspDto appaiappprogresspDto) {
//        return appaiappprogresspaService.updateStatus(appaiappprogresspDto, "READING");
    public int update1stStatus(AppaiappraiserDto appaiappraiserDto) {
        return appaiapapprdetailService.updateSubmitStatus(appaiappraiserDto, "READ");
    }

    /**
     * 중간점검 평가진행현황관리(관리자) 전송
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/submit")
//    public int updateSubmit(AppaiappprogresspDto appaiappprogresspDto) {
//        return appaiappprogresspaService.updateSubmit(appaiappprogresspDto.getAppaiappprogresspList(), "SUBMIT");
    public int updateSubmit(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_EXEC_IND");
    }

    /**
     * 중간점검 평가진행현황관리(관리자) 전송취소
     * @param appaiapapprindDto
     * @return int
     */
    @PostMapping("/submitcancel")
//    public int updateSubmitCancel(AppaiappprogresspDto appaiappprogresspDto) {
//        return appaiappprogresspaService.updateSubmit(appaiappprogresspDto.getAppaiappprogresspList(), "SUBMIT_CANCEL");
    public int updateSubmitCancel(AppaiapapprindDto appaiapapprindDto) {
        return appaiapapprtargetService.updateConfirm(appaiapapprindDto.getAppaiapapprindList(), "CLOSE_EXEC_IND_CANCEL");
    }

}

