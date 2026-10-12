package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistsDto;
import hr.system.api.app.appmng.dto.AppmngapaprswgtDto;
import hr.system.api.wrk.wrkleav.dto.WcwrkdescDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappobjlistsMapper {

    List<CamelCaseMap> findAppaiappobjlistsList(AppaiappobjlistsDto AppaiappobjlistsDto);

    int checkApexcpter(AppaiappobjlistsDto AppaiappobjlistsDto);
    int checkApapprser(AppaiappobjlistsDto AppaiappobjlistsDto);

    int insertApapprobj(AppaiappobjlistsDto AppaiappobjlistsDto);
    // int insertApobjdtl(AppaiappobjlistsDto AppaiappobjlistsDto);
    int insertApapprser(AppaiappobjlistsDto AppaiappobjlistsDto);

    int updateApapprobj(AppaiappobjlistsDto AppaiappobjlistsDto);
    // int updateApobjdtl(AppaiappobjlistsDto AppaiappobjlistsDto);
    int updateApapprser(AppaiappobjlistsDto AppaiappobjlistsDto);

    int deleteApapprobj(AppaiappobjlistsDto AppaiappobjlistsDto);
    // int deleteApobjdtl(AppaiappobjlistsDto AppaiappobjlistsDto);
    int deleteApapprser(AppaiappobjlistsDto AppaiappobjlistsDto);

    // 대상자 확정 업데이트
    int updateApapprobjConfirm(AppaiappobjlistsDto AppaiappobjlistsDto);
    // 담당 확인 업데이트
    int updateApapprobjConfirmSend(AppaiappobjlistsDto AppaiappobjlistsDto);


}
