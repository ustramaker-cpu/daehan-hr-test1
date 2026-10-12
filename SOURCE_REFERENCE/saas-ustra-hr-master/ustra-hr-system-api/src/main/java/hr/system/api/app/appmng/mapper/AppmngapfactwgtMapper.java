package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapfactwgtDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngapfactwgtMapper {
    List<CamelCaseMap> findAppmngapfactwgtList(AppmngapfactwgtDto appmngapfactwgtDto);

    int checkApfactwgtList(AppmngapfactwgtDto appmngapfactwgtDto) ;

    int saveApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto);

    int insertApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto) ;

    int updateApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto) ;

    int deleteApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto) ;

    int insertselectApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto) ;

    int deleteYearApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto) ;

}
