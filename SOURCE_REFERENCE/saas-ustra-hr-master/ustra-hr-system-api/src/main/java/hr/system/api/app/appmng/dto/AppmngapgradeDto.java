package hr.system.api.app.appmng.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppmngapgradeDto extends BaseDto {
    // 검색조건
    /** 기준일자 */
    private String baseDate;

    // APGRADE
    /** 회사구분코드 */
    private String coCode;
    /** 평가형태 */
    private String typeCodeAppr;
    private String typeCodeApprOrg;
    /** 평가등급코드 */
    private String gradeCodeAppr;
    private String gradeCodeApprOrg;
    /** 유효시작일자 */
    private String effDateFrom;
    private String effDateFromOrg;
    /** 유효종료일자 */
    private String effDateTo;
    /** 평가등급명 */
    private String gradeNameAppr;
    /** 평가등급점수from */
    private String gradePoint;
    /** 평가등급점수to */
    private String gradePointCvt;
    /** 평가기준설명 */
    private String gradeDesc;
    /** 디스플레이순서 */
    private String displayOrder;

    private String gradeKindAppr;       // 평가등급종류
    private String gradeKindApprOrg;       // 평가등급종류

    private List<AppmngapgradeDto> appmngapgradeDtoList;
}
