package hr.system.api.edu.edumil.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumil.dto.EdureqstatusDto;
import hr.system.api.edu.edumil.service.EdureqstatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/empmenu/edumil")
@RequiredArgsConstructor
public class EdureqstatusRestController {
    private final EdureqstatusService edureqstatusService;

    /* 임직원용 교육신청현황 조회 */
    @GetMapping("/edureqstatusempl")
    public List<CamelCaseMap> findEdureqstatusemplList(EdureqstatusDto edureqstatusDto) {
        return edureqstatusService.findEdureqstatusemplList(edureqstatusDto);
    }

    /* 부서장용 교육신청현황 조회 */
    @GetMapping("/edureqstatusleader")
    public List<CamelCaseMap> findEdureqstatusleaderList(EdureqstatusDto edureqstatusDto) {
        return edureqstatusService.findEdureqstatusleaderList(edureqstatusDto);
    }

    /* 인사담당자용 교육신청현황 조회 */
    @GetMapping("/edureqstatus")
    public List<CamelCaseMap> findEdureqstatusList(EdureqstatusDto edureqstatusDto) {
        return edureqstatusService.findEdureqstatusList(edureqstatusDto);
    }
}
