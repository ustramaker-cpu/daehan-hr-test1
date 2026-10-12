package hr.system.api.edu.edumng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import hr.system.api.edu.edumng.service.EdumngedursltService;
import hr.system.api.wrk.wrkwork.dto.WrkworkaggrDto;
import hr.system.api.wrk.wrkwork.service.Wrkworkaggr100Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/edumng/edumngedurslt")
@RequiredArgsConstructor
public class EdumngedursltRestController {

    private final EdumngedursltService edumngedursltService;

    @GetMapping
    public List<CamelCaseMap> findEdumngedursltList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltService.findEdumngedursltList(edumngedursltDto);
    }

    @GetMapping("/tablist")
    public List<CamelCaseMap> findEdumngedursltTabList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltService.findEdumngedursltTabList(edumngedursltDto);
    }

    @PostMapping("/edu-insertupdatedelete")
    public int insertupdatedeleteEduRslt(EdumngedursltDto edumngedursltDto) {
        return edumngedursltService.insertupdatedeleteEduRslt(edumngedursltDto, edumngedursltDto.getEdumngedursltDtoList());
    }

    @GetMapping("/cpjobmaster")
    public List<CamelCaseMap> findCpjobmasterList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltService.findCpjobmasterList(edumngedursltDto);
    }

    @GetMapping("/cppgdesc")
    public List<CamelCaseMap> findCppgdescList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltService.findCppgdescList(edumngedursltDto);
    }

    @GetMapping("/cpjobedu")
    public List<CamelCaseMap> findCpjobeduList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltService.findCpjobeduList(edumngedursltDto);
    }

    @PostMapping("/updatecompletechange")
    public int updateCompleteChange(EdumngedursltDto edumngedursltDto) {
        return edumngedursltService.updateCompleteChange(edumngedursltDto, edumngedursltDto.getEdumngedursltDtoList());
    }
}
