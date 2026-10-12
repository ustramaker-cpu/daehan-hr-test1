package hr.system.api.app.appai.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.service.AppaiappobjlistgService;
import hr.system.api.app.appai.service.AppaiappobjlistguploadService;
import hr.system.api.edu.edumng.dto.EdumngcourseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappobjlistgupload")
@RequiredArgsConstructor
public class AppaiappobjlistguploadRestController {

    private final AppaiappobjlistguploadService appaiappobjlistguploadService;

    /**
     * 목표설정 대상자관리 업로드 등록
     *
     * @param appaiappobjlistgDto
     * @return int
     */
    @PostMapping("/save")
    public int saveAppaiappobjlistgUpload(AppaiappobjlistgDto appaiappobjlistgDto){
        return appaiappobjlistguploadService.saveAppaiappobjlistgUpload(appaiappobjlistgDto.getAppaiappobjlistgList());
    }

}

