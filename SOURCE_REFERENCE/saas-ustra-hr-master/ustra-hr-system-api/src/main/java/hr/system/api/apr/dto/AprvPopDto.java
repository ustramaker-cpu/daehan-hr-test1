package hr.system.api.apr.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

/*
 결재선관리팝업, 문서양식결재선관리 팝업 DTO
 */
@Getter
@Setter
public class AprvPopDto extends BaseDto {
    private String searchEmpl;
    private String orgCode;
}
