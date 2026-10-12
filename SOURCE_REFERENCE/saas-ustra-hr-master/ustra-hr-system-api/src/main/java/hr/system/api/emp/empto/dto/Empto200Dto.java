package hr.system.api.emp.empto.dto;

import hr.system.api.apr.dto.AprvdocmstrDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Empto200Dto extends AprvdocmstrDto {

    private String coCode;
    private String docNo;
    private int apprYear;
    private String orgCode;
    private String createEmplNo;
    private String reqDate;
    private String hireTypeCode;
    private String gradeCodeKind;
    private int befTo;
    private int curTo;
    private int planTo;
    private int calTo;
    private int befEmplCnt9;
    private int befEmplCnt10;
    private int befEmplCnt11;
    private int befEmplCnt12;
    private int emplCnt1;
    private int emplCnt2;
    private int emplCnt3;
    private int emplCnt4;
    private int emplCnt5;
    private int emplCnt6;
    private int emplCnt7;
    private int emplCnt8;
    private int emplCnt9;
    private int emplCnt10;
    private int emplCnt11;
    private int emplCnt12;
    private String remark;
    private String effDateFrom;
    private String effDateTo;

    private String docTitlNm;        // 문서명
    private String createEmplName;   // 작성자 성명
    private String formId;           // FORM_ID(신청서)
    private String pgmId;            // 프로그램ID
    private String gwDocNo;          // GW문서번호(I/F)
    private String createDate;       // 문서생성일자
    private String reqstDate;        // 결재요청일자
    private String apprvDate;        // 결재완료일자
    private String statusCode;       // 결재상태코드
    private String mblPgmId;         // 모바일프로그램ID

    private List<Empto200Dto> empto200DtoList;
}
