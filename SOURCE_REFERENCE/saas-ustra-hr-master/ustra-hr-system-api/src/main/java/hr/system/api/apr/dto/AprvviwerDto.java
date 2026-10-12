package hr.system.api.apr.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AprvviwerDto extends BaseDto { // 문서디테일 (결재선 라인)

    private String docNo;        // 문서번호

    private String emplNo;       // 사번

}
