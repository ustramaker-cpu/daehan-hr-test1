package hr.system.api.app.appai.rest;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsitm.framework.core.type.CamelCaseMap;

import hr.system.api.app.appai.dto.AppaiappryearendsDto;
import hr.system.api.app.appai.service.AppaiappryearendsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappryearends")
@RequiredArgsConstructor
public class AppaiappryearendsRestController {

    private final AppaiappryearendsService appaiappryearendsService;

    //@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }



    /**
     * 자기신고서 현 직무 만족도 조회_grid용
     * @param AppaiappryearendsDto
     * @return Map<String, List<CamelCaseMap>>
     */
    @GetMapping("/apopncnt1")
    public List<CamelCaseMap> getApopncntForGrid1(AppaiappryearendsDto aprydsDto){
    	return appaiappryearendsService.getApopncnt1(aprydsDto);
    }

    /**
     * 자기신고서 전보희망 조회_grid용
     * @param AppaiappryearendsDto
     * @return Map<String, List<CamelCaseMap>>
     */
    @GetMapping("/apopncnt2")
    public List<CamelCaseMap> getApopncntForGrid2(AppaiappryearendsDto aprydsDto){
    	return appaiappryearendsService.getApopncnt2(aprydsDto);
    }

    /**
     * 자기신고서 전보희망부서조회
     * @param AppaiappryearendsDto
     * @return Map<String, List<CamelCaseMap>>
     */
    @GetMapping("/aporgwish")
    public List<CamelCaseMap> getAporgwishForGrid(AppaiappryearendsDto aprydsDto){
    	return appaiappryearendsService.getAporgwish(aprydsDto);
    }

    /**
     * 자기신고서 기타의견
     * @param AppaiappryearendsDto
     * @return Map<String, List<CamelCaseMap>>
     */
    @GetMapping("/apopnetc")
    public List<CamelCaseMap> getApopnetcForGrid(AppaiappryearendsDto aprydsDto){
    	return appaiappryearendsService.getApopnetc(aprydsDto);
    }

    /**
     * 자기신고서 본인 기본정보 조회
     * @param AppaiappryearendsDto
     * @return CamelCaseMap
     */
    @GetMapping("/self")
    public CamelCaseMap getSelfInfo(AppaiappryearendsDto aprydsDto) {

    	return appaiappryearendsService.getSelfInfo(aprydsDto);
    }

    /**
     * 마감여부 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/checkind")
    public List<CamelCaseMap> checkInd(AppaiappryearendsDto aprydsDto) {
    	return appaiappryearendsService.checkInd(aprydsDto);
    }

    /**
     * 마감여부 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/apprser")
    public List<CamelCaseMap> checkapprser(AppaiappryearendsDto aprydsDto) {
    	return appaiappryearendsService.checkApprser(aprydsDto);
    }


    /**
     * 부서정보 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/org")
    public List<CamelCaseMap> getOrgInfo(AppaiappryearendsDto aprydsDto){
    	return appaiappryearendsService.getOrg(aprydsDto);
    }

    /**
     * 직무정보 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/job")
    public List<CamelCaseMap> getJobList(AppaiappryearendsDto aprydsDto){
    	return appaiappryearendsService.getJobList(aprydsDto);
    }

    /**
     * 직무만족도코드 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/ap61")
    public List<CamelCaseMap> getCdap61List(AppaiappryearendsDto aprydsDto){
    	return appaiappryearendsService.getCdap61(aprydsDto);
    }

    /**
     * 전보희망코드 조회
     * @param AppaiappryearendsDto
     * @return List<CamelCaseMap>
     */
    @GetMapping("/ap63")
    public List<CamelCaseMap> getCdap63List(AppaiappryearendsDto aprydsDto){
    	return appaiappryearendsService.getCdap63(aprydsDto);
    }
    /**
     * 자기신고서 저장
     * @param AppaiappryearendsDto
     * @return int
     */
    @PostMapping
    public int saveAppaiappryearends(AppaiappryearendsDto aprydsDto) {
    	return appaiappryearendsService.saveAppaiappryearends(aprydsDto);
    }

    /**
     * 자기신고서 전송
     * @param AppaiappryearendsDto
     * @return int
     */
    @PostMapping("/submit")
    public int modifySubmitStatus(AppaiappryearendsDto aprydsDto) {
    	return appaiappryearendsService.modifySubmitStatus(aprydsDto);
    }
}

