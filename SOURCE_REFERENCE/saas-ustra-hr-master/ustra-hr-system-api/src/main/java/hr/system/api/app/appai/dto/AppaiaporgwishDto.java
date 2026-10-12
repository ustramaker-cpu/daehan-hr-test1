package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class AppaiaporgwishDto extends BaseDto { // 자기신고서_이동희망부서 DTO

	private String coCode;         // 회사구분코드
	private String apprYear;       // 평가년도
	private String emplNo;         // 평가대상자사번
	private String apprSeqNo;      // 평가차수
	private String typeCodeAppr;   // 평가형태코드
	private String seqOrder;       // 지망순서
	private String orgWishCode;    // 희망부서코드
	private String jobWishCode;    // 희망직무코드
	private String busiWish;       // 희망업무
	private String remarks;        // 사유

    private List<AppaiaporgwishDto> AppaiaporgwishDtoList;
}
