package hr.system.api.apr.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvtransferDto;
import hr.system.api.apr.service.AprvtransferService;
import hr.system.api.statistics.dto.EmpcsfcDto;
import hr.system.api.statistics.service.EmpclassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/aprvtransfer")
@RequiredArgsConstructor
public class AprvtransferController {

    private final AprvtransferService aprvtransferService;
    private final EmpclassificationService empclassificationService;

    /**
     * 대결자 관리 조회
     * @param req AprvtransferDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAprformList(AprvtransferDto req){
        return aprvtransferService.findTransferList(req);
    }
    
    @GetMapping("/searchSuper")
    public int searchSuper(EmpcsfcDto req) {
        return empclassificationService.searchSuper(req);
    }

    /**
     * 대결자 관리 저장, 수정
     * @param req AprvtransferDto
     * @return int
     */
    @PostMapping("/save")
    public int saveAprvform(AprvtransferDto req){
        return aprvtransferService.saveTransferList(req);
    }
    
    /**
     * 대결자 관리 삭제
     * @param req AprvtransferDto
     * @return int
     */
    @PostMapping("/delete")
    public int deleteAprvform(AprvtransferDto req){
        return aprvtransferService.deleteTransferList(req);
    }


}
