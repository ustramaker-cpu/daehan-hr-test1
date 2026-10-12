package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngappgresDto;
import hr.system.api.app.appmng.service.AppmngappgresService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("appmngappgres")
@RequiredArgsConstructor
public class AppmngappgresRestController {

    private final AppmngappgresService appmngappgresService;

    /**
     * 교육결과보고
     * @param AppmngappgresDto
     * @return List<CamelCaseMap>
     */


    @GetMapping
    public List<CamelCaseMap> findappmngappgresList(AppmngappgresDto appmngappgresDto) {
        return appmngappgresService.findAppmngappgresList(appmngappgresDto);
    }

    @PostMapping("/app-insertupdatedelete")
    public int insertupdatedeleteAppgcapa(AppmngappgresDto appmngappgresDto) {
        log.info("저장 호출");
        return appmngappgresService.insertupdatedeleteAppgres(appmngappgresDto, appmngappgresDto.getAppmngappgresDtoList());
    }

    @PostMapping("/copy")
    public int copyAppgcapa(AppmngappgresDto appmngappgcapaDto) {
        return appmngappgresService.copyAppgres(appmngappgcapaDto);
    }
}

