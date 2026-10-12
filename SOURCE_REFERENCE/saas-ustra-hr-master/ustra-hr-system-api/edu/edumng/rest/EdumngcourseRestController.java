package hr.system.api.edu.edumng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngcourseDto;
import hr.system.api.edu.edumng.service.EdumngcourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
 * 2021.06.28  gynam    최초 생성
 * </pre>
 * @since 2021.06.28
 */
@RestController
@RequestMapping("/edu/edumng/edumngcourse")
@RequiredArgsConstructor
public class EdumngcourseRestController {

     private final EdumngcourseService edumngcourseService;

     /**
      * 교육과정관리 조회
      * @param edumngcourseDto
      * @return List<CamelCaseMap>
      */
     @GetMapping
     public List<CamelCaseMap> findEdumngcourseList(EdumngcourseDto edumngcourseDto){
          return edumngcourseService.findEdumngcourseList(edumngcourseDto);
     }

     /**
      * 교육과정관리 등록
      * @param edumngcourseDto
      * @return int
      */
     @PostMapping
     public int insertEdumngcourse(EdumngcourseDto edumngcourseDto) {
          return edumngcourseService.insertEdumngcourse(edumngcourseDto);
     }

     /**
      * 교육과정관리 수정
      * @param edumngcourseDto
      * @return int
      */
     @PutMapping
     public int updateEdumngcourse(EdumngcourseDto edumngcourseDto) {
          return edumngcourseService.updateEdumngcourse(edumngcourseDto);
     }

     /**
      * 교육과정관리 삭제
      * @param edumngcourseDto
      * @return int
      */
     @DeleteMapping
     public int deleteEdumngcourse(EdumngcourseDto edumngcourseDto) {
          return edumngcourseService.deleteEdumngcourse(edumngcourseDto);
     }

}

