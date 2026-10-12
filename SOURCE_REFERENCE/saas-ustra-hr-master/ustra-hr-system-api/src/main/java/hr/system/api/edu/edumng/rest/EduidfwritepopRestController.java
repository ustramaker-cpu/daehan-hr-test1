package hr.system.api.edu.edumng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfwriteDto;
import hr.system.api.edu.edumng.service.EduidfwritemngService;
import hr.system.api.edu.edumng.service.EduidfwritepopService;
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
 * 2021.07.12  gynam    최초 생성
 * </pre>
 * @since 2021.07.12
 */
@RestController
@RequestMapping("/edu/edumng/eduidfwrite-pop")
@RequiredArgsConstructor
public class EduidfwritepopRestController {

     private final EduidfwritepopService eduidfwritepopService;

     /**
      * 자기개발계획수립(팝업) 조회
      * @return List<CamelCaseMap>
      */
     @GetMapping
     public List<CamelCaseMap> findEduidfwritemngList(EduidfwriteDto eduidfwriteDto) {
          return eduidfwritepopService.findEduidfwritemngList(eduidfwriteDto);
     }

     /**
      * 자기개발계획수립(팝업) 등록
      * @param eduidfwriteDto
      * @return int
      */
     @PostMapping
     public int insertEduidfwritemng(EduidfwriteDto eduidfwriteDto) {
          return eduidfwritepopService.insertEduidfwritemng(eduidfwriteDto);
     }

     /**
      * 자기개발계획수립(팝업) 수정
      * @param eduidfwriteDto
      * @return int
      */
     @PutMapping
     public int updateEduidfwritemng(EduidfwriteDto eduidfwriteDto) {
          return eduidfwritepopService.updateEduidfwritemng(eduidfwriteDto);
     }

     /**
      * 자기개발계획수립(팝업) 삭제
      * @param eduidfwriteDto
      * @return int
      */
     @DeleteMapping
     public int deleteEduidfwritemng(EduidfwriteDto eduidfwriteDto) {
          return eduidfwritepopService.deleteEduidfwritemng(eduidfwriteDto);
     }

}

