package hr.system.api.app.appmng.rest;


import hr.system.api.app.appmng.dto.AppmngapexcpterDto;
import hr.system.api.app.appmng.dto.AppmngapforgpointDto;
import hr.system.api.app.appmng.service.AppmngapexcpterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * hr.system.api.edu.edumng.rest
 *
 * Description :
 * </pre>
 *
 * @author gynam
 * @version 1.0
 * @see <pre>
 * == 개정이력(Modification Information) ==
 *
 * 수정일      수정자    수정내용
 * ---------- -------- -------------------
 * 2021.06.30  gynam    최초 생성
 * </pre>
 * @since 2021.06.30
 */

@Slf4j
@RestController
@RequestMapping("/app/appmng/appmngapaddpointupload")
@RequiredArgsConstructor
public class AppmngapaddpointuploadRestController {

    private final AppmngapexcpterService appmngapexcpterService;

    @PostMapping("/save")
    public int saveappmngapaddpointupload(AppmngapexcpterDto appmngapexcpterDto){
        return appmngapexcpterService.insertupdatedeleteAddPoint(appmngapexcpterDto, appmngapexcpterDto.getAppmngapexcpterDtoList());
    }
}
