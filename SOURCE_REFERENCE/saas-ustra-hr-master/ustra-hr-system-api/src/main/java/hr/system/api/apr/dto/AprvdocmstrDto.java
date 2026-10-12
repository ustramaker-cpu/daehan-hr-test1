package hr.system.api.apr.dto;

import hr.system.api.apr.common.AprvType;
import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AprvdocmstrDto extends BaseDto {  // 문서마스터

    private String coCode;            // 회사코드

    private String docNo;             // 문서번호

    private String docTitlNm;         // 문서명

    private String createEmplNo;      // 작성자 성명

    private String createEmplName;    // 작성자 사번

    private String formId;            // formId

    private String pgmId;             // 프로그램ID

    private String mblPgmId;          // 모바일 프로그램 ID

    private String aprvPathOrder;     // 결재 순서

    private String statusCode;        // 결재 상태 코드

    private String aprvCmnt;          // 결재 의견

    private String createDate;        // 문서 생성 일자

    private String reqstDate;         // 결재 요청 일자

    private String apprvDate;         // 결재 완료 일자

    private String apprvDateYN;       // 결재 완료 일자 여부

    private String gwDocNo;           // GW 문서번호 (I/F)

    private String bfDocNo;           // 재기안 전 기존 문서번호 

    private List<AprvdetailDto> aprvdetailDtoList; // list


    // 문서 회수 여부
    public boolean isCancel() {
        return this.statusCode != null && this.statusCode.equals(AprvType.APPROVAL_CNCL);
    }

    public boolean isRequest() {
        return this.statusCode != null && this.statusCode.equals(AprvType.APPROVAL_REQ);
    }

}
