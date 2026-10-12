package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppaiappryearendyDto extends BaseDto {

    private String emplNo;        // 평가대상자사번
    private String apprYear;      // 평가년도
    private String apprSeqNo;     // 평가차수
    private String typeCodeAppr;  // 평가형태코드
    private String orgCodeFactor; // 평가조직코드
    private String factorCodeAppr;// 평가구분
    private String taskSeqNo;     // 과업순서
    private String capaInd;       // 지표구분
    private String bizInd;        // 업무구분
    private String taskName;      // 과업명(단위업무)
    private String taskGole;      // 업무목표
    private String achGole;       // 달성목표
    private String weight;        // 비중
    private String achieveInd;    // 달성도
    private String carryResult;   // 실적
    private String carryResultDtl;   // 실적상세
    private String carryEffort;   // 추진노력
    private String apprserOpn;    // 평정자의견
    private String talkOpn;       // 종합의견
    private String talkOpn1;       // 종합의견
    private String talkOpn2;       // 종합의견
    private String makerCode;     // 작성자구분코드


    private String orgCode;
    private String userId;
    private String loginUserId;

    private String execInd;       // 전송여부
    private String appraiserSeqNo;
    private String appraiserEmplNo;
    private String closeIndAppraiser; // 평가자마감여부

    private String codeNameHan;  //공통역량
    private String capaCodeKind; //역량코드
    private String capaName;    //역량명
    private String capaDef;     //착안사항..
    private String taskKind;     //평가 세부항목
    private String appraiserPoint1st; //1차평가
    private String appraiserPoint2nd; //2차평가
    private String appraiserPoint3nd; //3차평가
    private Float appraiserPoint1stPoint; //1차평가
    private Float appraiserPoint2ndPoint; //2차평가

    private String apprGroupCode;
    private String levelCode;
    private String compCode;
    private String timeCode;
    private Float levelPoint;
    private Float compPoint;
    private Float timePoint;
    private Float calPoint;
    private Float sumPoint;
    private String capaCode;

    private String ceFactorCodeAppr;
    private String peFactorCodeAppr;
    private Float ceWeight;
    private Float peWeight;

    private Float capaPoint1;
    private Float achievePoint1;

    private Float capaPoint1Bef;
    private Float achievePoint1Bef;

    private Float capaPoint2;
    private Float achievePoint2;
    private Float capaPoint2Bef;
    private Float achievePoint2Bef;
    private Float totalPoint;

    private String orgNameHan;
    private String titleNameHan;
    private String emplNameHan;

    private List<AppaiappryearendyDto> appaiappryearendyList;
}
