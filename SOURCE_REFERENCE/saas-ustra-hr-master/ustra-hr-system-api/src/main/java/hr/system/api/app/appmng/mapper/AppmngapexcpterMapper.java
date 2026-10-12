package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapexcpterDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngapexcpterMapper {
    List<CamelCaseMap> findAppmngapexcpterList(AppmngapexcpterDto appmngapexcpterDto);
    List<CamelCaseMap> findappmngapAddPointList(AppmngapexcpterDto appmngapexcpterDto);

    int checkApexcpterList(AppmngapexcpterDto appmngapexcpterDto) ;
    int checkAddPintList(AppmngapexcpterDto appmngapexcpterDto);
    int checkApapprobj(AppmngapexcpterDto appmngapexcpterDto);

    int inserTargetAddPointList(AppmngapexcpterDto appmngapexcpterDto) ;
    int saveApexcpter(AppmngapexcpterDto appmngapexcpterDto);
    int insertApexcpter(AppmngapexcpterDto appmngapexcpterDto);
    int insertAddPint(AppmngapexcpterDto appmngapexcpterDto);
    int updateApexcpter(AppmngapexcpterDto appmngapexcpterDto);
    int updateAddPint(AppmngapexcpterDto appmngapexcpterDto);

    int deleteApexcpter(AppmngapexcpterDto appmngapexcpterDto);
    int deleteAddPint(AppmngapexcpterDto appmngapexcpterDto);
}
