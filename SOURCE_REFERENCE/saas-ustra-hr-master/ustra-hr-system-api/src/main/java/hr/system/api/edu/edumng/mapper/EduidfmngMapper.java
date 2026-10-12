package hr.system.api.edu.edumng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfmngDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduidfmngMapper {

    List<CamelCaseMap> findEduidfmngList(EduidfmngDto eduidfmngDto);

    int insertEduidfmng(EduidfmngDto eduidfmngDto);
    int deleteEduidfmng(EduidfmngDto eduidfmngDto);

    // 대상자 생성
    int maketargetEduidfmng(EduidfmngDto eduidfmngDto);
    int deletetargetEduidfmng(EduidfmngDto eduidfmngDto);

    // 전송상태 업데이트 전 근무기간 체크
    EduidfmngDto checkSubmitEmpDay(EduidfmngDto eduidfmngDto);
    // 총 교육 시간 합계
    EduidfmngDto checkEduplanSum(EduidfmngDto eduidfmngDto);

    // 전송 상태 업데이트
    int updateStatusSubmit(EduidfmngDto eduidfmngDto);
    // 확인 상태 업데이트
    int updateStatusConfirm(EduidfmngDto eduidfmngDto);
    // 전송 취소, 확인 취소 (대기) 상태 업데이트
    int updateStatusCancel(EduidfmngDto eduidfmngDto);
}