package hr.system.api.emp.empto.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.emp.empto.dto.Empto500Dto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Empto500Mapper {

    List<CamelCaseMap> findEmpto500List(Empto500Dto empto500Dto);

    CamelCaseMap findCurTo(Empto500Dto empto500Dto);

    Empto500Dto findParentDepts(Empto500Dto empto500Dto);

    int insertEmpto500(Empto500Dto empto500Dto);

    int updateEmpto500(Empto500Dto empto500Dto);

    int deleteEmpto500(Empto500Dto empto500Dto);

    CamelCaseMap findPartTime(Empto500Dto empto500Dto);

    CamelCaseMap findBefEmplCnt9(Empto500Dto empto500Dto);

    Empto500Dto findEmpto500his(Empto500Dto empto500Dto);

    Empto500Dto findPrevHis(Empto500Dto empto500Dto);

    int updateEffDateTo(Empto500Dto findPrevHis);

    int updateEmpto500HisPrevDay(Empto500Dto empto500Dto);

    List<CamelCaseMap> findLevel3(CamelCaseMap map);

    List<CamelCaseMap> findDirect(CamelCaseMap map);
}