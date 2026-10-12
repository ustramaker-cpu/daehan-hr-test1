package hr.system.api.edu.educdp.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EducdpcpjobcapaDto extends BaseDto {
    private String jobCode;
    private String pgCodeKind;
    private String pgCode;
    private String pgNameHan;
    private String pgIndicat;
    private String effDateTo;
    private String effDateFrom;
    private Integer displayOrder;
    private String remmark;

    private String baseDate;

    private List<EducdpcpjobcapaDto> educdpcpjobcapaDtoList;
}
