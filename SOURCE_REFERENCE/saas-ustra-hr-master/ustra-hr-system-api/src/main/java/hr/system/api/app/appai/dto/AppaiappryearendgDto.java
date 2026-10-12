package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppaiappryearendgDto extends BaseDto {

    private String emplNo;        // 평가대상자사번
    private String apprYear;      // 평가년도
    private String apprSeqNo;     // 평가차수
    private String typeCodeAppr;  // 평가형태코드
    private String orgCodeFactor; // 평가조직코드
    private String capaCodeName; //  PE역량 평가
    
    private String taskSeqNo;     // 과업순서
    private String capaInd;       // 지표구분
    private String bizInd;        // 업무구분
    private String taskName;      // 과업명(단위업무)
    private String taskKind;      //

    private String taskGole;      // 평가요소
    private String carryEffort;   // 추친노력
    private String carryResultDtl;// 주요성과
    private String weight;        // 비중

    private String achGole;       // 달성목표
    private String achieveInd;    // 달성도

    private String orgCode;
    private String userId;

    private String execInd;       // 전송여부
    private String appraiserSeqNo;
    private String appraiserEmplNo;

    private List<AppaiappryearendgDto> appaiappryearendgList;
}
