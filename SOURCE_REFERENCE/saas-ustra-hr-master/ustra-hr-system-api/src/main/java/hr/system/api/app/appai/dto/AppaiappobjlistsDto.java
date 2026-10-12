package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class AppaiappobjlistsDto extends BaseDto {

    private String emplNo;            // 평가대상자사번
    private String apprYear;          // 평가년도
    private String apprSeqNo;         // 평가차수
    private String typeCodeAppr;      // 평가형태코드
    private String orgCodeFactor;     // 평가조직코드
    private String basisDateAppr;     // 평가기준일자
    private String apprGroupCode;     // 평가그룹코드
    private String confmInd;          // 확정여부
    private String sendInd;           // 전송여부
    private String confmIndSend;      // 확인여부
    private String openInd;           // 오픈여부
    private String closeIndAppraiser; // 평가자마감여부
    private String closeInd;          // 평가마감여부
    private String compltIndRelgrade; // 상대등급완료여부
    private String closeIndLast;      // 최종마감여부
    private String remark;            // 비고

    private String factorCodeAppr;    // 평가요소코드
    private String appraiserEmplNo;   // 평가자사번
    private String appraiserSeqNo;    // 평가자차수구분
    private String execInd;           // 평가실시여부

    private String orgCode;
    private String confirmSelect;
    private String submitSelect;
    private String seqSelect; // 평가자차수 (조회조건)
    private String actEmplNo; // 평가자 (조회조건)
    private String emplNameHan;
    private String seqOne; // 1차
    private String seqTwo; // 2차
    private String appraiserEmplNo1st; // 1차 평가자
    private String appraiserEmplNo2nd; // 2차 평가자
    private String orgCodeFactorNew;   // 평가조직코드

    private String baseYear;
    private String gradeCodes;

    private Integer returnCode;         // 프로시저 리턴코드
    private String returnMsg;          // 프로시저 리턴메세지

    private List<AppaiappobjlistsDto> appaiappobjlistsList;
}
