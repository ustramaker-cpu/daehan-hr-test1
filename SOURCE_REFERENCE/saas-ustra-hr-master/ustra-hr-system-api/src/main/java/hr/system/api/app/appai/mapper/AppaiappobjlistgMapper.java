package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appmng.dto.AppmngapaprswgtDto;
import hr.system.api.wrk.wrkleav.dto.WcwrkdescDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappobjlistgMapper {

    List<CamelCaseMap> findAppaiappobjlistgList(AppaiappobjlistgDto appaiappobjlistgDto);

    int checkApexcpter(AppaiappobjlistgDto appaiappobjlistgDto);
    int checkApapprser(AppaiappobjlistgDto appaiappobjlistgDto);

    int insertApapprobj(AppaiappobjlistgDto appaiappobjlistgDto);
    // int insertApobjdtl(AppaiappobjlistgDto appaiappobjlistgDto);
    int insertApapprser(AppaiappobjlistgDto appaiappobjlistgDto);

    int updateApapprobj(AppaiappobjlistgDto appaiappobjlistgDto);
    // int updateApobjdtl(AppaiappobjlistgDto appaiappobjlistgDto);
    int updateApapprser(AppaiappobjlistgDto appaiappobjlistgDto);

    int deleteApapprobj(AppaiappobjlistgDto appaiappobjlistgDto);
    // int deleteApobjdtl(AppaiappobjlistgDto appaiappobjlistgDto);
    int deleteApapprser(AppaiappobjlistgDto appaiappobjlistgDto);

    // 대상자 확정 업데이트
    int updateApapprobjConfirm(AppaiappobjlistgDto appaiappobjlistgDto);
    // 담당 확인 업데이트
    int updateApapprobjConfirmSend(AppaiappobjlistgDto appaiappobjlistgDto);


}
