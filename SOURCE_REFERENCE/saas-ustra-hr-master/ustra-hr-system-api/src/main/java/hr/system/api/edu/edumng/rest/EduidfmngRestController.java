package hr.system.api.edu.edumng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfmngDto;
import hr.system.api.edu.edumng.service.EduidfmngService;
import lombok.RequiredArgsConstructor;
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
 * 2021.07.02  gynam    최초 생성
 * </pre>
 * @since 2021.07.02
 */
@RestController
@RequestMapping("/edu/edumng/eduidfmng")
@RequiredArgsConstructor
public class EduidfmngRestController {

     private final EduidfmngService eduidfmngService;

     /**
      * 부서별 자기개발계획(담당자) 조회
      * @param eduidfmngDto
      * @return List<CamelCaseMap>
      */
     @GetMapping
     public List<CamelCaseMap> findEduidfmngList(EduidfmngDto eduidfmngDto){
          return eduidfmngService.findEduidfmngList(eduidfmngDto);
     }

     /**
      * 부서별 자기개발계획(담당자) 저장/추가/삭제
      * @param eduidfmngDto
      * @return int
      */
     @PostMapping
     public int saveEduidfmngList(EduidfmngDto eduidfmngDto) {
          return eduidfmngService.saveEduidfmngList(eduidfmngDto.getEduidfmngList());
     }

     /**
      * 부서별 자기개발계획(담당자) 대상자를 생성한다.
      *
      * @param eduidfmngDto
      * @return int
      */
     @PostMapping("/maketarget")
     public int saveNewtarget(EduidfmngDto eduidfmngDto) {
          return eduidfmngService.saveNewtarget(eduidfmngDto);
     }

     /**
      * 부서별 자기개발계획(담당자) 상태 수정 (전송)
      * @param eduidfmngDto
      * @return int
      */
     @PostMapping("/submit")
     public int updateStatusSubmit(EduidfmngDto eduidfmngDto) {
          return eduidfmngService.updateStatusCode(eduidfmngDto.getEduidfmngList(), "SO");
     }

     /**
      * 부서별 자기개발계획(담당자) 상태 수정 (확인)
      * @param eduidfmngDto
      * @return int
      */
     @PostMapping("/confirm")
     public int updateStatusConfirm(EduidfmngDto eduidfmngDto) {
          return eduidfmngService.updateStatusCode(eduidfmngDto.getEduidfmngList(), "CO");
     }

     /**
      * 부서별 자기개발계획(담당자) 상태 수정 (전송취소, 확인취소 - 대기)
      * @param eduidfmngDto
      * @return int
      */
     @PostMapping("/cancel")
     public int updateStatusCancel(EduidfmngDto eduidfmngDto) {
          return eduidfmngService.updateStatusCode(eduidfmngDto.getEduidfmngList(), "CA");
     }

}

