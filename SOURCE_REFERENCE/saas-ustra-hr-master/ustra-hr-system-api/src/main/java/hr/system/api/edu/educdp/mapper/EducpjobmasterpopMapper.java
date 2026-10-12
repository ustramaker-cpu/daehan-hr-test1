package hr.system.api.edu.educdp.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobindivmngDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EducpjobmasterpopMapper {

    List<CamelCaseMap> findEducpjobmasterList(EducdpcpjobindivmngDto educdpcpjobindivmngDto);
}