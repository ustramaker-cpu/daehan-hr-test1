package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.dto.AppaiappobjlistmDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappobjlistmMapper {

    List<CamelCaseMap> findAppaiappobjlistmObjList(AppaiappobjlistmDto appaiappobjlistmDto); // 평가대상자 조회
    List<CamelCaseMap> findAppaiappobjlistmSerList(AppaiappobjlistmDto appaiappobjlistmDto); // 평가자 조회
    
    int checkObjExcel(AppaiappobjlistmDto appaiappobjlistmDto); // 평가자 엑셀업로드 시 체크

    // 평가대상자관련
    int insertApapprobj(AppaiappobjlistmDto appaiappobjlistmDto);
    int updateApapprobj(AppaiappobjlistmDto appaiappobjlistmDto);
    int deleteApapprobj(AppaiappobjlistmDto appaiappobjlistmDto);
    int deleteApapprsermAll(AppaiappobjlistmDto appaiappobjlistmDto);

    // 평가자관련
    int insertApapprserm(AppaiappobjlistmDto appaiappobjlistmDto);
    int deleteApapprserm(AppaiappobjlistmDto appaiappobjlistmDto);

    // 평가대상자 확정
    int updateApapprobjConfirm(AppaiappobjlistmDto appaiappobjlistmDto);
    // 평가대상자 담당확인
    int updateApapprobjConfirmSend(AppaiappobjlistmDto appaiappobjlistmDto);

}
