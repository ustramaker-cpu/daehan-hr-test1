package hr.system.api.edu.edumng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EdumngedursltMapper {
    List<CamelCaseMap> findEdumngedursltList(EdumngedursltDto edumngedursltDto);

    List<CamelCaseMap> findEdumngedursltTabList(EdumngedursltDto edumngedursltDto);

    List<CamelCaseMap> findEdumngedursltempList(EdumngedursltDto edumngedursltDto);

    List<CamelCaseMap> findEdumngedursltempTabList(EdumngedursltDto edumngedursltDto);

    int insertEdedurslt(EdumngedursltDto edumngedursltDto) ;

    int updateEdedurslt(EdumngedursltDto edumngedursltDto) ;

    int deleteEdedurslt(EdumngedursltDto edumngedursltDto) ;

    List<CamelCaseMap> findCpjobmasterList(EdumngedursltDto edumngedursltDto);

    List<CamelCaseMap> findCppgdescList(EdumngedursltDto edumngedursltDto);

    List<CamelCaseMap> findCpjobeduList(EdumngedursltDto edumngedursltDto);

    int updateEdedursltCompleteChange(EdumngedursltDto edumngedursltDto) ;
}