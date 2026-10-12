package hr.system.api.apr.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.*;
import hr.system.api.apr.service.AprvformService;
import hr.system.api.apr.service.AprvlinesetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/aprvlineset")
@RequiredArgsConstructor
public class AprvlinesetController {

    private final AprvlinesetService aprvlinesetService;

    private final AprvformService aprvformService;

    /**
     * Deafult 결재선 조회
     *
     * @param aprvlinesetDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/default")
    public List<CamelCaseMap> findDefaultAprvline(AprvlinesetDto aprvlinesetDto) {
        return aprvlinesetService.findDefaultAprvline(aprvlinesetDto);
    }

    /**
     * 결재선 조회
     *
     * @param aprvlinesetDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAprvline(AprvlinesetDto aprvlinesetDto) {
        return aprvlinesetService.findAprvline(aprvlinesetDto);
    }

    /**
     * 결재선 저장
     *
     * @param aprvdocmstrDto
     * @return int
     */
    @PostMapping
    public boolean saveAprvline(AprvdocmstrDto aprvdocmstrDto) {
        return aprvlinesetService.saveApproval(aprvdocmstrDto);
    }


    /**
     * 문서번호 조회
     *
     * @param aprvlinesetDto
     * @return String
     * @description 저장할 결재문서의 새로운 문서번호 key 값을 생성
     */
    @GetMapping("/docno")
    public String findDocNo(AprvlinesetDto aprvlinesetDto) {
        return aprvlinesetService.findDocNo(aprvlinesetDto);
    }

    /**
     * 문서양식관리 조회
     *
     * @param aprvformDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/form")
    public List<CamelCaseMap> findAprformList(AprvformDto aprvformDto) {
        return aprvformService.findAprformList(aprvformDto);
    }

    /**
     * 문서양식관리 조회 (결재자용)
     *
     * @param aprvformDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/form-approval")
    public List<CamelCaseMap> findApprovalAprformList(AprvformDto aprvformDto) {
        return aprvformService.findApprovalAprformList(aprvformDto);
    }

    /**
     * 문서양식관리 조회 (관련 문서만)
     *
     * @param aprvformDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/form-emp")
    public List<CamelCaseMap> findEmpAprvformList(AprvformDto aprvformDto) {
        return aprvformService.findEmpAprvformList(aprvformDto);
    }

    /**
     * @param aprvdetailDto
     * @return boolean
     * @Description 본인 결재 순서 체크
     */
    @GetMapping("/myturn")
    public boolean checkMyApprovalTurn(AprvdetailDto aprvdetailDto) {
        return aprvlinesetService.checkMyApprovalTurn(aprvdetailDto);
    }


    /**
     * @param aprvdetailDto
     * @return boolean
     * @Description 회수 가능 여부 체크
     */
    @GetMapping("/cancelchk")
    public boolean checkCancelPossible(AprvdetailDto aprvdetailDto) {
        return aprvlinesetService.checkCancelPossible(aprvdetailDto);
    }

    /**
     * @param aprvdetailDto
     * @return boolean
     * @Description 기안자 인지 체크
     */
    @GetMapping("/createdchk")
    public boolean checkCreate(AprvdetailDto aprvdetailDto) {
        return aprvlinesetService.checkCreate(aprvdetailDto);
    }

    /**
     * 조직도 조회
     * 결재선설정(approval-pop), 문서양식결재선관리(approval-confirm-pop) 팝업에서 사용
     * @param aprvdetailDto
     * @return
     */
    @GetMapping("/pop/orgChart")
    public List<CamelCaseMap> findAprvPopOrgChart(AprvPopDto aprvPopDto) {
        return aprvlinesetService.findAprvPopOrgChart(aprvPopDto);
    }

    /**
     * 사원 정보 검색에 의한 사원 조회
     * 결재선설정(approval-pop), 문서양식결재선관리(approval-confirm-pop) 팝업에서 사용
     * @param aprvdetailDto
     * @return
     */
    @GetMapping("/pop/emplBySearch")
    public List<CamelCaseMap> findAprvPopEmplBySearch(AprvPopDto aprvPopDto) {
        return aprvlinesetService.findAprvPopEmplBySearch(aprvPopDto);
    }

    /**
     * 부서 선택에 의한 사원 조회
     * 결재선설정(approval-pop), 문서양식결재선관리(approval-confirm-pop) 팝업에서 사용
     * @param aprvdetailDto
     * @return
     */
    @GetMapping("/pop/emplByoOrg")
    public List<CamelCaseMap> findAprvPopEmplByOrg(AprvPopDto aprvPopDto) {
        return aprvlinesetService.findAprvPopEmplByOrg(aprvPopDto);
    }

    /**
     * 결재선 결재의견 리스트 조회 (1차~n차 결재자)
     * aprvlineset.html 결재문서 내 [의견] 버튼에서 사용
     *
     * @param aprvdocmstrDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/history")
    public List<CamelCaseMap> findDocAprvdetailList(AprvdocmstrDto aprvdocmstrDto) {
        return aprvlinesetService.findDocAprvdetailList(aprvdocmstrDto);
    }
}
