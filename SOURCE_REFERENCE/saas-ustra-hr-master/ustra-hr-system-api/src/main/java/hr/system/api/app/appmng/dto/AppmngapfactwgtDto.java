package hr.system.api.app.appmng.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppmngapfactwgtDto extends BaseDto {
    // 검색조건
    /** 기준일자 */
    private String baseYear;

    // APFACTWGT
    /** 평가년도 */
    private String apprYear;
    private String apprYearOrg;
    /** 평가차수 */
    private String apprSeqNo;
    /** 평가형태코드 */
    private String typeCodeAppr;
    private String typeCodeApprOrg;
    /** 평가요소코드 */
    private String factorCodeAppr;
    private String factorCodeApprOrg;
    /** 평가그룹코드 */
    private String apprGroupCode;
    private String apprGroupCodeOrg;

    private String weight;          // 비중
    private String remark;          // 업적

    private List<AppmngapfactwgtDto> appmngapfactwgtDtoList;
}
