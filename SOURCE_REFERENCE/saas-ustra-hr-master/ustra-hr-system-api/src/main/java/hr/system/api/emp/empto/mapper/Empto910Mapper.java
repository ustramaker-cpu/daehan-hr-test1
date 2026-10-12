package hr.system.api.emp.empto.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.emp.empto.dto.Empto910Dto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Empto910Mapper {

    CamelCaseMap findHeadOrgCode(Empto910Dto empto910Dto);
    List<CamelCaseMap> findEmpto910(Empto910Dto empto910Dto);
    String findEmpto910DocNo(Empto910Dto empto910Dto);

    int insertDocapprEmpto910(Empto910Dto empto910Dto);
    Integer checkAprvdocmstr(Empto910Dto empto910Dto);

}
