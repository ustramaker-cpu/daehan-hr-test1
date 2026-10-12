package hr.system.api.app.appmng.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppmngapexcpterDto extends BaseDto {

    private String emplNo;
    private String emplNoOrg;
    private String apprYear;        // 평가년도
    private String apprYearOrg;     // 평가년도
    private String apprSeqNo;       // 평가차수
    private String typeCodeAppr;    // 평가형태코드
    private String typeCodeApprOrg;    // 평가형태코드
    private String rsnCodeExcept;
    private String rsnCodeExceptOrg;  // 평가요소코드
    private String remark;          // 업적





    private String apprGroupCode;          // 그룹핑
    private String apprAddPoint;          // 추가 점수
    private String orgCode;               // 조직 코드
    private String titleNameHan;          // 2차 피평가자 조직
    private String orgNameHan2;          // 2차 피평가자 조직

    private String baseYear;

    private Integer returnCode;         // 프로시저 리턴코드
    private String returnMsg;          // 프로시저 리턴메세지

    private List<AppmngapexcpterDto> appmngapexcpterDtoList;
}
