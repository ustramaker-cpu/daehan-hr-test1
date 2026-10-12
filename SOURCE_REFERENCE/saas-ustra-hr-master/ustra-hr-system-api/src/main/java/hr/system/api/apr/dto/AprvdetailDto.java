package hr.system.api.apr.dto;

import com.gsitm.framework.core.object.UstraObject;
import com.gsitm.framework.core.utils.ModelMapperUtils;
import hr.system.api.apr.common.AprvType;
import hr.system.core.config.domain.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;

@Getter
@Setter
@ToString
public class AprvdetailDto extends BaseDto { // 문서디테일 (결재선 라인)

    private String docNo;        // 문서번호

    private Long aprvSeqNo;    // 결재 seq

    private String aprvType;     // 결재 타입

    private String aprvEmplNo;   // 결재자 사번

    private String transInd;     // 대리결재 여부

    private String transEmplNo;  // 대리결재자 사번

    private String statusCode;   // 결재 상태 코드

    private String aprvDate;     // 결재 완료 일자

    private String aprvCmnt;     // 결재라인 의견

    private String aprvDateYN;   // 결재 완료 일자 여부
    
    private List<AprvdetailDto> aprvdetailDtoList; // list

    public boolean isViewer(){
        return this.aprvType != null && this.aprvType.equals(AprvType.VIEWER);
    }

    public boolean isApprover(){
        return this.aprvType != null && this.aprvType.equals(AprvType.APPROVER);
    }

    public boolean isChecker(){
        return this.aprvType != null && this.aprvType.equals(AprvType.CHECKER);
    }

    @Override
    public AprvdetailDto of(UstraObject source) {
        ModelMapperUtils.getModelMapper().getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return super.of(source, this.getClass());
    }
}
