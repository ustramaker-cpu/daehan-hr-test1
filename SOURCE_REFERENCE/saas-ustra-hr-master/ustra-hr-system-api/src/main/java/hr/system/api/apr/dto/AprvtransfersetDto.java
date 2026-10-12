package hr.system.api.apr.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AprvtransfersetDto extends BaseDto { // 결재이양 설정
    
    private String emplNo;         // 사번
    
    private String orgCode;        // 조직코드

    private String transEmplNo;    // 대리결재자 사번

    private String transInd;       // 대리결재 여부

    private String transDateFrom;  // 대리결재 시작일

    private String transDateTo;    // 대리결재 종료일

    private String remark;         // 비고

    private String emplNoSch;      // 조회 param

    private List<AprvtransfersetDto> aprvtransfersetDtoList; // list
}
