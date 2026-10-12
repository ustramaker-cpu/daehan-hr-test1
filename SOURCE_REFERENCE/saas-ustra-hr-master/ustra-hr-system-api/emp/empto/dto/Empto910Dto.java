package hr.system.api.emp.empto.dto;

import hr.system.api.apr.dto.AprvdocmstrDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Empto910Dto extends AprvdocmstrDto {

    private String docNo;            // 문서번호
    private String emplNo;           // 사번
    private String reqHeadqOrgCode;  // 신청본부코드
    private String reqOrgCode;       // 신청부서코드
    private String increaseDept;     // 증원부서
    private String decreaseDept;     // 감원부서
    private String transCnt;         // 인원
    private String transReason;      // 이관사유
    private String remark;           // 비고

}
