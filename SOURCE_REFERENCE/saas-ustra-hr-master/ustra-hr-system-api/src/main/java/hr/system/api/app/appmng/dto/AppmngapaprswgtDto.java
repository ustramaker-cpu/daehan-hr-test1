package hr.system.api.app.appmng.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppmngapaprswgtDto extends BaseDto {
    
    private String apprYear;           // 평가년도
    private String typeCodeAppr;       // 평가형태
    private String appraiserSeqNoLast; // 최종평가차수
    private String appraiserSeqNo;     // 평가자차수
    private String apprSeqNo;          // 평가차수 - 일단 1
    private String weight;             // 비중
    private String remark;             // 비고
    private String codeNameHan;        // 평가형태명

    private List<AppmngapaprswgtDto> appmngapaprswgtList;
}
