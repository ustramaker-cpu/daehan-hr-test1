package hr.system.api.edu.educdp.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EducdpcdprsltDto extends BaseDto {
    private String orgCode;
    private String emplNo;
    private String baseYear;
    private String jobCode;
    private String pgCode;
    private String jobHopeCode;
}
