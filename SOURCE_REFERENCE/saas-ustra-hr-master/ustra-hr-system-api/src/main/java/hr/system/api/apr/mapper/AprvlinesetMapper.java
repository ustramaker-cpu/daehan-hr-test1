package hr.system.api.apr.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.*;
import hr.system.core.emp.psmstinfo.dto.PsmstrDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AprvlinesetMapper { // 결재선 라인

    List<CamelCaseMap> findDefaultAprvline(AprvlinesetDto aprvlinesetDto); // 기본 결재선 라인 조회
    List<CamelCaseMap> findAprvline(AprvlinesetDto aprvlinesetDto);        // 상신된 결재선 라인 조회
    List<AprvdetailDto> findAprvdetail(AprvdocmstrDto aprvdocmstrDto);     // 문서 상세 조회
    List<AprvviwerDto> findViewers(AprvdocmstrDto aprvdocmstrDto);         // 수신자 조회
    CamelCaseMap findAprvtransfer(AprvdetailDto aprvdetailDto);            // 대리결재자 조회
    String findDocNo(AprvlinesetDto aprvlinesetDto);                       // 문서 번호 (DocNo) 조회
    int checkMyApprovalTurn(AprvdetailDto aprvdetailDto);                  // 본인 결재 순서 check
    int checkCancelPossible(AprvdetailDto aprvdetailDto);                  // 회수 가능 여부 check
    int checkAprvdocmstr(AprvdocmstrDto aprvdocmstrDto);                   // 문서마스터 존재 여부 check
    int checkAprvdocmstrBfDocNo(AprvdocmstrDto aprvdocmstrDto);            // 재기안 전 기존문서 존재 여부 check
    int insertAprvdocmstr(AprvdocmstrDto aprvdocmstrDto);                  // 문서마스터 저장
    int insertAprvdetail(AprvdetailDto aprvdetailDto);                     // 결재선 라인 저장
    int insertAprvviewer(AprvdetailDto aprvdetailDto);                     // 수신자 저장
    int updateAprvdocmstrAprvPathOrder(AprvdocmstrDto aprvdocmstrDto);     // 결재라인이 수정될 경우 마스터 path order도 수정
    int updateAprvdocmstrStatus(AprvdocmstrDto aprvdocmstrDto);            // 문서마스터 상태 수정
    int updateAprvdetailStatus(AprvdetailDto aprvdetailDto);               // 결재선 라인 상태 수정
    int updateAprvdocmstrBfDocNo(AprvdocmstrDto aprvdocmstrDto);           // 재기안 시 기존 문서번호 상태 업데이트
    int deleteAprvdetail(AprvdocmstrDto aprvdocmstrDto);                   // 결재선 라인 삭제
    int deleteAprvviewer(AprvdocmstrDto aprvdocmstrDto);                   // 수신자 삭제
    void deleteAprvmstr(AprvdocmstrDto aprvdocmstrDto);                    // 결재 라인 삭제
    int checkCreate(AprvdetailDto aprvdetailDto);                          // 기안자 확인
    PsmstrDto findPsmstinfo(PsmstrDto psmstrDto);                       // 매일 발송용 정보

    CamelCaseMap findAprvform(AprvlinesetDto aprvlinesetDto);

    List<CamelCaseMap> findAprvconfirmAprvline(AprvlinesetDto aprvlinesetDto);

    List<CamelCaseMap> findAprvPopOrgChart(AprvPopDto aprvPopDto);
    List<CamelCaseMap> findAprvPopEmplBySearch(AprvPopDto aprvPopDto);
    List<CamelCaseMap> findAprvPopEmplByOrg(AprvPopDto aprvPopDto);

    int checkIfLeader(AprvdetailDto aprvdetailDto);                         // 조직장 여부 확인

    List<CamelCaseMap> findDocAprvdetailList(AprvdocmstrDto aprvdocmstrDto); // 결재선 결재의견 리스트 조회

}
