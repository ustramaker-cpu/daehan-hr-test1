package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppaiappryearendmDto extends BaseDto {

    private String emplNo;          // 평가대상자사번
    private String apprYear;        // 평가년도
    private String apprSeqNo;       // 평가차수
    private String typeCodeAppr;    // 평가형태코드
    private String orgCodeFactor;   // 평가조직코드
    private String capaCode;        // 역량코드
    private String appraiserEmplNo; // 평가자사번
    private String levelCode;       // 다면평가기준코드
    private String levelPoint;      // 다면평가점수
    private String weight;          // 비중
    private String calPoint;        // 계산점수
    
    // param 값
    private String orgCode;
    private String userId;
    private String execInd;
    private String gradeKindAppr;

    private List<AppaiappryearendmDto> appaiappryearendmList;
}
