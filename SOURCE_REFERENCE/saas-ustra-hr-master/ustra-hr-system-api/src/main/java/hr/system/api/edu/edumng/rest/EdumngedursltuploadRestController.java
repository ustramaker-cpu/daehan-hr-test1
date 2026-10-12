package hr.system.api.edu.edumng.rest;

import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import hr.system.api.edu.edumng.service.EdumngedursltuploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping("/edu/edumng/edumngedursltupload")
@RequiredArgsConstructor
public class EdumngedursltuploadRestController {

     private final EdumngedursltuploadService edumngedursltuploadService;

     //@InitBinder
     public void initBinder(WebDataBinder binder) {
          binder.setAutoGrowCollectionLimit(100000);
     }

     /**
      * 교육과정 업로드 등록
      *
      * @param List<EdumngedursltDto>
      * @return int
      */
     @PostMapping("/save")
     public int saveedumngedursltupload(EdumngedursltDto edumngedursltDto){
          return edumngedursltuploadService.saveedumngedursltupload(edumngedursltDto.getEdumngedursltDtoList());
     }

}

