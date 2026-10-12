package hr.system.api.app.appai.dto;


import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppaiappprogressmDto extends BaseDto {
    
    private String emplNo;            // 평가대상자사번
    private String apprYear;          // 평가년도
    private String apprSeqNo;         // 평가차수
    private String typeCodeAppr;      // 평가형태코드
    private String orgCodeFactor;     // 평가조직코드
    private String basisDateAppr;     // 평가기준일자
    private String apprGroupCode;     // 평가그룹코드
    private String confmInd;          // 확정여부
    private String sendInd;           // 전송여부
    private String confmIndSend;      // 확인여부
    private String openInd;           // 오픈여부
    private String closeIndAppraiser; // 평가자마감여부
    private String closeInd;          // 평가마감여부
    private String compltIndRelgrade; // 상대등급완료여부
    private String closeIndLast;      // 최종마감여부
    private String remark;            // 비고

    private String factorCodeAppr;    // 평가요소코드
    private String appraiserEmplNo;   // 평가자사번
    private String appraiserSeqNo;    // 평가자차수구분
    private String execInd;           // 평가실시여부

    private String orgCode;
    private String openSelect;  // 오픈여부 (조회조건)
    private String closeSelect; // 평가마감여부 (조회조건)
    private String actEmplNo;   // 평가자 (조회조건)
    private String emplNameHan;
    private String loginUserId; // 로그인한 사번(조회조건) - 피평가자인지 구분(관리자페이지에서)

    private List<AppaiappprogressmDto> appaiappprogressmList;
}
