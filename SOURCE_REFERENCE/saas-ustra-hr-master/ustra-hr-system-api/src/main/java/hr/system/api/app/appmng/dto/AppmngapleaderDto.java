package hr.system.api.app.appmng.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppmngapleaderDto extends BaseDto {
    private String coCode; // 회사코드
    private String orgCode; // 조직코드
    private String orgNameHan; // 조직명
    private String orgNamePath; // 조직명경로
    private String effDateFrom; // 유효시작일자
    private String effDateFromOld; // 이전유효시작일자
    private String effDateTo; // 유효종료일자
    private String emplNo; // 사번

    private String appInd; // 평가여 부여부
    private String levelNameHan; // 조직레벨명
    private String emplNameHan; // 성명
    private String positionNameHan; //직위
    private String titleNameHan; // 직책
    private String titleCode;       // 직책코드
    private String gradeNameHan; // 직급

    private String levelCode; // 조직레벨
    private String apprYear; // 평가 년도
    private String apprSeqNo; // 평가차수

    private Integer returnCode; // 리턴코드

    private List<AppmngapleaderDto> appmngapleaderDtoList;
}
