package hr.system.api.edu.edumil.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumil.dto.EdumilmileageDto;
import hr.system.api.edu.edumil.service.EdumilmileageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/edumil/edumilmileage")
@RequiredArgsConstructor
public class EdumilmileageRestController {

    private final EdumilmileageService edumilmileageService;

    @GetMapping
    public List<CamelCaseMap> findEdumilmileageList(EdumilmileageDto edumilmileageDto) {
        //담당자의 경우는 저장상태는 안보이고 전송 및 확인만 보임..
        edumilmileageDto.setAdminFlag("Y");
        return edumilmileageService.findEdumilmileageList(edumilmileageDto);
    }

    @PostMapping("/edu-insertupdatedelete")
    public int insertupdatedeleteMileage(EdumilmileageDto edumilmileageDto) {
        return edumilmileageService.insertupdatedeleteMileage(edumilmileageDto.getEdumilmileageDtoList());
    }

    @PostMapping("/edu-transfer")
    public int tranferUpdateMileage(EdumilmileageDto edumilmileageDto) {
        return edumilmileageService.tranferUpdateMileage(edumilmileageDto, edumilmileageDto.getEdumilmileageDtoList());
    }

    @GetMapping("/getevaitem")
    public List<CamelCaseMap> findCded18_vList(EdumilmileageDto edumilmileageDto) {
        return edumilmileageService.findCded18_vList(edumilmileageDto);
    }
}
