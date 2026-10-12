package hr.system.api.emp.empto.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.emp.empto.dto.Empto210Dto;
import hr.system.api.emp.empto.dto.Empto300Dto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Empto210Mapper {

    List<CamelCaseMap> initCodeSet(Empto210Dto empto210Dto);

    List<CamelCaseMap> findEmpto210List(Empto210Dto empto210Dto);

    CamelCaseMap findCurTo(Empto210Dto empto210Dto);

    Empto210Dto findParentDepts(Empto210Dto empto210Dto);

    int insertEmpto210(Empto210Dto empto210Dto);

    int updateEmpto210(Empto210Dto empto210Dto);

    int deleteEmpto210(Empto210Dto empto210Dto);

    CamelCaseMap findPartTime(Empto210Dto empto210Dto);

    CamelCaseMap findBefEmplCnt9(Empto210Dto empto210Dto);

    Empto210Dto findEmpto210his(Empto210Dto empto210Dto);

    Empto210Dto findPrevHis(Empto210Dto empto210Dto);

    int updateEffDateTo(Empto210Dto findPrevHis);

    int updateEmpto210HisPrevDay(Empto210Dto empto210Dto);
}