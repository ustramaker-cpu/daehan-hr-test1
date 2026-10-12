package hr.system.api.advice.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.notice.dto.Notice100Dto;
import hr.system.core.api.help.dto.HelpDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Advice100Mapper {

    List<CamelCaseMap> findAdvice100List(HelpDto helpDto);

}
