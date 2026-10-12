package hr.system.api.apr.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvtransfersetDto;
import hr.system.api.apr.service.AprvtransfersetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/aprvtransferset")
@RequiredArgsConstructor
public class AprvtransfersetController {

    private final AprvtransfersetService aprvtransfersetService;

    /**
     * 대리결재내역 조회
     * @param aprvtransfersetDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAprvtransfersetList(AprvtransfersetDto aprvtransfersetDto) {
        return aprvtransfersetService.findAprvtransfersetList(aprvtransfersetDto);
    }

    /**
     * 대리결재
     * @param aprvtransfersetDto
     * @return int
     */
    @PostMapping
    public int insertAprvtransfer(AprvtransfersetDto aprvtransfersetDto) {
        return aprvtransfersetService.insertAprvtransfer(aprvtransfersetDto);
    }

    /**
     * 회수
     * @param aprvtransfersetDto
     * @return int
     */
    @PutMapping
    public int updateAprvtransfer(AprvtransfersetDto aprvtransfersetDto) {
        return aprvtransfersetService.updateAprvtransfer(aprvtransfersetDto);
    }


    @DeleteMapping
    public int deleteAprvtransfer(AprvtransfersetDto aprvtransfersetDto){
        return aprvtransfersetService.deleteAprvtransfer(aprvtransfersetDto);
    }


}
