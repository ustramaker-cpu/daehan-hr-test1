package hr.system.api.edu.edumil.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EdumilmileageDto extends BaseDto {
    private String emplNo;
    private String emplNoOrg;
    private String emplNameHan;
    private String searchEmplNo;
    private String loginEmplNo;
    private String reqEmplNo;
    private String searchEmplNameHan;
    private String orgNameHan;
    private String orgCode;
    private String gradeCode;
    private String gradeNameHan;

    private String milYear;
    private String milSeqNo;
    private String milYearOrg;
    private String baseYear;
    private String evaArea;
    private String evaAreaName;
    private String evaAreaOrg;
    private String evaItem;
    private String evaItemName;
    private String evaItemOrg;
    private String milDateFrom;
    private String milDateFromOrg;
    private String milDateTo;
    private String actDetail;
    private String actDetailOrg;
    private Integer milWeight;
    private Integer milPoint;
    private String remark;
    private String statusCode;

    private String statusCodeNameHan;

    private String adminFlag;
    private String flagKey;
    private String flagValue;

    private List<EdumilmileageDto> edumilmileageDtoList;
}
