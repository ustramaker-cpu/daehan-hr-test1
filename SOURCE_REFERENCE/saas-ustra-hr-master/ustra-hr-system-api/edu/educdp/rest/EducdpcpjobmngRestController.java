package hr.system.api.edu.educdp.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobmngDto;
import hr.system.api.edu.educdp.service.EducdpcpjobmngService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/educdp/educdpcpjobmng")
@RequiredArgsConstructor
public class EducdpcpjobmngRestController {

    private final EducdpcpjobmngService educdpcpjobmngService;

    @GetMapping
    public List<CamelCaseMap> findEducdpcpjobmngList(EducdpcpjobmngDto educdpcpjobmngDto) {
         return educdpcpjobmngService.findEducdpcpjobmngList(educdpcpjobmngDto);
    }

    @PostMapping("/cdp-insertupdatedelete")
    public int insertupdatedeleteCpjobmaster(EducdpcpjobmngDto educdpcpjobmngDto) {
        return educdpcpjobmngService.insertupdatedeleteCpjobmaster(educdpcpjobmngDto.getEducdpcpjobmngDtoList());
    }

    @GetMapping("/jobcode-history")
    public List<CamelCaseMap> findJobcodeHistory(EducdpcpjobmngDto educdpcpjobmngDto){
        return educdpcpjobmngService.findJobcodeHistory(educdpcpjobmngDto);
    }
}
