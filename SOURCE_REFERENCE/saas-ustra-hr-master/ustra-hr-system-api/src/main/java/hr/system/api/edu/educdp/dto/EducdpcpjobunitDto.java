package hr.system.api.edu.educdp.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EducdpcpjobunitDto extends BaseDto {
    private String jobCode;
    private String effDateFrom;
    private String effDateTo;
    private String jobUnitCode;
    private String jobUnitSeq;
    private String jobDescNameHan;
    private String jobDescNameEng;
    private Integer displayOrder;
    private String remark;

    private String baseDate;

    private List<EducdpcpjobunitDto> educdpcpjobunitDtoList;
}
