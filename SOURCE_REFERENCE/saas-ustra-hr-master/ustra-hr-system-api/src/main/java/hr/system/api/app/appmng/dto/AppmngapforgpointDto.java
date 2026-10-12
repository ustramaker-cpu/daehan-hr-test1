package hr.system.api.app.appmng.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppmngapforgpointDto extends BaseDto {

    private String coCode;              // 회사코드
    private String apprYear;            // 평가년도
    private String apprSeqNo;           // 평가차수
    private String orgCodeFactor;       // 코드
    private String orgNameFactor;       // 본부명
    private String point;               // 점수
    private String addPoint;            // 추가점수
    private String addCount;            // 추가인원수

    private String allotPoint;          // 인당배분점수
    private String orgCode;
    private String orgCodeUp;           // 상위부서코드

    private String orgCodeFactor1;      // 학원 평가 조직 코드
    private String apprGroupingCode;    // 그룹핑 코드
    private int apprAddPoint;        // 그룹핑 추가점수
    private int totAllotPoint;       // 학원 배분총점
    private String emplNo;              // 평가자 사번
    private String apprCheckYn;              // 학원여부 체크


    private String baseYear;

    private List<AppmngapforgpointDto> appmngapforgpointDtoList;
}
