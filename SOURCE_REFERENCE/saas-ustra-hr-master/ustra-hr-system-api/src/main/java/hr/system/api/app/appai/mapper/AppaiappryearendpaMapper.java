package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendpDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappryearendpaMapper {

    List<CamelCaseMap> findAppaiappryearendpaList(AppaiappryearendpDto appaiappryearendpDto);
    List<CamelCaseMap> checkApprserList(AppaiappryearendpDto appaiappryearendpDto);

    List<CamelCaseMap> checkInd(AppaiappryearendpDto appaiappryearendpDto); // 평가자마감여부

    int checkAptask(AppaiappryearendpDto appaiappryearendpDto);
    int checkInsertAptask(AppaiappryearendpDto appaiappryearendpDto);
    int checkInsertAptlkrsltp(AppaiappryearendpDto appaiappryearendpDto);

    int insertAptaskp(AppaiappryearendpDto appaiappryearendpDto);   // 개인과업 테이블
    int insertAptlkrsltp(AppaiappryearendpDto appaiappryearendpDto);// 평가의견 테이블
    int updateAptaskp(AppaiappryearendpDto appaiappryearendpDto);   // 개인과업 테이블
    int updateAptlkrsltp(AppaiappryearendpDto appaiappryearendpDto);// 평가의견 테이블

}
