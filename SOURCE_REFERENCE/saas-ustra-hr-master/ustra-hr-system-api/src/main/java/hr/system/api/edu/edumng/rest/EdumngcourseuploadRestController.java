package hr.system.api.edu.edumng.rest;

import hr.system.api.edu.edumng.dto.EdumngcourseDto;
import hr.system.api.edu.edumng.service.EdumngcourseuploadService;
import hr.system.core.emp.empas.dto.AsrecomndDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
@RequestMapping("/edu/edumng/edumngcourseupload")
@RequiredArgsConstructor
public class EdumngcourseuploadRestController {

     private final EdumngcourseuploadService edumngcourseuploadService;

     /**
      * 교육과정 업로드 등록
      *
      * @param List<EdumngcourseDto>
      * @return int
      */
     @PostMapping("/save")
     public int saveEdumngcourseupload(EdumngcourseDto edumngcourseDto){
          return edumngcourseuploadService.saveEdumngcourseupload(edumngcourseDto.getEdumngcourseList());
     }

}

