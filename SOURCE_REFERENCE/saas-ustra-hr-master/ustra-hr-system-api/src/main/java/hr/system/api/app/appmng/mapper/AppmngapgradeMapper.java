package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapgradeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngapgradeMapper {
    List<CamelCaseMap> findAppmngapgradeList(AppmngapgradeDto appmngapgradeDto);

    List<CamelCaseMap> findGradeKindApprHistory(AppmngapgradeDto appmngapgradeDto);

    int checkApgradeList(AppmngapgradeDto appmngapgradeDto) ;
    int checkApgradeEffdate(AppmngapgradeDto appmngapgradeDto);
    String checkApgradeEffdateto(AppmngapgradeDto appmngapgradeDto) ;
    int insertApgrade(AppmngapgradeDto appmngapgradeDto) ;
    int updateApgrade(AppmngapgradeDto appmngapgradeDto) ;

    int updateApgradeEffdateto(AppmngapgradeDto appmngapgradeDto) ;

    int deleteApgrade(AppmngapgradeDto appmngapgradeDto) ;

}
