package hr.system.api.edu.edumng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfwriteDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduidfwritemngMapper {

    List<CamelCaseMap> findEduidfwritemngList(EduidfwriteDto eduidfwriteDto);

    // 자기개발계획상태 확인용
    List<CamelCaseMap> checkPlanStatus(EduidfwriteDto eduidfwriteDto);
    // insert 전 상태 확인용
    EduidfwriteDto checkPlanStatusInsert(EduidfwriteDto eduidfwriteDto);

    int insertEduidfwritemng(EduidfwriteDto eduidfwriteDto);
    int updateEduidfwritemng(EduidfwriteDto eduidfwriteDto);
    int deleteEduidfwritemng(EduidfwriteDto eduidfwriteDto);
}