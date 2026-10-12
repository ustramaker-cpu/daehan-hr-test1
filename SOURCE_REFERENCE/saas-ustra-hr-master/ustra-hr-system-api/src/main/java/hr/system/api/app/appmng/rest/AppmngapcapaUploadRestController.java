package hr.system.api.app.appmng.rest;

import hr.system.api.app.appmng.dto.AppmngapcapaDto;
import hr.system.api.app.appmng.service.AppmngapcapaService;
import lombok.RequiredArgsConstructor;
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
@RestController
@RequestMapping("/app/appmng/appmngapcapaupload")
@RequiredArgsConstructor
public class AppmngapcapaUploadRestController {

     private final AppmngapcapaService appmngapcapaService;

     /**
      * 단위업무 업로드 등록
      *
      * @param
      * @return int
      */
     @PostMapping("/save")
     public int saveappmngappgcapaupload(AppmngapcapaDto appmngapcapaDto){
          return appmngapcapaService.saveappmngapcapaupload(appmngapcapaDto, appmngapcapaDto.getAppmngapcapaDtoList());
     }
}

