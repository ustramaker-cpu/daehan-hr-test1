package hr.system.api.edu.educdp.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobmngDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EducdpcpjobmngMapper {
    List<CamelCaseMap> findEducdpcpjobmngList(EducdpcpjobmngDto educdpcpjobmngDto);

    int checkCpjobmasterList(EducdpcpjobmngDto educdpcpjobmngDto) ;

    int insertCpjobmaster(EducdpcpjobmngDto educdpcpjobmngDto) ;

    int updateCpjobmaster(EducdpcpjobmngDto educdpcpjobmngDto) ;

    int deleteCpjobmaster(EducdpcpjobmngDto educdpcpjobmngDto) ;

    List<CamelCaseMap> findJobcodeHistory(EducdpcpjobmngDto educdpcpjobmngDto);
}