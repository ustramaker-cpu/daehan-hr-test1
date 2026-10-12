package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappryearendgMapper {

    List<CamelCaseMap> findAppaiappryearendgList(AppaiappryearendgDto appaiappryearendgDto);

    List<CamelCaseMap> checkInd(AppaiappryearendgDto appaiappryearendgDto); // 오픈여부, 최종마감여부
    List<CamelCaseMap> checkApprserList(AppaiappryearendgDto appaiappryearendgDto);

    int checkCountNo(AppaiappryearendgDto appaiappryearendgDto);
    int checkYearSeqNo(AppaiappryearendgDto appaiappryearendgDto);

    int insertAppryearendg(AppaiappryearendgDto appaiappryearendgDto);
    int updateAppryearendg(AppaiappryearendgDto appaiappryearendgDto);
    int updateSubmitStatus(AppaiappryearendgDto appaiappryearendgDto); // 전송
    int deleteAppryearendg(AppaiappryearendgDto appaiappryearendgDto);
}
