package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappryearendyaMapper {

    List<CamelCaseMap> findAppaiappryearendyaList(AppaiappryearendyDto appaiappryearendyDto);
    List<CamelCaseMap> checkApprserList(AppaiappryearendyDto appaiappryearendyDto);
    List<CamelCaseMap> checkApprserList2(AppaiappryearendyDto appaiappryearendyDto);

    List<CamelCaseMap> checkInd(AppaiappryearendyDto appaiappryearendyDto); // 평가자마감여부

    List<CamelCaseMap> getGrade(AppaiappryearendyDto appaiappryearendyDto); //평가등급 가져오기..


    List<CamelCaseMap> getTotPoint(AppaiappryearendyDto appaiappryearendyDto); //학원 배분총점 가져오기..

    CamelCaseMap getWorkEvaluation(AppaiappryearendyDto appaiappryearendyDto); //평가등급 가져오기..

    List<CamelCaseMap> getEvaluation(AppaiappryearendyDto appaiappryearendyDto); //평가등급 가져오기..

    List<CamelCaseMap> getPoint(AppaiappryearendyDto appaiappryearendyDto);

    List<CamelCaseMap> getAvgPoint(AppaiappryearendyDto appaiappryearendyDto);

    //평가요소별 비중 가져오기..
    CamelCaseMap getFactwgt(AppaiappryearendyDto appaiappryearendyDto);
    //평가요소별 비중 가져오기..
    CamelCaseMap getAprswgt(AppaiappryearendyDto appaiappryearendyDto);

    int checkAptask(AppaiappryearendyDto appaiappryearendyDto);
    int checkInsertAptask(AppaiappryearendyDto appaiappryearendyDto);
    int checkInsertAptlkrslty(AppaiappryearendyDto appaiappryearendyDto);

    int insertAptasky(AppaiappryearendyDto appaiappryearendyDto);   // 개인과업 테이블
    int insertAptlkrslty(AppaiappryearendyDto appaiappryearendyDto);// 평가의견 테이블
    int updateAptasky(AppaiappryearendyDto appaiappryearendyDto);   // 개인과업 테이블
    int updateAptlkrslty(AppaiappryearendyDto appaiappryearendyDto);// 평가의견 테이블

    int checkInsertApcapaappry(AppaiappryearendyDto appaiappryearendyDto); // 연말평가 평점 체크
    int insertApcapaappry(AppaiappryearendyDto appaiappryearendyDto);   // 연말평가 평점 등록
    int updateApcapaappry(AppaiappryearendyDto appaiappryearendyDto);   // 연말평가 평점 update

    int updateSubmitStatus(AppaiappryearendyDto appaiappryearendyDto); // 전송

    int checkOrleader(AppaiappryearendyDto appaiappryearendyDto); // 부서장인지 부서원인지 체크

    int checkInsertAppointy(AppaiappryearendyDto appaiappryearendyDto); // 연말평가 평점 체크
    int insertAppointy(AppaiappryearendyDto appaiappryearendyDto);   // 연말평가 평점 등록
    int updateAppointy(AppaiappryearendyDto appaiappryearendyDto);   // 연말평가 평점 update

}