package hr.system.api.apr.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AprvtransferDto extends BaseDto {

    // aprvtransfer (대결자 조회)
    private String coCode;          // 회사구분코드
    private String emplNo;          // 사번
    private String orgCode;         // 조직코드
    private String transDateFrom;   // 대리결재 시작일시
    private String transTimeFrom;
    private String transDateTo;     // 대리결재 종료일시
    private String transTimeTo;
    private String transEmplNo;     // 대리결재자 사번
    private String transInd;        // 대리결재 여부
    private String remark;          // 비고
    
    private String schEmplNo;
    private String schTransInd;
    private String schIndCode;
    
    private String isSuper;
    
    private String emplNameHan;
    private String orgNameHan;
    private String transEmplNameHan;
    private String transDateFromOld;

    private List<AprvtransferDto> dataList;
}
