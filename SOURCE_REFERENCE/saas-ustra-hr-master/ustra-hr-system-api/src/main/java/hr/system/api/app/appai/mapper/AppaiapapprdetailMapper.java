package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprcapaDto;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappraiserDto;
import hr.system.api.app.appai.dto.AppaiappryearendDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiapapprdetailMapper {

    // 평가 항목 상세 조회
    List<CamelCaseMap> findAppaiapprdetailList(AppaiapapprcapaDto appaiapapprcapaDto);
    List<CamelCaseMap> findAppaiapprdetailDefaultList(AppaiapapprcapaDto appaiapapprcapaDto);


    // 평가자 조회
    List<CamelCaseMap> findApprserList(AppaiappraiserDto appaiappraiserDto);

    // 평가자 진행 상태 변경
    int updateAppraiserFalg(AppaiappraiserDto appaiappraiserDto);

    // 역량,성과 등록 갯수 확인
    int checkApapprcapa(AppaiapapprcapaDto appaiapapprcapaDto);

    // 역량,성과 SeqNo 조회
    int checkApprcapaSeqNo(AppaiapapprcapaDto appaiapapprcapaDto);

    // 역량,성과 저장
    int insertTargetApapprcapa(AppaiapapprcapaDto appaiapapprcapaDto);

    //역량, 성과 저장(select기반)
    int insertselectTargetApapprcapa(AppaiapapprcapaDto appaiapapprcapaDto);
    // 역량,성과 저장 - 목표설정 기반
    // int insertTargetApapprcapaFromC(AppaiapapprcapaDto appaiapapprcapaDto);

    // 역량, 성과 저장
    int saveApapprcapa(AppaiapapprcapaDto appaiapapprcapaDto);


    // 역량,성과 저장
    int insertApapprcapa(AppaiapapprcapaDto appaiapapprcapaDto);

    // 역량,성과 수정
    int updateApapprcapa(AppaiapapprcapaDto appaiapapprcapaDto);

    // 역량,성과 삭제
    int deleteApapprcapa(AppaiapapprcapaDto appaiapapprcapaDto);
    
    // 역량,성과 수정 데이터 체크
    int checkInsertApapprcapa(AppaiapapprcapaDto appaiapapprcapaDto);

    // 성과 점수 수정 데이터 체크
    int checkInsertApapprpoint(AppaiapapprcapaDto appaiapapprcapaDto);

    // 성과 점수 저장
    int saveApapprpoint(AppaiapapprcapaDto appaiapapprcapaDto);

    // 성과 점수 저장
    int saveApapprpointY(AppaiapapprcapaDto appaiapapprcapaDto);

    // 성과 점수 저장
    int insertApapprpoint(AppaiapapprcapaDto appaiapapprcapaDto);

    // 성과 점수 수정
    int updateApapprpoint(AppaiapapprcapaDto appaiapapprcapaDto);

    // 성과 점수 삭제
    int deleteApapprpoint(AppaiapapprcapaDto appaiapapprcapaDto);
}
