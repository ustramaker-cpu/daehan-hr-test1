package hr.system.api.edu.educdp.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.*;
import hr.system.api.edu.educdp.service.EducdpcpjobmngService;
import hr.system.api.edu.educdp.service.EducdpcpmapmngService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/educdp/educdpcpmapmng")
@RequiredArgsConstructor
public class EducdpcpmapmngRestController {

    private final EducdpcpmapmngService educdpcpmapmngService;
    private final EducdpcpjobmngService educdpcpjobmngService;

    @GetMapping
    public List<CamelCaseMap> findEducdpcpjobmngList(EducdpcpjobmngDto educdpcpjobmngDto) {
         return educdpcpjobmngService.findEducdpcpjobmngList(educdpcpjobmngDto);
    }

    @GetMapping("/jobcode-history")
    public List<CamelCaseMap> findJobcodeHistory(EducdpcpjobmngDto educdpcpjobmngDto){
        return educdpcpjobmngService.findJobcodeHistory(educdpcpjobmngDto);
    }

    //단위업무
    @GetMapping("/tablist1")
    public List<CamelCaseMap> findEducdpcpjobunitList(EducdpcpjobunitDto educdpcpjobunitDto) {
        return educdpcpmapmngService.findEducdpcpjobunitList(educdpcpjobunitDto);
    }

    //교육
    @GetMapping("/tablist2")
    public List<CamelCaseMap> findEducdpcpjobeduList(EducdpcpjobeduDto educdpcpjobeduDto) {
        return educdpcpmapmngService.findEducdpcpjobeduList(educdpcpjobeduDto);
    }

    //자격증
    @GetMapping("/tablist3")
    public List<CamelCaseMap> findEducdpcpjoblisncList(EducdpcpjoblisncDto educdpcpjoblisncDto) {
        return educdpcpmapmngService.findEducdpcpjoblisncList(educdpcpjoblisncDto);
    }

    //자격증
    @GetMapping("/tablist4")
    public List<CamelCaseMap> findEducdpcpjobcapaList(EducdpcpjobcapaDto educdpcpjobcapaDto) {
        return educdpcpmapmngService.findEducdpcpjobcapaList(educdpcpjobcapaDto);
    }

    @GetMapping("/jobunitcode-history")
    public List<CamelCaseMap> findJobunitcodeHistory(EducdpcpjobunitDto educdpcpjobunitDto){
        return educdpcpmapmngService.findJobunitcodeHistory(educdpcpjobunitDto);
    }

    @PostMapping("/jobunit-insertupdatedelete")
    public int insertupdatedeleteCpjobunit(EducdpcpjobunitDto educdpcpjobunitDto) {
        return educdpcpmapmngService.insertupdatedeleteCpjobunit(educdpcpjobunitDto.getEducdpcpjobunitDtoList());
    }

    @PostMapping("/jobedu-insertupdatedelete")
    public int insertupdatedeleteCpjobedu(EducdpcpjobeduDto educdpcpjobeduDto) {
        return educdpcpmapmngService.insertupdatedeleteCpjobedu(educdpcpjobeduDto.getEducdpcpjobeduDtoList());
    }

    @PostMapping("/joblisnc-insertupdatedelete")
    public int insertupdatedeleteCpjoblisnc(EducdpcpjoblisncDto educdpcpjoblisncDto) {
        return educdpcpmapmngService.insertupdatedeleteCpjoblisnc(educdpcpjoblisncDto.getEducdpcpjoblisncDtoList());
    }

    @PostMapping("/jobcapa-insertupdatedelete")
    public int insertupdatedeleteCpjobcapa(EducdpcpjobcapaDto educdpcpjobcapaDto) {
        return educdpcpmapmngService.insertupdatedeleteCpjobcapa(educdpcpjobcapaDto.getEducdpcpjobcapaDtoList());
    }
}
