package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class AppaiapopncntDto extends BaseDto { // 자기신고서 현직무만족도, 전보희망 DTO

	private String coCode;				// 회사구분코드
	private String emplNo;				// 평가대상자사번
	private String apprYear;			// 평가년도
	private String apprSeqNo;			// 평가차수
	private String typeCodeAppr;		// 평가형태코드
	private String researchInd;		    // 설문구분
	private String opnCode;			    // 의견항목코드
	private String opnCodeClass;		// 의견분류코드
	private String otherText;			// 분류외기타의견

}
