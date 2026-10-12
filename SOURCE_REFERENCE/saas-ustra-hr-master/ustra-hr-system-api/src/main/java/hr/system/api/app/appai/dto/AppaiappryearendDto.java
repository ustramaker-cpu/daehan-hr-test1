package hr.system.api.app.appai.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
/**
 *
 * */
public class AppaiappryearendDto extends BaseDto {

    private String emplNo;          // 평가대상자사번
    private String apprYear;        // 평가년도
    private String apprSeqNo;       // 평가차수
    private String typeCodeAppr;    // 평가형태코드
    private String orgCodeFactor;   // 평가조직코드
    private String factorCodeAppr;  // 평가구분(PE 성과,CE 역량)
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
    private String capaCodeKind;    // 평가등급코드
    private String capaCode;
    private String taskCode;

    private String point;
    private String point0;          // 본인평가점수
    private double pointCvt0;       // 본인평가 가중점수
    private String point1;          // 1차평가점수
    private double pointCvt1;       // 1차평가 가중점수
    private String comment1;        // 1차평가코멘트
    private String peNote0;         // 성과평가의견(본인)
    private String peNote1;         // 성과평가의견(1차평가자)
    private String ceNote0;         // 역량평가의견(본인)
    private String ceNote1;         // 역량평가의견(1차평가자)
    private String appraiserEmplNo0;
    private String appraiserEmplNo1;

    private String orgCode;
    private String userId;
    private String loginUserId;

    private String calPoint;
    private String levelCode;
    private String levelPoint;

    private String levelCode0st;
    private String calPoint0st;

    private String levelCode1st;
    private String calPoint1st;

    private String execInd;         // 전송여부
    private String appraiserSeqNo;  // 평가차수
    private String apprCheckYn;
    private String comment;         // 코멘트
    private String appraiserEmplNo;
    private String appraiserEmplNameHan;
    private String appraiserOrgNameHan;
    private String appraiserPositionNameHan;
    private String closeIndAppraiser; // 평가자마감여부
    private String apprGroupCode;       // 평가그룹
    private double pePoint;         // 성과평가점수
    private double cePoint;         // 역량평가점수
    private String note;
    private String ceNote;
    private String peNote;
    private String capaDefAf;
    private String capaSeqNo;

    private String levelCode1;
    private String levelCode1Aft;
    private String levelCode1AftEtc;

    private List<AppaiappryearendDto> appaiappryearendList;


}
