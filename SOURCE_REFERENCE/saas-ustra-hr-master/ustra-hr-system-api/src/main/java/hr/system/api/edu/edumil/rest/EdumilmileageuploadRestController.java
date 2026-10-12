package hr.system.api.edu.edumil.rest;

import hr.system.api.edu.edumil.dto.EdumilmileageDto;
import hr.system.api.edu.edumil.service.EdumilmileageuploadService;
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
@RequestMapping("/edu/edumil/edumilmileageupload")
@RequiredArgsConstructor
public class EdumilmileageuploadRestController {

     private final EdumilmileageuploadService edumilmileageuploadService;

     /**
      * 마일리지 업로드 등록전 데이터 체크..
      *
      * @param List<EdumngedursltDto>
      * @return int
      */
     @PostMapping("/checkbackData")
     public int checkbackData(EdumilmileageDto edumilmileageDto){
          return edumilmileageuploadService.checkbackData(edumilmileageDto.getEdumilmileageDtoList());
     }

     /**
      * 마일리지 업로드 등록
      *
      * @param List<EdumngedursltDto>
      * @return int
      */
     @PostMapping("/save")
     public int saveedumilmileageupload(EdumilmileageDto edumilmileageDto){
          return edumilmileageuploadService.saveedumilmileageupload(edumilmileageDto.getEdumilmileageDtoList());
     }
}

