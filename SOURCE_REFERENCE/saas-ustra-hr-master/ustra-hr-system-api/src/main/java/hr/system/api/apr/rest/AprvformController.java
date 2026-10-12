package hr.system.api.apr.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvPopDto;
import hr.system.api.apr.dto.AprvformDto;
import hr.system.api.apr.service.AprvformService;
import hr.system.api.apr.service.AprvlinesetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/aprvform")
@RequiredArgsConstructor
public class AprvformController {

    private final AprvformService aprvformService;
    private final AprvlinesetService aprvlinesetService;

    /**
     * 문서양식관리 조회
     * @param aprvformDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAprformList(AprvformDto aprvformDto){
        return aprvformService.findAprformList(aprvformDto);
    }

    /**
     * 문서양식관리 저장, 수정, 삭제
     * @param aprvformDto
     * @return int
     */
    @PostMapping
    public int saveAprvform(AprvformDto aprvformDto){
        return aprvformService.saveAprvform(aprvformDto);
    }

    /**
     * 결재선설정 신청서 수정
     * @param aprvformDto
     * @return int
     */
    @PostMapping("/updateDepthYn")
    public int updateAprvformDepthYn(AprvformDto aprvformDto){
        return aprvformService.updateAprvformDepthYn(aprvformDto);
    }

    /**
     * 조직도 조회
     * 결재선설정(approval-pop), 문서양식결재선관리(approval-confirm-pop) 팝업에서 사용
     * @param aprvPopDto
     * @return
     */
    @GetMapping("/pop/orgChart")
    public List<CamelCaseMap> findAprvPopOrgChart(AprvPopDto aprvPopDto) {
        return aprvlinesetService.findAprvPopOrgChart(aprvPopDto);
    }

    /**
     * 사원 정보 검색에 의한 사원 조회
     * 결재선설정(approval-pop), 문서양식결재선관리(approval-confirm-pop) 팝업에서 사용
     * @param aprvPopDto
     * @return
     */
    @GetMapping("/pop/emplBySearch")
    public List<CamelCaseMap> findAprvPopEmplBySearch(AprvPopDto aprvPopDto) {
        return aprvlinesetService.findAprvPopEmplBySearch(aprvPopDto);
    }

    /**
     * 부서 선택에 의한 사원 조회
     * 결재선설정(approval-pop), 문서양식결재선관리(approval-confirm-pop) 팝업에서 사용
     * @param aprvPopDto
     * @return
     */
    @GetMapping("/pop/emplByoOrg")
    public List<CamelCaseMap> findAprvPopEmplByOrg(AprvPopDto aprvPopDto) {
        return aprvlinesetService.findAprvPopEmplByOrg(aprvPopDto);
    }

}
