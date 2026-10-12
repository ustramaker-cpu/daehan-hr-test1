package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappryearendyMapper {

    List<CamelCaseMap> findAppaiappryearendyList(AppaiappryearendyDto appaiappryearendyDto);
    List<CamelCaseMap> checkApprserList(AppaiappryearendyDto appaiappryearendyDto);
    List<CamelCaseMap> checkApprserAllList(AppaiappryearendyDto appaiappryearendyDto);
    List<CamelCaseMap> checkInd(AppaiappryearendyDto appaiappryearendyDto); // 평가자마감여부

    List<CamelCaseMap> getOrgcode(AppaiappryearendyDto appaiappryearendyDto);

    int checkAptask(AppaiappryearendyDto appaiappryearendyDto);
    int checkInsertAptask(AppaiappryearendyDto appaiappryearendyDto);
    int checkInsertAptlkrslty(AppaiappryearendyDto appaiappryearendyDto);

    int insertAptasky(AppaiappryearendyDto appaiappryearendyDto);   // 개인과업 테이블
    int insertAptlkrslty(AppaiappryearendyDto appaiappryearendyDto);// 평가의견 테이블
    int updateAptasky(AppaiappryearendyDto appaiappryearendyDto);   // 개인과업 테이블
    int updateAptlkrslty(AppaiappryearendyDto appaiappryearendyDto);// 평가의견 테이블

    int deleteAptasky(AppaiappryearendyDto appaiappryearendyDto);   // 개인과업 테이블

    int updateSubmitStatus(AppaiappryearendyDto appaiappryearendyDto); // 전송
}
