package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapcapaDto;
import hr.system.api.app.appmng.service.AppmngapcapaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngapcapa")
@RequiredArgsConstructor
public class AppmngapcapaRestController {

    private final AppmngapcapaService appmngapcapaService;

    /**
     * 성과 및 역량 항목 조회
     * @param AppmngapcapaDto
     * @return List<CamelCaseMap>
     */
     @GetMapping
     public List<CamelCaseMap> findappmngappgcapaList(AppmngapcapaDto appmngapcapaDto) {
        return appmngapcapaService.findAppmngapcapaList(appmngapcapaDto);
     }

     /**
      * 성과 및 역량 항목 관리
      * @param appmngapcapaDto
      * @return int
      * */
    @PostMapping("/app-insertupdatedelete")
    public int insertupdatedeleteAppgcapa(AppmngapcapaDto appmngapcapaDto) {
        return appmngapcapaService.insertupdatedeleteApcapa(appmngapcapaDto, appmngapcapaDto.getAppmngapcapaDtoList());
    }

    @PostMapping("/copy")
    public int copyAppgcapa(AppmngapcapaDto appmngappgcapaDto) {
        return appmngapcapaService.copyAppgcapa(appmngappgcapaDto);
    }
}

