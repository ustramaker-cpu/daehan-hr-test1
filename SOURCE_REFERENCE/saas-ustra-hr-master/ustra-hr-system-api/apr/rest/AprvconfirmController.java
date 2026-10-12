package hr.system.api.apr.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvconfirmDto;
import hr.system.api.apr.service.AprvconfirmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/aprvconfirm")
@RequiredArgsConstructor
public class AprvconfirmController {

    private final AprvconfirmService aprvconfirmService;

    /**
     * 결재 확인자 조회
     * @param AprvconfirmDto
     * @return List<CamelCaseMap>
     */
    @GetMapping
    public List<CamelCaseMap> findAprformList(AprvconfirmDto aprvconfirmDto){
        return aprvconfirmService.findAprformList(aprvconfirmDto);
    }

    /**
     * 결재 확인자 저장, 수정, 삭제
     * @param AprvconfirmDto
     * @return int
     */
    @PostMapping
    public int saveAprvform(AprvconfirmDto aprvconfirmDto){
        return aprvconfirmService.saveAprvform(aprvconfirmDto);
    }
}
