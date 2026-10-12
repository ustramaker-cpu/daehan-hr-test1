package hr.system.api.edu.educdp.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EducdpcpjobeduDto extends BaseDto {
    private String jobCode;
    private String courseCode;
    private String effDateFrom;
    private String effDateTo;
    private String courseName;
    private String mngOrgEdu;
    private Integer displayOrder;
    private String remark;

    private String baseDate;

    private List<EducdpcpjobeduDto> educdpcpjobeduDtoList;
}
