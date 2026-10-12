package hr.system.api.app.appmng.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppmngappobjstandDto extends BaseDto {

    private String apprYear;
    private String apprSeqNo;
    private String typeCodeAppr;
    private String standPoint;

    private List<AppmngappobjstandDto> appmngappobjstandList;

}
