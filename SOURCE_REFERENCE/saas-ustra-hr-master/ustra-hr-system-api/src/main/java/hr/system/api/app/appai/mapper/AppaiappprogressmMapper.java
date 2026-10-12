package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressmDto;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappprogressmMapper {

    List<CamelCaseMap> findAppaiappprogressmList(AppaiappprogressmDto appaiappprogressmDto);

    // 평가대상자 오픈 업데이트
    int updatAppprogressmOpenInd(AppaiappprogressmDto appaiappprogressmDto);
    // 평가대상자 평가마감 업데이트
    int updatAppprogressmCloseInd(AppaiappprogressmDto appaiappprogressmDto);
    // 평가대상자 최종마감 업데이트
    int updatAppprogressmCloseIndLast(AppaiappprogressmDto appaiappprogressmDto);

}
