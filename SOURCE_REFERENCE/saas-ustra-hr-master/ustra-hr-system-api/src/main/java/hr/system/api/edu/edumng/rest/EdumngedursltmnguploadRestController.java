package hr.system.api.edu.edumng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import hr.system.api.edu.edumng.service.EdumngedursltmnguploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
@RequestMapping("/edu/edumng/edumngedursltmngupload")
@RequiredArgsConstructor
public class EdumngedursltmnguploadRestController {

     private final EdumngedursltmnguploadService edumngedursltmnguploadService;

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
     public int saveedumngedursltmngupload(EdumngedursltDto edumngedursltDto){
          return edumngedursltmnguploadService.saveedumngedursltmngupload(edumngedursltDto.getEdumngedursltDtoList());
     }

     @GetMapping("/edcourse")
     public List<CamelCaseMap> findEdcourseList(EdumngedursltDto edumngedursltDto) {
          return edumngedursltmnguploadService.findEdcourseList(edumngedursltDto);
     }

     @PostMapping("/checkbackData")
     public int checkbackData(EdumngedursltDto edumngedursltDto) {
          return edumngedursltmnguploadService.checkbackData(edumngedursltDto.getEdumngedursltDtoList());
     }

}

