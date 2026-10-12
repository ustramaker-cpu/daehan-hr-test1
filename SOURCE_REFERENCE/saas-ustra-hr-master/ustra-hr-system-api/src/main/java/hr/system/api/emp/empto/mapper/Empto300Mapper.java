package hr.system.api.emp.empto.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.emp.empto.dto.Empto300Dto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Empto300Mapper {

    List<CamelCaseMap> findEmpto300List(Empto300Dto empto300Dto);

    int insertEmpto300(Empto300Dto empto300Dto);

    int updateEmpto300(Empto300Dto empto300Dto);

    int deleteEmpto300(Empto300Dto empto300Dto);
}