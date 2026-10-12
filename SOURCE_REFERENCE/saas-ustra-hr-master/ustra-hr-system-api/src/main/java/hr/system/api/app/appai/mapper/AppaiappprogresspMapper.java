package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappprogresspMapper {

    List<CamelCaseMap> findAppaiappprogresspList(AppaiappprogresspDto appaiappprogresspDto);

    // 오픈 여부 업데이트
    int updatAppprogresspOpenInd(AppaiappprogresspDto appaiappprogresspDto);
    // 평가마감 여부 업데이트
    int updatAppprogresspCloseInd(AppaiappprogresspDto appaiappprogresspDto);
    // 최종마감 여부 업데이트
    int updatAppprogresspCloseIndLast(AppaiappprogresspDto appaiappprogresspDto);

}
