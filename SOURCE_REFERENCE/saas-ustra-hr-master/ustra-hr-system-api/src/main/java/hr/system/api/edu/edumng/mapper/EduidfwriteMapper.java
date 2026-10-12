package hr.system.api.edu.edumng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfwriteDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduidfwriteMapper {

    List<CamelCaseMap> findEduidfwriteList(EduidfwriteDto eduidfwriteDto);
    int checkCountNo(EduidfwriteDto eduidfwriteDto);
    int checkYearSeqNo(EduidfwriteDto eduidfwriteDto);

    // 교육과정조회 팝업 리스트
    List<CamelCaseMap> findEdcoursePopList(EduidfwriteDto eduidfwriteDto);

    List<CamelCaseMap> findEdcourseAllPopList(EduidfwriteDto eduidfwriteDto);

    // 직무코드 가져오기
    List<CamelCaseMap> findCpJobMasterList(EduidfwriteDto eduidfwriteDto);

    // 역량코드 가져오기
    List<CamelCaseMap> findInitCpPgdesc(EduidfwriteDto eduidfwriteDto);

    // 직무에 따른 역량코드 가져오기
    List<CamelCaseMap> findCpPgdescList(EduidfwriteDto eduidfwriteDto);

    // 자기개발계획상태 확인용
    List<CamelCaseMap> checkPlanStatus(EduidfwriteDto eduidfwriteDto);

    int insertEduidfwrite(EduidfwriteDto eduidfwriteDto);
    int updateEduidfwrite(EduidfwriteDto eduidfwriteDto);
    int deleteEduidfwrite(EduidfwriteDto eduidfwriteDto);
}