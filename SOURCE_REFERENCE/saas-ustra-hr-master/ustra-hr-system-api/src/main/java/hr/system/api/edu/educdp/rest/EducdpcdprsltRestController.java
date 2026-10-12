package hr.system.api.edu.educdp.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcdprsltDto;
import hr.system.api.edu.educdp.service.EducdpcdprsltService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/educdp/educdpcdprslt")
@RequiredArgsConstructor
public class EducdpcdprsltRestController {

    private final EducdpcdprsltService educdpcdprsltService;

    @GetMapping
    public CamelCaseMap findEducdpcdprsltList(EducdpcdprsltDto educdpcdprsltDto) {
        return educdpcdprsltService.findEducdpcdprsltList(educdpcdprsltDto);
    }

    @GetMapping("/avg1")
    public CamelCaseMap findPgResultComAvg(EducdpcdprsltDto educdpcdprsltDto) {
        return educdpcdprsltService.findPgResultComAvg(educdpcdprsltDto);
    }

    @GetMapping("/avg2")
    public CamelCaseMap findPgResultPgCodeAvg(EducdpcdprsltDto educdpcdprsltDto) {
        return educdpcdprsltService.findPgResultPgCodeAvg(educdpcdprsltDto);
    }

    @GetMapping("/avg3")
    public CamelCaseMap findPgResultJobCodeAvg(EducdpcdprsltDto educdpcdprsltDto) {
        return educdpcdprsltService.findPgResultJobCodeAvg(educdpcdprsltDto);
    }

    @GetMapping("/avg4")
    public CamelCaseMap findPgResultJobHopeAvg(EducdpcdprsltDto educdpcdprsltDto) {
        return educdpcdprsltService.findPgResultJobHopeAvg(educdpcdprsltDto);
    }
}
