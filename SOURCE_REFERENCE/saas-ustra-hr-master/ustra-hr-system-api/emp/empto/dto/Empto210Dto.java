package hr.system.api.emp.empto.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Empto210Dto extends BaseDto {

    private String coCode;
    private String apprYear;
    private String orgCode;
    private String orgNameHan;
    private String effDateFrom;
    private String effDateTo;
    private String lv1NameHan;
    private String lv2NameHan;
    private String lv3NameHan;
    private String lv4NameHan;
    private String lv5NameHan;
    private String lv6NameHan;
    private String hireTypeCode;
    private String gradeCodeKind;
    private int befTo;
    private int curTo;
    private int planTo;
    private int calTo;
    private int befEmplCnt9;
    private int befEmplCnt10;
    private int befEmplCnt11;
    private int befEmplCnt12;
    private int emplCnt1;
    private int emplCnt2;
    private int emplCnt3;
    private int emplCnt4;
    private int emplCnt5;
    private int emplCnt6;
    private int emplCnt7;
    private int emplCnt8;
    private int emplCnt9;
    private int emplCnt10;
    private int emplCnt11;
    private int emplCnt12;
    private String remark;
    private String baseDate;

    private String baseHireTypeCode;
    private String baseHireTypeNameHan;
    private String baseGradeCode;
    private String baseGradeNameHan;

    private List<Empto210Dto> empto210dtoList;

}
