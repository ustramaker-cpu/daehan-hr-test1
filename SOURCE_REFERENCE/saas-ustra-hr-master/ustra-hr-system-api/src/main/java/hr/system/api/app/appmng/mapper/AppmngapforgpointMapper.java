package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import hr.system.api.app.appmng.dto.AppmngapforgpointDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngapforgpointMapper {

    List<CamelCaseMap> appmngapfactpointList(AppmngapforgpointDto appmngapforgpointDto);

    AppmngapforgpointDto findAppmngapforgpointList(AppmngapforgpointDto appmngapforgpointDto);

    AppmngapforgpointDto findappmngapfactpointList(AppmngapforgpointDto appmngapforgpointDto);

    AppmngapforgpointDto findappmngapfactTotpointList(AppmngapforgpointDto appmngapforgpointDto);

    int insertapforgpoint(AppmngapforgpointDto appmngapforgpointDto);

    int updateapforgpoint(AppmngapforgpointDto appmngapforgpointDto);

    int deleteapforgpoint(AppmngapforgpointDto appmngapforgpointDto);
}
