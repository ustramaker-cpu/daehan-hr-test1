package hr.system.api.apr.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AprvformDto extends BaseDto {         // 신청서정보
    private String formId;         // 문서양식번호

    private String effDateFrom;    // 유효시작일자

    private String effDateTo;      // 유효종료일자

    private String formName;       // 양식명

    private String pgmId;          // 데스크탑 Program ID

    private String mblPgmId;       // 모바일 Program ID
    
    private String formFileName;   // 양식파일명

    private String aprvDepth;      // 결재DEPTH
    
    private String classCode;      // 분류
    
    private String baseDate;       // 기준일자

    private String depthYn;        // DEPTH 사용여부

    private List<AprvformDto> aprvformDtoList; // list
}
