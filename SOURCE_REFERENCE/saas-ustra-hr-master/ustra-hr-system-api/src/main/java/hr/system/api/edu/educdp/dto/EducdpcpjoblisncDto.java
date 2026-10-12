package hr.system.api.edu.educdp.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EducdpcpjoblisncDto extends BaseDto {
    private String jobCode;
    private String lisncCodeKind;
    private String lisncCodeGrade;
    private String requireCode;
    private String effDateTo;
    private String effDateFrom;
    private Integer displayOrder;

    private String baseDate;

    private List<EducdpcpjoblisncDto> educdpcpjoblisncDtoList;
}
