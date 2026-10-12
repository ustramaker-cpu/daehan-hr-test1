package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapexcpterDto;
import hr.system.api.app.appmng.service.AppmngapexcpterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngapexcpter")
@RequiredArgsConstructor
public class AppmngapexcpterRestController {

    private final AppmngapexcpterService appmngapexcpterService;

    /**
     * 교육결과보고
     * @param AppmngapexcpterDto
     * @return List<CamelCaseMap>
     */
     @GetMapping
     public List<CamelCaseMap> findappmngapexcpterList(AppmngapexcpterDto appmngapexcpterDto) {
        return appmngapexcpterService.findAppmngapexcpterList(appmngapexcpterDto);
     }

    /**
     * 2차평가 추가 점수 조회
     * @param appmngapexcpterDto
     * @return
     */
    @GetMapping("/selectaddpoint")
    public List<CamelCaseMap> findappmngapAddPointList(AppmngapexcpterDto appmngapexcpterDto) {
        return appmngapexcpterService.findappmngapAddPointList(appmngapexcpterDto);
    }

    @PostMapping("/app-insertupdatedelete")
    public int insertupdatedeleteApexcpter(AppmngapexcpterDto appmngapexcpterDto) {
        return appmngapexcpterService.insertupdatedeleteApexcpter(appmngapexcpterDto, appmngapexcpterDto.getAppmngapexcpterDtoList());
    }
    /*
    2차 평가자 생성
     */
    @PostMapping("/appraddpoint")
    public int insertupdatedeleteAddPint(AppmngapexcpterDto appmngapexcpterDto) {
        return appmngapexcpterService.insertupdatedeleteAddPoint(appmngapexcpterDto, appmngapexcpterDto.getAppmngapexcpterDtoList());
    }


    @PostMapping("/targetappraddpoint")
    public int inserTargetAddPointList(AppmngapexcpterDto appmngapexcpterDto) {
        return appmngapexcpterService.inserTargetAddPointList(appmngapexcpterDto);
    }


    @PostMapping("/checkapapprobj")
    public int checkApapprobj(AppmngapexcpterDto appmngapexcpterDto) {
        return appmngapexcpterService.checkApapprobj(appmngapexcpterDto, appmngapexcpterDto.getAppmngapexcpterDtoList());
    }
}

