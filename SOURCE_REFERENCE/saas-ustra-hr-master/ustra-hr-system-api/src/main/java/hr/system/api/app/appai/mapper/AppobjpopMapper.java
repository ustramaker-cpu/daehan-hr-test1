package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.wrk.wrkleav.dto.WclvedayDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppobjpopMapper {

    // 대상사생성기준 조회
    String findApapprobjstandStandpoint(AppaiappobjlistgDto appaiappobjlistgDto);

    // 생성기준일자 조회
    List<CamelCaseMap> findAppobjBaseDate(AppaiappobjlistgDto appaiappobjlistgDto);

    // 대상자 생성
    List<CamelCaseMap> objTargetList(AppaiappobjlistgDto appaiappobjlistgDto);
    // 대상자 선정(프로시저)
    void insertObjTargetList(AppaiappobjlistgDto appaiappobjlistgDto);

    int checkApprobj(AppaiappobjlistgDto appaiappobjlistgDto);

    int deleteApapprobjBeforeTarget(AppaiappobjlistgDto appaiappobjlistgDto);
    // int deleteApobjdtlBeforeTarget(AppaiappobjlistgDto appaiappobjlistgDto);
    int deleteApapprserBeforeTarget(AppaiappobjlistgDto appaiappobjlistgDto);

}
