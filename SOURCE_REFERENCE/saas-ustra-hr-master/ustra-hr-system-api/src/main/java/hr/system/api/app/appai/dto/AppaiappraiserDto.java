package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppaiappraiserDto extends BaseDto {

    private String coCode;              // 회사코드
    private String apprYear;            // 평가년도
    private String apprSeqNo;           // 평가차수
    private String typeCodeAppr;        // 평가형태코드
    private String emplNo;              // 평가대상자사번
    private String apprGroupCode;       // 평가그룹코드
    private String orgCodeFactor;       // 평가조직코드
    private String taskCode;            // 평가형태
    private String appraiserEmplNo;     // 평가자사번
    private String appraiserEmplNameHan;    // 평가자이름
    private String appraiserOrgNameHan; //평가자조직명
    private String appraiserSeqNo;      // 평가자차수
    private String execInd;             // 평가여부
    private String note;                // 비고

    private Integer returnCode;         // 프로시저 리턴코드
    private String returnMsg;           // 프로시저 리턴메세지

    private List<AppaiappraiserDto> appaiappraiserList;

}
