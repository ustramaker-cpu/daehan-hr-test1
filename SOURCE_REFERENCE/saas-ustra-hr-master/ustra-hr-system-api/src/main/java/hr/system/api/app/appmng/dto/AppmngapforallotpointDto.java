package hr.system.api.app.appmng.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppmngapforallotpointDto extends BaseDto {

    private String apprYear;            // 평가년도
    private String apprSeqNo;           // 차수
    private String point;               // 기준점수
    private String pointLm;             // 점수구분(이상/이하)
    private String allotPoint;          // 인당배분점수

    private String baseYear;            // 검색기준일자

    private List<AppmngapforallotpointDto> appmngapforallotpointDtoList;

}
