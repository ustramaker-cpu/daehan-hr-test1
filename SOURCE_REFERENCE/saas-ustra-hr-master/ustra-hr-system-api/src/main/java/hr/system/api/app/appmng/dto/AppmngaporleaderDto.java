package hr.system.api.app.appmng.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppmngaporleaderDto extends BaseDto{
    private String emplNo;
    private String assignDate;
    private String assignCode;
    private String orgCode;
    private String orgNameHan;
    private String remark;
    private String emplNameHan;
    private String assignNameHan;
    private String resultInd;
    private String positionNameHan;
    private String titleCode;
    private String titleNameHan;
    private String gradeNameHan;
    private String effDateTo;

    private List<AppmngaporleaderDto> orleaderAsList;
}
