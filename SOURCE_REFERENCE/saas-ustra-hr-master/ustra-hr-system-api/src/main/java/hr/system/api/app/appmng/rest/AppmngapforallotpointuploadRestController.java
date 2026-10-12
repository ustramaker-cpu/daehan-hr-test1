package hr.system.api.app.appmng.rest;

import hr.system.api.app.appmng.dto.AppmngapforallotpointDto;
import hr.system.api.app.appmng.service.AppmngapforallotpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngapforallotpointupload")
@RequiredArgsConstructor
public class AppmngapforallotpointuploadRestController {

    private final AppmngapforallotpointService appmngapforallotpointService;

    @PostMapping("/save")
    public int saveappmngapforallotpointupload(AppmngapforallotpointDto appmngapforallotpointDto){
        return appmngapforallotpointService.saveappmngapforallotpointupload(appmngapforallotpointDto, appmngapforallotpointDto.getAppmngapforallotpointDtoList());
    }

}
