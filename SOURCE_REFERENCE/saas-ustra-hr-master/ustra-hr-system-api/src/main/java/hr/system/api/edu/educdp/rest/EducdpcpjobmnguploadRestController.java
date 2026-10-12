package hr.system.api.edu.educdp.rest;

import hr.system.api.edu.educdp.dto.EducdpcpjobmngDto;
import hr.system.api.edu.educdp.service.EducdpcpjobmnguploadService;
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
@RequestMapping("/edu/educdp/educdpcpjobmngupload")
@RequiredArgsConstructor
public class EducdpcpjobmnguploadRestController {

     private final EducdpcpjobmnguploadService educdpjobmnguploadService;

     /**
      * 교육과정 업로드 등록
      *
      * @param List<EducdpjobmngDto>
      * @return int
      */
     @PostMapping("/save")
     public int saveeducdpjobmngupload(EducdpcpjobmngDto educdpcpjobmngDto){
          return educdpjobmnguploadService.saveeducdpcpjobmngupload(educdpcpjobmngDto.getEducdpcpjobmngDtoList());
     }

}

