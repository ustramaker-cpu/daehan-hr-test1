package hr.system.api.edu.edumng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfwriteDto;
import hr.system.api.edu.edumng.service.EduidfwriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
 * 2021.07.12  gynam    최초 생성
 * </pre>
 * @since 2021.07.12
 */
@RestController
@RequestMapping("/edu/edumng/edcourse01-pop")
@RequiredArgsConstructor
public class Edcourse01PopRestController {

     private final EduidfwriteService eduidfwriteService;

     /**
      * 교육과정조회 전체 팝업
      * @return List<CamelCaseMap>
      */
     @GetMapping("/edcourseall")
     public List<CamelCaseMap> findEdcourseAllPopList(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.findEdcourseAllPopList(eduidfwriteDto);
     }

}

