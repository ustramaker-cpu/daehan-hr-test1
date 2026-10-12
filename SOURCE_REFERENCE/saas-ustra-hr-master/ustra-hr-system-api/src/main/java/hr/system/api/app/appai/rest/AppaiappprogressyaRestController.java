package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistyDto;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import hr.system.api.app.appai.dto.AppaiappryearendDto;
import hr.system.api.app.appai.service.AppaiappprogressyaService;
import hr.system.api.app.appai.service.AppaiappryearendService;
import hr.system.api.app.appmng.dto.AppmngapforgpointDto;
import hr.system.api.app.appmng.dto.AppmngapgradeDto;
import hr.system.api.app.appmng.service.AppmngapforgpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappprogressya")
@RequiredArgsConstructor
public class AppaiappprogressyaRestController {

    private final AppaiappprogressyaService appaiappprogressyaService;

    private final AppaiappryearendService appaiappryearendService;

    private final AppmngapforgpointService appmngapforgpointService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 연말평가 평가진행현황관리(관리자) 조회
     * @param appaiappobjlistyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppaiappprogressyaList(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappprogressyaService.findAppaiappprogressyaList(appaiappobjlistyDto);
    }


    /**
     * 연말평가 평가진행현황관리(관리자) 조회_ amdin
     * @param appaiappobjlistyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/admin")
    public List<CamelCaseMap> findAppaiappprogressyaListadmin(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappprogressyaService.findAppaiappprogressyaListadmin(appaiappobjlistyDto);
    }

    /**
     * 연말평가 배분총점 가져오기..
     * @param appmngapforgpointDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/appmngapforgpoint")
    public AppmngapforgpointDto findappmngapfactpointList(AppmngapforgpointDto appmngapforgpointDto){
        return appmngapforgpointService.findappmngapfactpointList(appmngapforgpointDto);
    }

    /**
     * 연말평가 2차 학원 배분총점 가져오기..
     * @param appmngapforgpointDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/totpoint")
    public AppmngapforgpointDto findappmngapfactTotpointList(AppmngapforgpointDto appmngapforgpointDto) {
        return appmngapforgpointService.findappmngapfactTotpointList(appmngapforgpointDto);
    }


    /**
     * 연말평가진행관리(평가자) 저장
     * @param appaiappobjlistyDto
     * @return
     */
    @PostMapping("/appaiappprogressyaSave")
    public int appaiappprogressyaSave(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappprogressyaService.appaiappprogressyaSave(appaiappobjlistyDto);
    }

    /**
     * 연말평가 전송시 최종점수 가져오기..
     * @param appaiappprogressyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/getavgtotal")
    public List<CamelCaseMap> getAVGTotalPoint(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.getAVGTotalPoint(appaiappprogressyDto);
    }

    /**
     * 대상자 생성 기준관리 조회하기
     * @param appaiappprogressyDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/gradecount")
    public List<CamelCaseMap> getGradeCount(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.getGradeCount(appaiappprogressyDto);
    }

    /**
     * 연말평가 평가진행현황관리(담당자) 상태 수정 (오픈)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/submit")
    public int updateSubmit(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "SUBMIT");
    }
    /**
     * 연말평가 평가진행현황관리(담당자) 상태 수정 (오픈)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/pointupdate")
    public int pointupdate(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.pointupdate(appaiappprogressyDto);
    }

    /**
     * 연말평가 평가진행현황관리(담당자) 상태 수정 (오픈 취소)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/submitcancel")
    public int updateSubmitCancel(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "SUBMIT_CANCEL");
    }

    /**
     * 연말평가(관리자) 평가자 R 업데이트
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/apprser1ststatus")
    public int update1stStatus(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.updateSubmitStatus(appaiappprogressyDto, "READING");
    }

    /**
     * 연말평가 평가진행현황관리(관리자) 상태 수정 (오픈)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/open")
    public int updateOpen(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "OPEN");
    }

    /**
     * 연말평가 평가진행현황관리(관리자) 상태 수정 (오픈 취소)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/opencancel")
    public int updateOpenCancel(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "OPEN_CANCEL");
    }

    /**
     * 연말평가 평가진행현황관리(관리자) 상태 수정 (평가마감)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/close")
    public int updateClose(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "CLOSE");
    }

    /**
     * 연말평가 평가진행현황관리(관리자) 상태 수정 (평가마감 취소)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/closecancel")
    public int updateCloseCancel(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "CLOSE_CANCEL");
    }

    /**
     * 연말평가 평가진행현황관리(관리자) 상태 수정 (최종마감)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/closelast")
    public int updateCloseLast(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "CLOSE_LAST");
    }

    /**
     * 연말평가 평가진행현황관리(관리자) 상태 수정 (최종마감 취소)
     * @param appaiappprogressyDto
     * @return int
     */
    @PostMapping("/closelastcancel")
    public int updateCloseLastCancel(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.updateStatusInd(appaiappprogressyDto.getAppaiappprogressyList(), "CLOSE_LAST_CANCEL");
    }

    /**
     * 평가자 평가조직 조회
     * @param appaiappprogressyDto
     * @return
     */
    @GetMapping("/orleader")
    public List<CamelCaseMap> getOrgleader(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaService.getOrgleader(appaiappprogressyDto);
    }

    /**
     * 평가자 평가조직 조회
     * @param appaiappprogressyDto
     * @return
     */
    @PostMapping("/checkind")
    public int checkind(AppaiappprogressyDto appaiappprogressyDto) {
        int result = 0;
        result = appaiappprogressyaService.checkind(appaiappprogressyDto);
        return result;
    }

    /**
     * 학원 내 평가가 마감되지 않는 곳 확인
     * @param appaiappprogressyDto
     * @return
     */
    @PostMapping("/checksend")
    public int checksend(AppaiappprogressyDto appaiappprogressyDto) {
        int result = 0;
        result = appaiappprogressyaService.checksend(appaiappprogressyDto);
        return result;
    }

    /**
     * 연말평가(관리자) 등급가져오기..
     * @param appmngapgradeDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/getgrade")
    public List<CamelCaseMap> getGrade(AppmngapgradeDto appmngapgradeDto) {
        return appaiappryearendService.findGradeList(appmngapgradeDto);
    }

    /**
     * 평가자 차수 가져오기
     * @param appaiappryearendDto
     * @return
     */
    @GetMapping("/appraiserind")
    public List<CamelCaseMap> getAppraiserind(AppaiappryearendDto appaiappryearendDto) {
        return appaiappryearendService.getAppraiserind(appaiappryearendDto);
    }

    /**
     * 평가자 전송&전송취소
     * @param appaiappobjlistyDto
     * @return
     */
    @PostMapping("/send")
    public int send(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappprogressyaService.send(appaiappobjlistyDto);
    }

    /**
     * 평가자 평가내역 삭제
     * @param appaiappobjlistyDto
     * @return
     */
    @PostMapping("/clear")
    public int clear(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappprogressyaService.clear(appaiappobjlistyDto);
    }

    /**
     * 연말평가진행관리(관리자) 최종등급 인사정보에 반영
     * @param appaiappobjlistyDto
     * @return
     */
    @PostMapping("/apprSavePoint")
    public int apprSavePoint(AppaiappobjlistyDto appaiappobjlistyDto) {
        return appaiappprogressyaService.apprSavePoint(appaiappobjlistyDto);
    }

}

