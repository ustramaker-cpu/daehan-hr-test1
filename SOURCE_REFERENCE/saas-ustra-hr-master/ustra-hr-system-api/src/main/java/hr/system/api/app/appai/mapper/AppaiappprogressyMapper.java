package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappprogressyMapper {

    List<CamelCaseMap> findAppaiappprogressyList(AppaiappprogressyDto appaiappprogressyDto);

    int checkApprObj(AppaiappprogressyDto appaiappprogressyDto);

    // 오픈 여부 업데이트
    int updatAppprogresspOpenInd(AppaiappprogressyDto appaiappprogressyDto);
    // 평가마감 여부 업데이트
    int updatAppprogresspCloseInd(AppaiappprogressyDto appaiappprogressyDto);
    // 최종마감 여부 업데이트
    int updatAppprogresspCloseIndLast(AppaiappprogressyDto appaiappprogressyDto);

    int updatAppprogresspGrouping(AppaiappprogressyDto appaiappprogressyDto);
    //그룹핑 업데이트
    int insertAplstpnt(AppaiappprogressyDto appaiappprogressyDto);

    int insertAplstpnt2(AppaiappprogressyDto appaiappprogressyDto);

    int deleteAplstpnt(AppaiappprogressyDto appaiappprogressyDto);

    /**
     * 연말평가진행관리(담당자) 최종마감&마감취소
     * @param appaiappprogressyDto
     * @return
     */
    int closeIndYn(AppaiappprogressyDto appaiappprogressyDto);

}
