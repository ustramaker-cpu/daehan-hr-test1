package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprcapaDto;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappraiserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiapapprtargetMapper {
    // 평가자 차수 조회
    Integer countApappraiserSeqno(AppaiapapprindDto appaiapapprindDto);

    // 평가대상자 공통 조회
    List<CamelCaseMap> findAppraisementTargetList(AppaiapapprindDto appaiapapprindDto);

    // 평가진행 상태 변경
    int updateApapprindFalg(AppaiapapprindDto appaiapapprindDto);
    int updateApapprindInd(AppaiapapprindDto appaiapapprindDto);

    // 평가예외자 조회
    int checkApexcpter(AppaiapapprindDto appaiapapprindDto);

    // 평가대상자 추가
    int insertApapprind(AppaiapapprindDto appaiapapprindDto);
    // 평가대상자 변경
    int updateApapprind(AppaiapapprindDto appaiapapprindDto);
    // 평가대상자 삭제
    int deleteApapprind(AppaiapapprindDto appaiapapprindDto);

    // 평가가 추가
    int insertAppraiser(AppaiapapprindDto appaiapapprindDto);
    // 평가가 변경
    int updateAppraiser(AppaiapapprindDto appaiapapprindDto);
    // 평가의견 변경
    int updateAppraiserNote(AppaiapapprcapaDto appaiapapprcapaDto);
    // 평가가 삭제
    int deleteAppraiser(AppaiapapprindDto appaiapapprindDto);
    // 평가자 확인
    int checkAppraiser(AppaiapapprindDto appaiapapprindDto);

    // 평가여부 확인
    List<CamelCaseMap> checkApprInd(AppaiapapprindDto appaiapapprindDto);
    // 확정여부 확인
    int checkConfmApprInd(AppaiapapprindDto appaiapapprindDto);

    // 평가데이터 조회
    List<CamelCaseMap> findAppraiseInd(AppaiapapprindDto appaiapapprindDto);
}
