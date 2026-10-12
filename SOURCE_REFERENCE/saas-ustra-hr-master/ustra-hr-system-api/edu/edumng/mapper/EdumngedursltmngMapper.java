package hr.system.api.edu.edumng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EdumngedursltmngMapper {
    List<CamelCaseMap> findEdumngedursltmngList(EdumngedursltDto edumngedursltDto);

    List<CamelCaseMap> findEdumngedursltmngTabList(EdumngedursltDto edumngedursltDto);

    int insertEdedursltmng(EdumngedursltDto edumngedursltDto) ;

    int updateEdedursltmng(EdumngedursltDto edumngedursltDto) ;

    int deleteEdedursltmng(EdumngedursltDto edumngedursltDto) ;

    int checkOrleader(EdumngedursltDto edumngedursltDto) ;

    List<CamelCaseMap> findEdcourseList(EdumngedursltDto edumngedursltDto);

    CamelCaseMap checkbackData(EdumngedursltDto dto);
}