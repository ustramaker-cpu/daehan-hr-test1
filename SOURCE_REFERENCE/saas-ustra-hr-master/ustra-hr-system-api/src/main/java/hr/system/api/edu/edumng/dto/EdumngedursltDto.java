package hr.system.api.edu.edumng.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EdumngedursltDto extends BaseDto {
    private String fromYear;
    private String toYear;
    private String courseYear;
    private String emplNo;
    private String loginEmplNo;
    private String emplNameHan;
    private String orgNameHan;
    private String orgCode;
    private String gradeCode;
    private String gradeNameHan;

    private String level1CodeCourse;
    private String level1CodeNameHan;
    private String courseCode;
    private String courseCodeOrg;
    private String rsltSeqNo;
    private String jobCode;
    private String jobNameHan;
    private String pgCode;
    private String pgNameHan;
    private String courseGroupCode;
    private String courseName;
    private String eduDateFrom;
    private String eduDateTo;
    private String planInd;
    private String eduTimeTotal;
    private String completeInd;
    private String completeCredit;
    private String eduAmt;
    private String dailyAmt;
    private String changeInd;
    private String grade;
    private String mngOrgEdu;
    private String remark;

    private String flagKey;
    private String flagValue;

    private List<EdumngedursltDto> edumngedursltDtoList;
}
