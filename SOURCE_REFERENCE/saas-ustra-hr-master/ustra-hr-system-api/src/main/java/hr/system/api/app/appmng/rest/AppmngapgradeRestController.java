package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapgradeDto;
import hr.system.api.app.appmng.service.AppmngapgradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngapgrade")
@RequiredArgsConstructor
public class AppmngapgradeRestController {

    private final AppmngapgradeService appmngapgradeService;

    /**
     * 평가등급코드
     * @param appmngapgradeDto
     * @return List<CamelCaseMap>
     */
     @GetMapping
     public List<CamelCaseMap> findAppmngapgradeList(AppmngapgradeDto appmngapgradeDto) {
        return appmngapgradeService.findAppmngapgradeList(appmngapgradeDto);
     }

    @GetMapping("/app-history")
    public List<CamelCaseMap> findGradeKindApprHistory(AppmngapgradeDto appmngapgradeDto){
        return appmngapgradeService.findGradeKindApprHistory(appmngapgradeDto);
    }

    @PostMapping("/app-insertupdatedelete")
    public int insertupdatedeleteApgrade(AppmngapgradeDto appmngapgradeDto) {
        return appmngapgradeService.insertupdatedeleteApgrade(appmngapgradeDto, appmngapgradeDto.getAppmngapgradeDtoList());
    }
}

