package hr.system.api.edu.edumng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfmngDto;
import hr.system.api.edu.edumng.dto.EduidfwriteDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduidfteamMapper {

    List<CamelCaseMap> findEduidfteamList(EduidfmngDto eduidfmngDto);

    List<CamelCaseMap> findEduidfExcelAllDownList(EduidfmngDto eduidfmngDto);

    // 전송상태 업데이트 전 근무기간 체크
    EduidfmngDto checkSubmitEmpDay(EduidfmngDto eduidfmngDto);
    // 총 교육 시간 합계
    EduidfmngDto checkEduplanSum(EduidfmngDto eduidfmngDto);
    // 전송
    int updateStatusCode(EduidfmngDto eduidfmngDto);
}