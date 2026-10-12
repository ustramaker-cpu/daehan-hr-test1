package hr.system.api.apr.dto         ;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AprvlinesetDto extends BaseDto { // 결재선설정
    private String formId                   ; // 문서유형
    private String emplNo                   ; // 사번
    private String docNo                    ; // 문서번호

    private List<AprvlinesetDto> aprvlinesetDtoList; // list

}
