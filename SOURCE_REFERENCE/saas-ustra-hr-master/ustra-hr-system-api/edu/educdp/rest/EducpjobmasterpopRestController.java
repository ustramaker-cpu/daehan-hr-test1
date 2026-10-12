package hr.system.api.edu.educdp.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobindivmngDto;
import hr.system.api.edu.educdp.service.EducpjobmasterpopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/educdp/educpjobmaster-pop")
@RequiredArgsConstructor
public class EducpjobmasterpopRestController {

    private final EducpjobmasterpopService educpjobmasterpopService;

    @GetMapping
    public List<CamelCaseMap> findEducpjobmasterList(EducdpcpjobindivmngDto educdpcpjobindivmngDto) {
        return educpjobmasterpopService.findEducpjobmasterList(educdpcpjobindivmngDto);
    }
}
