package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapfactwgtDto;
import hr.system.api.app.appmng.service.AppmngapfactwgtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngapfactwgt")
@RequiredArgsConstructor
public class AppmngapfactwgtRestController {

    private final AppmngapfactwgtService appmngapfactwgtService;

    /**
     * 교육결과보고
     * @param appmngapfactwgtDto
     * @return List<CamelCaseMap>
     */
     @GetMapping
     public List<CamelCaseMap> findappmngapfactwgtList(AppmngapfactwgtDto appmngapfactwgtDto) {
        return appmngapfactwgtService.findAppmngapfactwgtList(appmngapfactwgtDto);
     }

    @PostMapping("/app-insertupdatedelete")
    public int insertupdatedeleteApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto) {
        return appmngapfactwgtService.insertupdatedeleteApfactwgt(appmngapfactwgtDto, appmngapfactwgtDto.getAppmngapfactwgtDtoList());
    }

    @PostMapping("/copy")
    public int copyApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto) {
        return appmngapfactwgtService.copyApfactwgt(appmngapfactwgtDto);
    }
}

