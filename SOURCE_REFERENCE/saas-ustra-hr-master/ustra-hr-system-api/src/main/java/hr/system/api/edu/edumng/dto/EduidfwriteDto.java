package hr.system.api.edu.edumng.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EduidfwriteDto extends BaseDto {

    // 교육과정조회 팝업용
    private String jobCodePop;
    private String courseNamePop;

    // 직무 테이블 코드
    /*private String effDateFrom;
    private String effDateTo;
    private String jobLevel1;
    private String jobLevel2;
    private String jobNameHan;
    private String jobDesc;*/

    // 조회 시 param 값
    private String baseYear;
    private String orgCode;
    private String userId;

    private String courseYear;
    private String emplNo;
    private String courseCode;
    private String yearSeqNo;
    private String courseName;
    private String courseGroupCode;
    private String jobCode;
    private String capaCode;
    private String mngOrgEdu;
    private String eduDateFrom;
    private String eduDateTo;
    private String eduTimeTotal;
    private String eduAmt;
    private String dailyAmt;
    private String grade;
    private String statusCode;

    private String dailyNewAmt;

    private List<EduidfwriteDto> eduidfwriteList;

}
