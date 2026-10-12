package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappprogresspaMapper {

    List<CamelCaseMap> findAppaiappprogresspaList(AppaiappprogresspDto appaiappprogresspDto);
    List<CamelCaseMap> appaiappryearendpaList(AppaiappprogresspDto appaiappprogresspDto); // 중간점검 작성 확인여부

    // 평가자 상태 업데이트
    int updateApprserStatus(AppaiappprogresspDto appaiappprogresspDto);

}
