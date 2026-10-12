package hr.system.api.edu.edumng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import hr.system.api.edu.edumng.service.EdumngedursltmngService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/edumng/edumngedursltmng")
@RequiredArgsConstructor
public class EdumngedursltmngRestController {

    private final EdumngedursltmngService edumngedursltmngService;

    @GetMapping
    public List<CamelCaseMap> findEdumngedursltmngList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltmngService.findEdumngedursltmngList(edumngedursltDto);
    }

    @GetMapping("/tablist")
    public List<CamelCaseMap> findEdumngedursltmngTabList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltmngService.findEdumngedursltmngTabList(edumngedursltDto);
    }

    @PostMapping("/edu-insertupdatedeletemng")
    public int insertupdatedeleteEduRsltmng(EdumngedursltDto edumngedursltDto) {
        return edumngedursltmngService.insertupdatedeleteEduRsltmng(edumngedursltDto, edumngedursltDto.getEdumngedursltDtoList());
    }

    @GetMapping("/cpjobmaster")
    public List<CamelCaseMap> findCpjobmasterList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltmngService.findCpjobmasterList(edumngedursltDto);
    }

    @GetMapping("/cppgdesc")
    public List<CamelCaseMap> findCppgdescList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltmngService.findCppgdescList(edumngedursltDto);
    }

    @GetMapping("/cpjobedu")
    public List<CamelCaseMap> findCpjobeduList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltmngService.findCpjobeduList(edumngedursltDto);
    }

    @GetMapping("/chkorleader")
    public int checkOrleader(EdumngedursltDto edumngedursltDto) {
        return edumngedursltmngService.checkOrleader(edumngedursltDto);
    }

}
