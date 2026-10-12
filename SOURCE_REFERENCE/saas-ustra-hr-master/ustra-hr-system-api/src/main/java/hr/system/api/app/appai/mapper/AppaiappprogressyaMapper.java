package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistyDto;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface AppaiappprogressyaMapper {

    List<CamelCaseMap> findAppaiappprogressyaList(AppaiappprogressyDto appaiappprogressyDto);

    List<CamelCaseMap> getAVGTotalPoint(AppaiappprogressyDto appaiappprogressyDto);

    List<CamelCaseMap> getGradeCount(AppaiappprogressyDto appaiappprogressyDto);

    /**
     * 평가자 평가조직 조회
     * @param appaiappprogressyDto
     * @return
     */
    List<CamelCaseMap> getOrgleader(AppaiappprogressyDto appaiappprogressyDto);

    /**
     * 평가 조직이 학원인지 확인 여부
     *
     * @param appaiappprogressyDto
     * @return
     */
    int checkind(AppaiappprogressyDto appaiappprogressyDto);

    int checksend(AppaiappprogressyDto appaiappprogressyDto);

    List<CamelCaseMap> getApportion(AppaiappprogressyDto appaiappprogressyDto);

    // 오픈 여부 업데이트
    int updatAppprogresspaOpenInd(AppaiappprogressyDto appaiappprogressyDto);
    // 평가마감 여부 업데이트
    int updatAppprogresspaCloseInd(AppaiappprogressyDto appaiappprogressyDto);
    // 최종마감 여부 업데이트
    int updatAppprogresspaCloseIndLast(AppaiappprogressyDto appaiappprogressyDto);
    // 평가자 상태 업데이트(R)
    int update1stStatus(AppaiappprogressyDto appaiappprogressyDto);

    //전송
    int updateSubmitStatus(AppaiappprogressyDto appaiappprogressyDto); // 전송


    int updatePoint(AppaiappprogressyDto appaiappprogressyDto); // 학원 배분총점
    
    /**
     * 평가자 전송&전송취소
     * @param appaiappobjlistyDto
     * @return
     */
    int send(AppaiappobjlistyDto appaiappobjlistyDto);

    int clearApapprpoint(AppaiappobjlistyDto appaiappobjlistyDto);   // 역량업적평가 점수 초기화
    int clearAppraiser(AppaiappobjlistyDto appaiappobjlistyDto);    // 평가자테이블 점수 초기화
}
