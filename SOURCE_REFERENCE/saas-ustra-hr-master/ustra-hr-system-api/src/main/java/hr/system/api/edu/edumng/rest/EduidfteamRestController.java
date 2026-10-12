package hr.system.api.edu.edumng.rest;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfmngDto;
import hr.system.api.edu.edumng.service.EduidfteamService;
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
 * 2021.07.01  gynam    최초 생성
 * </pre>
 * @since 2021.07.01
 */
@RestController
@RequestMapping("/edu/edumng/eduidfteam")
@RequiredArgsConstructor
public class EduidfteamRestController {

     private final EduidfteamService eduidfteamService;

     /**
      * 자기개발계획관리 조회
      * @param eduidfmngDto
      * @return List<CamelCaseMap>
      */
     @GetMapping
     public List<CamelCaseMap> findEduidfteamList(EduidfmngDto eduidfmngDto){
          return eduidfteamService.findEduidfteamList(eduidfmngDto);
     }

     /**
      * 자기개발계획수립 계획 다운로드
      * @return List<CamelCaseMap>
      */
     @GetMapping("/excelall")
     public List<CamelCaseMap> findEduidfExcelAllDownList(EduidfmngDto eduidfmngDto) {
          return eduidfteamService.findEduidfExcelAllDownList(eduidfmngDto);
     }

     /**
      * 자기개발계획관리 상태 수정 (전송)
      * @param eduidfmngDto
      * @return int
      */
     @PostMapping("/status")
     public int updateStatusCode(EduidfmngDto eduidfmngDto) {
          return eduidfteamService.updateStatusCode(eduidfmngDto.getEduidfmngList());
     }

}

