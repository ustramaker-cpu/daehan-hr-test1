package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngappobjstandDto;
import hr.system.api.app.appmng.service.AppmngappobjstandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngappobjstand")
@RequiredArgsConstructor
public class AppmngappobjstandRestController {

    private final AppmngappobjstandService appmngappobjstandService;

    @GetMapping
    List<CamelCaseMap> findAppmngappobjstand(AppmngappobjstandDto appmngappobjstandDto){
        return appmngappobjstandService.findAppmngappobjstand(appmngappobjstandDto);
    }

    @PostMapping
    int saveAppmngappobjstand(AppmngappobjstandDto appmngappobjstandDto){
        return appmngappobjstandService.saveAppmngappobjstand(appmngappobjstandDto);
    }

}
