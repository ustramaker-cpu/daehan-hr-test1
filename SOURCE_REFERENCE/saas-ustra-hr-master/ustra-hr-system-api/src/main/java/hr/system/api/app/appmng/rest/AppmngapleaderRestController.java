package hr.system.api.app.appmng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapleaderDto;

import hr.system.api.app.appmng.service.AppmngapleaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appmng/appleader")
@RequiredArgsConstructor
public class AppmngapleaderRestController {
    private final AppmngapleaderService appmngapleaderService;

    /**
     * 평가자 리스트 가져옴
     * @param appmngapleaderDto
     * @return
     */
    @GetMapping
    public List<CamelCaseMap> findAppleader(AppmngapleaderDto appmngapleaderDto){
        return appmngapleaderService.findAppleader(appmngapleaderDto);
    }

    /**
     * 평가자 생성 proc 수행
     * @param appmngapleaderDto
     * @return
     */
    @GetMapping("/app-save")
    public int findAppleaderIns(AppmngapleaderDto appmngapleaderDto)  {
        return appmngapleaderService.findAppleaderIns(appmngapleaderDto);
    }

    /**
     * 평가자 대상자 저장
     * @param appmngapleaderDto
     * @returnParseException
     */
    @PostMapping("/app-insertupdatedelete")
    public int insertupdatedeleteAppleader(AppmngapleaderDto appmngapleaderDto)  {
        return appmngapleaderService.insertupdatedeleteAppleader(appmngapleaderDto,appmngapleaderDto.getAppmngapleaderDtoList());
    }

    @PostMapping
    public int updatAppleader(AppmngapleaderDto appmngapleaderDto) {
        return appmngapleaderService.updatAppleader(appmngapleaderDto,appmngapleaderDto.getAppmngapleaderDtoList());
    }

}
