package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapforallotpointDto;
import hr.system.api.app.appmng.service.AppmngapforallotpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngapforallotpoint")
@RequiredArgsConstructor
public class AppmngapforallotpointRestController {

    private final AppmngapforallotpointService appmngapforallotpointService;

    @GetMapping
    public List<CamelCaseMap> findappmngapforallotpointList(AppmngapforallotpointDto appmngapforallotpointDto){
        return appmngapforallotpointService.findappmngapforallotpointList(appmngapforallotpointDto);
    }

    @PostMapping("/app-insertupdatedelete")
    public int insertupdatedeleteApforallotpoint(AppmngapforallotpointDto appmngapforallotpointDto){
        return appmngapforallotpointService.insertupdatedeleteApforallotpoint(appmngapforallotpointDto, appmngapforallotpointDto.getAppmngapforallotpointDtoList());
    }

}
