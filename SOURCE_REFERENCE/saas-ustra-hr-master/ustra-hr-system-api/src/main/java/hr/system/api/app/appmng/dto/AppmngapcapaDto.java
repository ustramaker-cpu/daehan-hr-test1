package hr.system.api.app.appmng.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppmngapcapaDto extends BaseDto {

    private String coCode;          // 회사코드
    private String apprYear;        // 평가년도
    private String apprSeqNo;       // 평가차수
    private String typeCodeAppr;    // 평가형태코드
    private String apprGroupCode;   // 평가그룹코드
    private String taskCode;        // 항목구분코드 ( PE:성과, CE:역량 )
    private String capaSeqNo;       // 역량 순번
    private String capaKind;        // 역량 분류
    private String capaName;        // 역량명
    private String capaDef;         // 역량정의
    private String weight;          // 비중

    private String baseYear;

    //private String apprYearOrg;     // 평가년도
    //private String orgCodeFactor;    // 관리조직코드
    //private String factorCodeAppr;  // 평가요소코드

    //private String orgCodeFactorOrg;    // 평가형태코드
    //private String factorCodeApprOrg;  // 평가요소코드
    //private String apprGroupCodeOrg;   // 평가그룹코드
    //private String capaCodeKindOrg;

    private List<AppmngapcapaDto> appmngapcapaDtoList;
}
