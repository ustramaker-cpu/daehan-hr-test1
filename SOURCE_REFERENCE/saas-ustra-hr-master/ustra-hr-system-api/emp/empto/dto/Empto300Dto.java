package hr.system.api.emp.empto.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Empto300Dto extends BaseDto {

    private String coCode;
    private String apprYear;
    private String orgCode;
    private Integer seqNo;
    private String jobNameHan;
    private String workTime;
    private String workDay;
    private String workHour;
    private String timeAmt;
    private String weekHour;
    private String fixMonWkHour;
    private String monPayAmt;
    private String remark;

    private List<Empto300Dto> empto300dtoList;

}
