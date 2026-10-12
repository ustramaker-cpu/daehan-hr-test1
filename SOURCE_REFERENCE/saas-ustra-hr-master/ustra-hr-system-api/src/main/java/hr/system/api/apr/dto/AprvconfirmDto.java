package hr.system.api.apr.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AprvconfirmDto extends BaseDto { // 확인자 관리
    private String formId;         // 문서양식번호

    private String emplNo;         // 사번

    private String confirmSeqNo;   // 확인자SEQ

    private String confirmType;    // 확인자 전/후

    private String emplNameHan;    // 사원명

    private String positionNameHan;   // 직위명

    private String titleNameHan;   // 직책명

    private String orgNameHan;     // 부서명

    private String baseDate;       // 기준일자

    private String aprvType;       // 결재TYPE

    private List<AprvconfirmDto> aprvconfirmDtoList; // list
}
