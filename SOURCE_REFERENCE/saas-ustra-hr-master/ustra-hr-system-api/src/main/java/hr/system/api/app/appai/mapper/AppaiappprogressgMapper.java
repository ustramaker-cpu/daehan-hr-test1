package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappprogressgMapper {

    List<CamelCaseMap> findAppaiappprogressgList(AppaiappprogressgDto appaiappprogressgDto);

    // 오픈 여부 업데이트
    int updatAppprogressgOpenInd(AppaiappprogressgDto appaiappprogressgDto);
    // 평가마감 여부 업데이트
    int updatAppprogressgCloseInd(AppaiappprogressgDto appaiappprogressgDto);
    // 최종마감 여부 업데이트
    int updatAppprogressgCloseIndLast(AppaiappprogressgDto appaiappprogressgDto);

}
