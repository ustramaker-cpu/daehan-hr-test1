package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import hr.system.api.app.appmng.dto.AppmngapforgpointDto;
import hr.system.api.app.appmng.service.AppmngapforgpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngapforgpoint")
@RequiredArgsConstructor
public class AppmngapforgpointRestController {

    private final AppmngapforgpointService appmngapforgpointService;

    @GetMapping("/list")
    public List<CamelCaseMap> appmngapfactpointList(AppmngapforgpointDto appmngapforgpointDto){
        return appmngapforgpointService.appmngapfactpointList(appmngapforgpointDto);
    }

    @GetMapping
    public AppmngapforgpointDto findappmngapfactpointList(AppmngapforgpointDto appmngapforgpointDto){
        return appmngapforgpointService.findappmngapforgpointList(appmngapforgpointDto);
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

    @PostMapping("/app-insertupdatedelete")
    public int insertupdatedeleteaApforgpoint(AppmngapforgpointDto appmngapforgpointDto) {
        return appmngapforgpointService.insertupdatedeleteaApforgpoint(appmngapforgpointDto, appmngapforgpointDto.getAppmngapforgpointDtoList());
    }

}
