package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class AppaiapopnetcDto extends BaseDto { // 자기신고서_기타의견 DTO

	private String coCode;				// 회사구분코드
	private String emplNo;				// 평가대상자사번
	private String apprYear;			// 평가년도
	private String apprSeqNo;			// 평가차수
	private String typeCodeAppr;		// 평가형태코드
	private String opnEtc;		        // 의견분류코드

}

