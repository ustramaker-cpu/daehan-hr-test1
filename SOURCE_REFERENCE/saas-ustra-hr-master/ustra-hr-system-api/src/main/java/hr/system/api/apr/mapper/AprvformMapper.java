package hr.system.api.apr.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvformDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AprvformMapper {

    List<CamelCaseMap> findAprvformList(AprvformDto aprvformDto); // 문서양식관리 리스트 조회
    List<CamelCaseMap> findApprovalAprformList (AprvformDto aprvformDto); // 결재할 문서 종류 조회
    List<CamelCaseMap> findEmpAprvformList (AprvformDto aprvformDto); // 임직원 문서 종류 조회
    int insertAprvform(AprvformDto aprvformDto);                 // 문서양식관리 추가
    int updateAprvform(AprvformDto aprvformDto);                 // 문서양식관리 수정
    int deleteAprvform(AprvformDto aprvformDto);                 // 문서양식관리 삭제
    int updateAprvformDepthYn(AprvformDto aprvformDto);          // 결재선설정 신청서 저장

    int checkExistApprover(AprvformDto row);
}
