package hr.system.api.edu.edumng.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EduidfmngDto extends BaseDto {

    private String courseYear;
    private String emplNo;
    private String emplNameHan;
    private String gradeNameHan;
    private String orgCode;
    private String orgNameHan;
    private String statusCode;
    private String codeNameHan;

    private String baseYear;
    private String userId; // 엑셀 일괄 다운로드 용

    // 전송 업데이트 전 체크 용
    private String dayData;
    private String eduSumData;

    private List<EduidfmngDto> eduidfmngList;


}
