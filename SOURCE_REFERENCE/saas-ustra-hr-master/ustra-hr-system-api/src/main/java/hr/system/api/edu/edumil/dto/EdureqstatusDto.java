package hr.system.api.edu.edumil.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EdureqstatusDto extends BaseDto {
    // 조회조건
    private String dayFrom;
    private String dayTo;
    private String courseKindCode;
    private String courseName;
    private String orgCode;
    private String emplNo;
    private String statusCode;
}
