package hr.system.api.app.appai.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppaiappobjlistyMapper {

    List<CamelCaseMap> findAppaiappobjlistyList(AppaiappobjlistyDto appaiappobjlistyDto);

    List<CamelCaseMap> findAppaiappobjlistyListadmin(AppaiappobjlistyDto appaiappobjlistyDto);

    List<CamelCaseMap> findAppaiappobjlistyListEnd(AppaiappobjlistyDto appaiappobjlistyDto);

    int checkApexcpter(AppaiappobjlistyDto appaiappobjlistyDto);
    int checkApapprser(AppaiappobjlistyDto appaiappobjlistyDto);

    int insertApapprobj(AppaiappobjlistyDto appaiappobjlistyDto);
    int insertApapprser(AppaiappobjlistyDto appaiappobjlistyDto);

    int updateApapprobj(AppaiappobjlistyDto appaiappobjlistyDto);
    int updateApapprser(AppaiappobjlistyDto appaiappobjlistyDto);

    int deleteApapprobj(AppaiappobjlistyDto appaiappobjlistyDto);
    int deleteApapprser(AppaiappobjlistyDto appaiappobjlistyDto);
    int deleteApapprserSeq(AppaiappobjlistyDto appaiappobjlistyDto);

    // 대상자 확정 업데이트
    int updateApapprobjConfirm(AppaiappobjlistyDto appaiappobjlistyDto);
    // 담당 확인 업데이트
    int updateApapprobjConfirmSend(AppaiappobjlistyDto appaiappobjlistyDto);

    /**
     * 동록된 평가대상자인지 체크
     * @param appaiappobjlistyDto
     * @return
     */
    int selectApapprindEmplNoCheck(AppaiappobjlistyDto appaiappobjlistyDto);

    /**
     * 그리드 내 대상자 개별 성과 및 역량 저장
     * @param appaiappobjlistyDto
     * @return
     */
    int insertApapprcapa(AppaiappobjlistyDto appaiappobjlistyDto);

    /**
     * 그리드 내 대상자 개별 성과 및 역량 삭제
     * @param appaiappobjlistyDto
     * @return
     */
    int deleteApapprcapa(AppaiappobjlistyDto appaiappobjlistyDto);

    /**
     * 연말평가진행관리(평가자) 저장
     * @param appaiappobjlistyDto
     * @return
     */
    int appaiappprogressyaSave(AppaiappobjlistyDto appaiappobjlistyDto);


    /**
     * 연말평가진행관리(관리자) 최종평가등급 저장
     * @param appaiappobjlistyDto
     * @return
     */
    int apprSavePoint(AppaiappobjlistyDto appaiappobjlistyDto);
    
    //평가 반영 여부 Y로 업데이트
    int apprUpdApprInd(AppaiappobjlistyDto appaiappobjlistyDto);

    
}
