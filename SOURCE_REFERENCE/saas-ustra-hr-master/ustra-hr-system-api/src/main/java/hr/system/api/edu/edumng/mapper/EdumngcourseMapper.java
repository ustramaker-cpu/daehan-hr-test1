package hr.system.api.edu.edumng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngcourseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EdumngcourseMapper {

    List<CamelCaseMap> findEdumngcourseList(EdumngcourseDto edumngcourseDto);

    int insertEdumngcourse(EdumngcourseDto edumngcourseDto);
    int updateEdumngcourse(EdumngcourseDto edumngcourseDto);
    int deleteEdumngcourse(EdumngcourseDto edumngcourseDto);

}