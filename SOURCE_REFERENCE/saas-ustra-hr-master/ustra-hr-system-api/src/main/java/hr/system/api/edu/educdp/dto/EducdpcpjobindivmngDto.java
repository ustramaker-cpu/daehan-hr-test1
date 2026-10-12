package hr.system.api.edu.educdp.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EducdpcpjobindivmngDto extends BaseDto {

    private String baseDate;
    private String emplNo;
    private String orgCode;
    private String jobCodeName; // 개인별직무관리 화면 직무코드/명
    private String jobNameHan; // 직무관리 팝업 param

    private String jobCode;
    private String effDateFrom;
    private String effDateTo;

    private List<EducdpcpjobindivmngDto> educdpcpjobindivmngList;
}
