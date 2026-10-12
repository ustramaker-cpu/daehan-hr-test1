package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistpDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappobjlistpMapper {

    List<CamelCaseMap> findAppaiappobjlistpList(AppaiappobjlistpDto appaiappobjlistpDto);

    int checkApexcpter(AppaiappobjlistpDto appaiappobjlistpDto);
    int checkApapprser(AppaiappobjlistpDto appaiappobjlistpDto);

    int insertApapprobj(AppaiappobjlistpDto appaiappobjlistpDto);
    // int insertApobjdtl(AppaiappobjlistpDto appaiappobjlistpDto);
    int insertApapprser(AppaiappobjlistpDto appaiappobjlistpDto);

    int updateApapprobj(AppaiappobjlistpDto appaiappobjlistpDto);
    // int updateApobjdtl(AppaiappobjlistpDto appaiappobjlistpDto);
    int updateApapprser(AppaiappobjlistpDto appaiappobjlistpDto);

    int deleteApapprobj(AppaiappobjlistpDto appaiappobjlistpDto);
    // int deleteApobjdtl(AppaiappobjlistpDto appaiappobjlistpDto);
    int deleteApapprser(AppaiappobjlistpDto appaiappobjlistpDto);

    // 대상자 확정 업데이트
    int updateApapprobjConfirm(AppaiappobjlistpDto appaiappobjlistpDto);
    // 담당 확인 업데이트
    int updateApapprobjConfirmSend(AppaiappobjlistpDto appaiappobjlistpDto);


}
