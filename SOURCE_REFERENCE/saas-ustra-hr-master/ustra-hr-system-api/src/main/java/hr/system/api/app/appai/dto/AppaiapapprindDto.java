package hr.system.api.app.appai.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppaiapapprindDto extends BaseDto {

    // 조회조건
    private String searchAppraiserEmplNo;   // 평가자사번- 대상자 리스트 조회

    // APAPPRIND
    /** 평가년도 */
    private String apprYear;
    /** 평가차수 */
    private String apprSeqNo;
    /** 평가형태코드 */
    private String typeCodeAppr;
    private String searchTypeCodeAppr;
    /** 평가그룹코드 */
    private String apprGroupCode;
    /** 평가조직코드 */
    private String orgCodeFactor;
    /** 평가대상자별 그룹핑 */
    private String apprGrouping;
    /** 평가대상자 사번 */
    private String emplNo;
    /** 확정여부 */
    private String confmInd;
    /** 오픈여부 */
    private String openInd;
    /** 전송여부 */
    private String sendInd;
    /** 평가마감여부 */
    private String closeInd;
    /** 최종마감여부 */
    private String closeIndLast;

    // APPRAISER
    /** 1차 평가자 */
    private String appraiserEmplNo1;
    /** 2차 평가자 */
    private String appraiserEmplNo2;
    /** 3차 평가자 */
    private String appraiserEmplNo3;

    private String confmIndSend;      // 확인여부
    private String remark;            // 비고
    private String openSelect;        // 오픈select
    private String closeSelect;       // 마감select
    private String positionNameHan;   // 직위명

    private String factorCodeAppr;    // 평가요소코드
    private String appraiserEmplNo;   // 평가자사번
    private String appraiserSeqNo;    // 평가자차수구분
    private String execInd;           // 평가실시여부

    private String appraiserEmplNo1st; // 1차 평가자
    private String appraiserEmplNo2nd; // 2차 평가자
    private String appraiserEmplNo3nd; // 3차 평가자

    private String orgCode;
    private String confirmSelect;
    private String confirmInd;
    private String submitSelect;
    private String seqSelect; // 평가자차수 (조회조건)
    private String actEmplNo; // 평가자 (조회조건)
    private String emplNameHan;
    private String seqOne; // 1차
    private String seqTwo; // 2차

    private String orgCodeFactorNew;   // 평가조직코드

    private String baseYear;
    private String gradeCodes;
    private String baseDate;            //평가생성기준일

    private Integer returnCode;         // 프로시저 리턴코드
    private String returnMsg;          // 프로시저 리턴메세지

    private List<Integer> seqnoList;    // 평가자 seqnoList
    private List<AppaiapapprindDto> appaiapapprindList;

}
