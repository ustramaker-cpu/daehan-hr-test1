package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.*;
import hr.system.api.app.appmng.dto.AppmngapgradeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappryearendMapper {

    List<CamelCaseMap> findAppaiappryearendList(AppaiappryearendDto appaiappryearendDto);
    List<CamelCaseMap> findApprcapaList(AppaiappryearendDto appaiappryearendDto);

    List<CamelCaseMap> findGradeList(AppmngapgradeDto appmngapgradeDto);
    List<CamelCaseMap> getAppraiserind(AppaiappryearendDto appaiappryearendDto);
    List<CamelCaseMap> checkApprserList(AppaiappryearendDto appaiappryearendDto);

    int checkAptask(AppaiappryearendDto appaiappryearendDto);
    int checkInsertAptask(AppaiappryearendDto appaiappryearendDto);
    int checkInsertAptlkrsltp(AppaiappryearendDto appaiappryearendDto);

    int insertAptaskp(AppaiappryearendDto appaiappryearendDto);   // 개인과업 테이블
    int insertAptlkrsltp(AppaiappryearendDto appaiappryearendDto);// 평가의견 테이블
    int updateAptaskp(AppaiappryearendDto appaiappryearendDto);   // 개인과업 테이블
    int updateAptlkrsltp(AppaiappryearendDto appaiappryearendDto);// 평가의견 테이블

    int checkApcapaappr(AppaiappryearendDto appaiappryearendDto);   // 역량 테이블
    int insertApcapaappr(AppaiappryearendDto appaiappryearendDto);   // 역량 테이블
    int updateApcapaappr(AppaiappryearendDto appaiappryearendDto);   // 역량 테이블

    int updateSubmitStatus(AppaiappryearendDto appaiappryearendDto); // 전송

    int checkCountNo(AppaiappryearendDto appaiappryearendDto);
    int checkYearSeqNo(AppaiappryearendDto appaiappryearendDto);

    int insertAppryearend(AppaiappryearendDto appaiappryearendDto);
    int deleteAppryearend(AppaiappryearendDto appaiappryearendDto);

    List<CamelCaseMap> checkInd(AppaiappryearendDto appaiappryearendDto); // 오픈여부, 최종마감여부

    /**
     * 평가항목 수정(추진결과 및 성과 수정)
     * @param appaiappryearendDto
     * @return
     */
    int apapprcapaUpdate(AppaiappryearendDto appaiappryearendDto);

    /**
     * 항목별 평가점수 저장
     * @param appaiappryearendDto
     * @return
     */
    int apapprpointSave(AppaiappryearendDto appaiappryearendDto);

    /**
     * 평가요소별 비중 가져오기
     * @param appaiappryearendDto
     * @return
     */
    double apfactwgtWeight(AppaiappryearendDto appaiappryearendDto);

    /**
     * 평가자별 평가정보 저장
     * @param appaiappryearendDto
     * @return
     */
    int appraiserUpdate(AppaiappryearendDto appaiappryearendDto);

    /**
     * 평가대상자 테이블 업데이트(전송여부)
     * @param appaiappryearendDto
     * @return
     */
    int apapprindSendIndYn(AppaiappryearendDto appaiappryearendDto);
}
