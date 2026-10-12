package hr.system.api.app.appai.rest;

import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.dto.AppaiappobjlistmDto;
import hr.system.api.app.appai.service.AppaiappobjlistguploadService;
import hr.system.api.app.appai.service.AppaiappobjlistmuploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/app/appai/appaiappobjlistmupload")
@RequiredArgsConstructor
public class AppaiappobjlistmuploadRestController {

    private final AppaiappobjlistmuploadService appaiappobjlistmuploadService;

    /**
     * 다면평가 평가자 엑셀업로드
     *
     * @param appaiappobjlistmDto
     * @return int
     */
    @PostMapping("/save")
    public int saveAppaiappobjlistmUpload(AppaiappobjlistmDto appaiappobjlistmDto){
        return appaiappobjlistmuploadService.saveAppaiappobjlistmUpload(appaiappobjlistmDto.getAppaiappobjlistmList());
    }

}

