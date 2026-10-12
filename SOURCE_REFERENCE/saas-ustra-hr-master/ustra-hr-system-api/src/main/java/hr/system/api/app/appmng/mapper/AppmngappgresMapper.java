package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngappgresDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngappgresMapper {

    List<CamelCaseMap> findAppmngappgresList(AppmngappgresDto appmngappgresDto);//성과평가 항목 관리

    int checkAppgresList(AppmngappgresDto appmngappgresDto) ;

    int insertAppgres(AppmngappgresDto appmngappgresDto) ;

    int updateAppgres(AppmngappgresDto appmngappgresDto) ;

    int deleteAppgres(AppmngappgresDto appmngappgresDto) ;

    int insertselectAppgres(AppmngappgresDto appmngappgresDto) ;

    int deleteYearAppgres(AppmngappgresDto appmngappgresDto) ;


}
