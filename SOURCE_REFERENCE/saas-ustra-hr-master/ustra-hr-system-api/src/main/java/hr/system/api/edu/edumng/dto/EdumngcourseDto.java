package hr.system.api.edu.edumng.dto;

import hr.system.api.doc.docappr.dto.DocapprflexDto;
import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EdumngcourseDto extends BaseDto {

    private String level1CodeCourse;
    private String level1CodeCourseName;
    private String courseGroupCode;
    private String courseGroupCodeName;
    private String courseCode;
    private String courseName;
    private String mngOrgEdu;
    private String eduPlace;
    private String eduAmt;
    private String completeCredit;
    private String mandatoryIndCourse;
    private String eduDateFrom;
    private String eduDateTo;
    private String closeInd;
    private String remark;

    private String closeIndSearch;

    private List<EdumngcourseDto> edumngcourseList;


}
