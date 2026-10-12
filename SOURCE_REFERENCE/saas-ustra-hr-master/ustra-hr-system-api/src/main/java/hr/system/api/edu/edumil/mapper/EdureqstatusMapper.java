package hr.system.api.edu.edumil.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumil.dto.EdureqstatusDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EdureqstatusMapper {
    List<CamelCaseMap> findEdureqstatusList(EdureqstatusDto edureqstatusDto);           // 교육신청현황 조회
    List<CamelCaseMap> findEdureqstatusleaderList(EdureqstatusDto edureqstatusDto);     // 부서장용 교육신청현황 조회
}
