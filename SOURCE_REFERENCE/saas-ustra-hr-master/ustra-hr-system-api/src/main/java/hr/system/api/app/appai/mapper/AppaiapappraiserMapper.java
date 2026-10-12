package hr.system.api.app.appai.mapper;

import hr.system.api.app.appai.dto.AppaiapappraiserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppaiapappraiserMapper {

//    List<CamelCaseMap> findAppaiappprogressgaList(AppaiappprogressgDto appaiappprogressgDto);

    // 목표승인
    int updateApappraiserInd(AppaiapappraiserDto appaiapappraiserDto);

}
