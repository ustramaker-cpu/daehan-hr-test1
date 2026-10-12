package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapaprswgtDto;
import hr.system.api.app.appmng.dto.AppmngapgradeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngapaprswgtMapper {

    List<CamelCaseMap> findAppmngapaprswgtList(AppmngapaprswgtDto appmngapaprswgtDto);

    int insertAppmngapaprswgt(AppmngapaprswgtDto appmngapaprswgtDto);
    int updateAppmngapaprswgt(AppmngapaprswgtDto appmngapaprswgtDto);
    int deleteAppmngapaprswgt(AppmngapaprswgtDto appmngapaprswgtDto);
}
