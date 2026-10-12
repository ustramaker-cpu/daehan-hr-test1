package hr.system.api.edu.educdp.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobindivmngDto;
import hr.system.api.edu.educdp.service.EducdpcpjobindivmngService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/educdp/educdpcpjobindivmng")
@RequiredArgsConstructor
public class EducdpcpjobindivmngRestController {

    private final EducdpcpjobindivmngService educdpcpjobindivmngService;

    @GetMapping
    public List<CamelCaseMap> findEducdpcpjobindivmngList(EducdpcpjobindivmngDto educdpcpjobindivmngDto) {
        return educdpcpjobindivmngService.findEducdpcpjobindivmngList(educdpcpjobindivmngDto);
    }

    @GetMapping("/educdpcpjobindiv-history")
    public List<CamelCaseMap> findEducdpcpjobindivHistory(EducdpcpjobindivmngDto educdpcpjobindivmngDto) {
        return educdpcpjobindivmngService.findEducdpcpjobindivHistory(educdpcpjobindivmngDto);
    }

    /**
     * 개인별직무관리 저장/추가/삭제
     * @param educdpcpjobindivmngDto
     * @return int
     */
    @PostMapping
    public int saveDocapprovtm(EducdpcpjobindivmngDto educdpcpjobindivmngDto) {
        return educdpcpjobindivmngService.saveEducdpcpjobindivmng(educdpcpjobindivmngDto.getEducdpcpjobindivmngList());
    }
}
