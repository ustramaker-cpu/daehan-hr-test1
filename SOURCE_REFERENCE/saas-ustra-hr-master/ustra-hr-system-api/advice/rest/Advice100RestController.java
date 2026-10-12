package hr.system.api.advice.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.advice.service.Advice100Service;
import hr.system.core.api.help.dto.HelpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/advice100")
@RequiredArgsConstructor
public class Advice100RestController {

    private final Advice100Service advice100Service;

    /**
     * 도움말 조회
     * @param HelpDto
     * @return List<CamelCaseMap>
     */

    @GetMapping
    public List<CamelCaseMap> findAdvice100List(HelpDto helpDto) {
        return advice100Service.findAdvice100List(helpDto);
    }


}
