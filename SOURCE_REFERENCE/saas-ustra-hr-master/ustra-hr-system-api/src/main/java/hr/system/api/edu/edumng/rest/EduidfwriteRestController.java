package hr.system.api.edu.edumng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfwriteDto;
import hr.system.api.edu.edumng.service.EduidfwriteService;
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
@RequestMapping("/edu/edumng/eduidfwrite")
@RequiredArgsConstructor
public class EduidfwriteRestController {

     private final EduidfwriteService eduidfwriteService;

     /**
      * 자기개발계획수립 조회
      * @return List<CamelCaseMap>
      */
     @GetMapping
     public List<CamelCaseMap> findEduidfwriteList(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.findEduidfwriteList(eduidfwriteDto);
     }

     /**
      * 자기개발계획상태 조회
      * @return List<CamelCaseMap>
      */
     @GetMapping("/check-status")
     public List<CamelCaseMap> checkPlanStatus(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.checkPlanStatus(eduidfwriteDto);
     }

     /**
      * 직무별 교육과정조회 팝업
      * @return List<CamelCaseMap>
      */
     @GetMapping("/pop/edcourse")
     public List<CamelCaseMap> findEdcoursePopList(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.findEdcoursePopList(eduidfwriteDto);
     }

     /**
      * 교육과정조회 전체 팝업
      * @return List<CamelCaseMap>
      */
     @GetMapping("/pop/edcourseall")
     public List<CamelCaseMap> findEdcourseAllPopList(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.findEdcourseAllPopList(eduidfwriteDto);
     }

     /**
      * 직무마스터 조회
      * @return List<CamelCaseMap>
      */
     @GetMapping("/cpjobmaster")
     public List<CamelCaseMap> findCpJobMasterList(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.findCpJobMasterList(eduidfwriteDto);
     }

     /**
      * 역량관리 조회
      * @return List<CamelCaseMap>
      */
     @GetMapping("/initcppgdesc")
     public List<CamelCaseMap> findInitCpPgdesc(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.findInitCpPgdesc(eduidfwriteDto);
     }

     /**
      * 직무에 따른 역량관리 조회
      * @return List<CamelCaseMap>
      */
     @GetMapping("/cppgdesc")
     public List<CamelCaseMap> findCpPgdescList(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.findCpPgdescList(eduidfwriteDto);
     }

     /**
      * 자기개발계획수립 등록
      * @param eduidfwriteDto
      * @return int
      */
     @PostMapping
     public int insertEduidfwrite(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.insertEduidfwrite(eduidfwriteDto);
     }

     /**
      * 자기개발계획수립 수정
      * @param eduidfwriteDto
      * @return int
      */
     @PutMapping
     public int updateEduidfwrite(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.updateEduidfwrite(eduidfwriteDto);
     }

     /**
      * 자기개발계획수립 삭제
      * @param eduidfwriteDto
      * @return int
      */
     @DeleteMapping
     public int deleteEduidfwrite(EduidfwriteDto eduidfwriteDto) {
          return eduidfwriteService.deleteEduidfwrite(eduidfwriteDto);
     }


}

