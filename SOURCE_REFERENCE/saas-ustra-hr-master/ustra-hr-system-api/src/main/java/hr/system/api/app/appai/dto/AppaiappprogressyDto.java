package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppaiappprogressyDto extends BaseDto {
    
    private String emplNo;            // 평가대상자사번
    private String apprYear;          // 평가년도
    private String apprSeqNo;         // 평가차수
    private String typeCodeAppr;      // 평가형태코드
    private String orgCodeFactor;     // 평가조직코드
    private String basisDateAppr;     // 평가기준일자
    private String apprGroupCode;     // 평가그룹코드 부서장/티원
    private String apprGrouping;      // 평가그룹핑  팀장/실장/임직원
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
    private String appraiserExecInd1st; //1차 전송여부
    private String appraiserExecInd2nd; //2차 전송여부

    private String appraiserEmplNo1st;  //1차평가자 사번
    private String appraiserEmplNo2nd;  //2차 전송여부
//    private String appraiserSeqNo1st;   //1차 전송여부
//    private String appraiserSeqNo2nd;   //2차 전송여부

    private String cePoint;             //역량평가
    private String pePoint;             //성과평가
    private String levelCode1;          //1차등급
    private String levelCode1Aft;       //1차등급추천
    private String levelCode1AftEtc;    //1차등급추천코멘트

    private String levelCode2;          //2차등급
    private String levelCode2Aft;       //2차등급추천
    private String levelCode2AftEtc;    //2차등급추천코멘트

    private String orgCode;
    private String openSelect;  // 오픈여부 (조회조건)
    private String closeSelect; // 평가마감여부 (조회조건)
    private String seqSelect;   // 평가자차수 (조회조건)
    private String actEmplNo;   // 평가자 (조회조건)
    private String emplNameHan;
    private String positionNameHan;
    private String loginUserId; // 로그인한 사번(조회조건) - 피평가자인지 구분(관리자페이지에서)
    private Integer workingDay;

    private String appraiserInd; //조회 평가차수
    private String apprGroupingCode; //평가 그룹핑
    private String totAllotPoint; //1차 학원 배분 총점

    private List<AppaiappprogressyDto> appaiappprogressyList;
}
