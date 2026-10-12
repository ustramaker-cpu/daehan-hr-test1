package hr.system.api.edu.educdp.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EducdpcpmapmngDto extends BaseDto {
    private String jobCode;
    private String effDateFrom;
    private String effDateTo;
    private String jobLevel1;
    private String jobLevel1Nm;
    private String jobLevel2;
    private String jobLevel2Nm;
    private String jobNameHan;
    private String jobNameEng;
    private String jobDesc;

    private String baseDate;

    private List<EducdpcpmapmngDto> educdpcpmapmngDtoList;
}
