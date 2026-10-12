package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppaiappryearendpDto extends BaseDto {

    private String emplNo;          // 평가대상자사번
    private String apprYear;        // 평가년도
    private String apprSeqNo;       // 평가차수
    private String typeCodeAppr;    // 평가형태코드
    private String orgCodeFactor;   // 평가조직코드
    private String taskSeqNo;       // 과업순서
    private String capaInd;         // 지표구분
    private String bizInd;          // 업무구분
    private String taskName;        // 과업명(단위업무)
    private String taskGole;        // 업무목표
    private String achGole;         // 달성목표 -> 본인평가
    private String weight;          // 비중
    private String achieveInd;      // 달성도 -> 가중점수
    private String carryResult;     // 실적
    private String carryResultDtl;  // 추진결과 및 성과
    private String carryEffort;     // 추진노력
    private String apprserOpn;      // 평정자의견
    private String talkOpn;         // 종합의견
    private String makerCode;       // 작성자구분코드
    private String achGole1st;      // 1차평가등급
    private String achieveInd1st;   // 1차평가가중점수

    private String orgCode;
    private String userId;
    private String loginUserId;

    private String execInd;         // 전송여부
    private String appraiserSeqNo;
    private String appraiserEmplNo;
    private String appraiserEmplNameHan;
    private String appraiserOrgNameHan;
    private String appraiserPositionNameHan;
    private String closeIndAppraiser; // 평가자마감여부

    private String levelCode1;
    private String levelCode1Aft;
    private String levelCode1AftEtc;

    private List<AppaiappryearendpDto> appaiappryearendpList;
}
