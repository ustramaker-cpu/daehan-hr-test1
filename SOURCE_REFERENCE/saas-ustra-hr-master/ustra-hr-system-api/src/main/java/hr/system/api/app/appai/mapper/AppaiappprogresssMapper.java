package hr.system.api.app.appai.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gsitm.framework.core.type.CamelCaseMap;

import hr.system.api.app.appai.dto.AppaiappprogresssDto;

@Mapper
public interface AppaiappprogresssMapper {

    List<CamelCaseMap> findAppaiappprogresssList(AppaiappprogresssDto appaiappprogresssDto);

    // 오픈 여부 업데이트
    int updatAppprogresssOpenInd(AppaiappprogresssDto appaiappprogresssDto);
    // 평가마감 여부 업데이트
    int updatAppprogresssCloseInd(AppaiappprogresssDto appaiappprogresssDto);
    // 최종마감 여부 업데이트
    int updatAppprogresssCloseIndLast(AppaiappprogresssDto appaiappprogresssDto);

}
