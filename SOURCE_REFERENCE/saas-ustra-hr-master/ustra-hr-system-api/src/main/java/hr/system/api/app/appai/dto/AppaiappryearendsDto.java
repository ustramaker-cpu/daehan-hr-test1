package hr.system.api.app.appai.dto;


import java.util.List;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AppaiappryearendsDto extends BaseDto { //자기신고서 Service DTO

    private String emplNo;        // 평가대상자사번
    private String apprYear;      // 평가년도
    private String apprSeqNo;     // 평가차수
    private String typeCodeAppr;  // 평가형태코드
    private String execInd;       // 전송여부

    private List<AppaiapopncntDto> appaiapopncntDtoList;   // 현 직무 만족도, 전보희망
    private List<AppaiaporgwishDto> appaiaporgwishDtoList; // 이동희망부서
    private List<AppaiapopnetcDto> appaiaponetcDtoList;    // 기타의견
}
