package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappprogressgaMapper {

    List<CamelCaseMap> findAppaiappprogressgaList(AppaiappprogressgDto appaiappprogressgDto);
    // 목표승인
    int update1stStatus(AppaiappprogressgDto appaiappprogressgDto);

}
