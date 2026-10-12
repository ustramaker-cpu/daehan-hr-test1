package hr.system.api.edu.educdp.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobindivmngDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EducdpcpjobindivmngMapper {

    List<CamelCaseMap> findEducdpcpjobindivmngList(EducdpcpjobindivmngDto educdpcpjobindivmngDto);
    List<CamelCaseMap> findEducdpcpjobindivHistory(EducdpcpjobindivmngDto educdpcpjobindivmngDto);

    int checkEduEffDateFrom(EducdpcpjobindivmngDto educdpcpjobindivmngDto);
    int checkEduEffDateTo(EducdpcpjobindivmngDto educdpcpjobindivmngDto);

    int insertEducdpcpjobindivmng(EducdpcpjobindivmngDto educdpcpjobindivmngDto);
    int updateEducdpcpjobindivmng(EducdpcpjobindivmngDto educdpcpjobindivmngDto);
    int deleteEducdpcpjobindivmng(EducdpcpjobindivmngDto educdpcpjobindivmngDto);

    int updateEduEffDateTo1(EducdpcpjobindivmngDto educdpcpjobindivmngDto);
    int updateEduEffDateTo2(EducdpcpjobindivmngDto educdpcpjobindivmngDto);
}