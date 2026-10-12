package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapaprswgtDto;
import hr.system.api.app.appmng.service.AppmngapaprswgtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngapaprswgt")
@RequiredArgsConstructor
public class AppmngapaprswgtRestController {

    private final AppmngapaprswgtService appmngapaprswgtService;

    /**
     * 평가자별기준관리 조회
     * @param appmngapaprswgtDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAppmngapaprswgtList(AppmngapaprswgtDto appmngapaprswgtDto) {
        return appmngapaprswgtService.findAppmngapaprswgtList(appmngapaprswgtDto);
    }

    /**
     * 평가자별기준관리 저장
     * @param appmngapaprswgtDto
     * @return int
     */
    @PostMapping
    public int saveAppmngapaprswgt(AppmngapaprswgtDto appmngapaprswgtDto) {
        return appmngapaprswgtService.saveAppmngapaprswgt(appmngapaprswgtDto.getAppmngapaprswgtList());
    }

}

