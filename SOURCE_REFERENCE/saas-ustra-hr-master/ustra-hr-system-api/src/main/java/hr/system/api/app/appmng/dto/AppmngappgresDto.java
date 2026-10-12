package hr.system.api.app.appmng.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppmngappgresDto extends BaseDto {

    private String apprYear;        // 평가년도
    private String apprYearOrg;     // 평가년도
    private String apprSeqNo;       // 평가차수
    private String orgCodeFactor;    // 관리조직코드
    private String factorCodeAppr;  // 평가요소코드
    private String apprGroupCode;   // 평가그룹코드
    private String orgCodeFactorOrg;    // 평가형태코드
    private String factorCodeApprOrg;  // 평가요소코드
    private String apprGroupCodeOrg;   // 평가그룹코드
    //private String capaCodeKind;
    //private String capaCodeKindOrg;
    private String resCode;
    private String resCodeOrg;
    private String resName;
    private String resDef;
    private String weight;          // 비중
    private String gradeCodeAppr; // 평가등급코드

    private String baseYear;

    private List<AppmngappgresDto> appmngappgresDtoList; //성과관리
}
