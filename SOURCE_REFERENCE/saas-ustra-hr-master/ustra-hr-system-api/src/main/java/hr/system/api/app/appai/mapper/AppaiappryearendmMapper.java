package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendmDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappryearendmMapper {

    List<CamelCaseMap> findAppaiappryearendmList(AppaiappryearendmDto appaiappryearendmDto);
    List<CamelCaseMap> findAppaiappryearendmCapaList(AppaiappryearendmDto appaiappryearendmDto); // 평가점수 테이블 없을 시 조회
    int checkCapaApprm(AppaiappryearendmDto appaiappryearendmDto); // 다면평가점수테이블 데이터 유무 확인
    
    List<CamelCaseMap> findCapaList(AppaiappryearendmDto appaiappryearendmDto); // 역량리스트
    List<CamelCaseMap> findGradeList(AppaiappryearendmDto appaiappryearendmDto); // 평가등급코드리스트
    List<CamelCaseMap> checkIndObj(AppaiappryearendmDto appaiappryearendmDto); // 오픈여부 확인
    List<CamelCaseMap> checkApprserList(AppaiappryearendmDto appaiappryearendmDto); // 평가자 전송여부 확인

    int checkCapaApprmInsert(AppaiappryearendmDto appaiappryearendmDto); // insert 전 확인용
    int insertAppryearendm(AppaiappryearendmDto appaiappryearendmDto);
    int updateAppryearendm(AppaiappryearendmDto appaiappryearendmDto);
    int deleteAppryearendm(AppaiappryearendmDto appaiappryearendmDto);
    
    int updateSubmitStatus(AppaiappryearendmDto appaiappryearendmDto); // 전송 업데이트
}
