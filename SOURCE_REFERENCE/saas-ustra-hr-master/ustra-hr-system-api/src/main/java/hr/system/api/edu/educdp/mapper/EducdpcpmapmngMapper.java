package hr.system.api.edu.educdp.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobcapaDto;
import hr.system.api.edu.educdp.dto.EducdpcpjobeduDto;
import hr.system.api.edu.educdp.dto.EducdpcpjoblisncDto;
import hr.system.api.edu.educdp.dto.EducdpcpjobunitDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EducdpcpmapmngMapper {

    List<CamelCaseMap> findEducdpcpjobunitList(EducdpcpjobunitDto educdpcpjobunitDto);

    List<CamelCaseMap> findEducdpcpjobeduList(EducdpcpjobeduDto educdpcpjobeduDto);

    List<CamelCaseMap> findEducdpcpjoblisncList(EducdpcpjoblisncDto educdpcpjoblisncDto);

    List<CamelCaseMap> findJobunitcodeHistory(EducdpcpjobunitDto educdpcpjobunitDto);

    List<CamelCaseMap> findEducdpcpjobcapaList(EducdpcpjobcapaDto educdpcpjobcapaDto);

    int checkCpjobunitList(EducdpcpjobunitDto educdpcpjobunitDto) ;

    int insertCpjobunit(EducdpcpjobunitDto educdpcpjobunitDto) ;

    int updateCpjobunit(EducdpcpjobunitDto educdpcpjobunitDto) ;

    int deleteCpjobunit(EducdpcpjobunitDto educdpcpjobunitDto) ;

    int checkCpjobeduList(EducdpcpjobeduDto educdpcpjobeduDto) ;

    int insertCpjobedu(EducdpcpjobeduDto educdpcpjobeduDto) ;

    int updateCpjobedu(EducdpcpjobeduDto educdpcpjobeduDto) ;

    int deleteCpjobedu(EducdpcpjobeduDto educdpcpjobeduDto) ;

    int checkCpjoblisncList(EducdpcpjoblisncDto educdpcpjoblisncDto) ;

    int insertCpjoblisnc(EducdpcpjoblisncDto educdpcpjoblisncDto) ;

    int updateCpjoblisnc(EducdpcpjoblisncDto educdpcpjoblisncDto) ;

    int deleteCpjoblisnc(EducdpcpjoblisncDto educdpcpjoblisncDto) ;

    int checkCpjobcapaList(EducdpcpjobcapaDto educdpcpjobcapaDto) ;

    int insertCpjobcapa(EducdpcpjobcapaDto educdpcpjobcapaDto) ;

    int updateCpjobcapa(EducdpcpjobcapaDto educdpcpjobcapaDto) ;

    int deleteCpjobcapa(EducdpcpjobcapaDto educdpcpjobcapaDto) ;
}