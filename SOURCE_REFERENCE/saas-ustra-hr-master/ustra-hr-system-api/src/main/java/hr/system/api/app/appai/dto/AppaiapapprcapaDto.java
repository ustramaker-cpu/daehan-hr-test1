package hr.system.api.app.appai.dto;

import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppaiapapprcapaDto extends BaseDto {
    /** 회사코드 */
    private String coCode;
    /** 평가형태코드 */
    private String searchTypeCodeAppr;

    // APAPPRCAPA
    /** 평가년도 */
    private String apprYear;
    /** 평가차수 */
    private String apprSeqNo;
    /** 평가형태코드 */
    private String typeCodeAppr;
    /** 평가대상자사번 */
    private String emplNo;
    /** 항목구분코드 */
    private String taskCode;
    /** 역량순번 */
    private String capaSeqNo;
    /** 역량분류 */
    private String capaKind;
    /** 역량명 */
    private String capaName;
    /** 역량정의 */
    private String capaDef;
    /** 역량추진결과및성과 */
    private String capaDefAf;
    /** 비중 */
    private String weight;
    /** 평가등급코드 */
    private String gradeCodeAppr;

    private String apprGroupCode;       // 평가그룹코드

    // APAPPRPOINT
    /** 평가자사번 */
    private String appraiserEmplNo;
    /** 평가자차수 */
    private String appraiserSeqNo;
    /** 점수 */
    private String point;

    /** 연말평가 점수 */
    private String point0;
    private String point1;
    private String point2;
    private String point3;
    /*연말평가 코멘트*/
    private String peNote0;
    private String peNote1;
    private String peNote2;
    private String peNote3;

    /*연말평가 코멘트*/
    private String ceNote0;
    private String ceNote1;
    private String ceNote2;
    private String ceNote3;

    /** 항목별코멘트 */
    private String comment;

    private String orgCodeFactor;       // 평가부서
    private String makerCode;           // 작성자구분코드
    private String note;                // 평가자의견
    private String peNote;
    private String ceNote;

    /** 평가자 seqnoList */
    private List<Integer> seqnoList;

    private List<AppaiapapprcapaDto> appaiapapprcapaList;

}
