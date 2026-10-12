package hr.system.api.apr.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvdocmstrDto;
import hr.system.api.apr.dto.WctimedescDto;
import hr.system.api.doc.docappr.dto.WcstopreqDto;
import hr.system.api.doc.docappr.dto.WcwrkdescreqDto;
import hr.system.api.edu.edumil.dto.EduclreqDto;
import hr.system.api.edu.edumil.dto.EdursltDto;
import hr.system.api.empmenu.edumil.dto.EdureqdocDto;
import hr.system.api.uhr.docappr.dto.WctimedescreqDto;
import hr.system.api.uhr.docappr.dto.*;
import hr.system.api.wrk.wrkwork.dto.WcotmdescDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AprvcompleteMapper {
    //최종 결재완료 시
    List<WclvereqDto> findWclvereqList(AprvdocmstrDto aprvdocmstrDto); //휴가신청서 내용
    List<WctrpreqDto> findWctrpreqList(AprvdocmstrDto aprvdocmstrDto); //출장신청서 내용
    List<WclvecnclreqDto> findWclvecnclreqList(AprvdocmstrDto aprvdocmstrDto); //휴가취소신청서 내용
    List<WctrpcnclreqDto> findWctrpcnclreqList(AprvdocmstrDto aprvdocmstrDto); //출장취소신청서 내용
    List<WcplanweekreqDto> findWcplanweekreqList(AprvdocmstrDto aprvdocmstrDto); //유연근무신청서 내용
    List<WcflexreqDto> findApprflexreqList(AprvdocmstrDto aprvdocmstrDto); //유연근무신청서 내용
    List<WcstopreqDto> findWcstopreqList(AprvdocmstrDto aprvdocmstrDto); //휴,복직 신청서 내용
    WcreducedreqDto findWcreducedreqList(AprvdocmstrDto aprvdocmstrDto); //단축근로 신청서 내용
    int checkHoli(WcplanweekreqDto wcplanweekreqDto); //휴일 구분


    List<WcotmreqDto> findWcotmreqList(AprvdocmstrDto aprvdocmstrDto); //초과근무신청서 내용
    int insertWcotmdesc(WcotmreqDto wcotmreqDto);   // 초과근무내역 저장
    List<WcotmcnclreqDto> findWcotmcnclreqList(AprvdocmstrDto aprvdocmstrDto); // 초과근무취소신청서 내용
    int deleteWcotmdesc(WcotmdescDto wcotmdescDto); // 초과근무신청결과 삭제
    List<WcworktmchngreqDto> findWcworktmchngreqList(AprvdocmstrDto aprvdocmstrDto); // 출퇴근변경신청서 내용
    List<CndocreqDto> findCndocreqList(AprvdocmstrDto aprvdocmstrDto); // 경조금신청서 내용
    List<WcholpayreqDto> findWcholpayreqList(AprvdocmstrDto aprvdocmstrDto); // 휴일수당지급신청서 내용

    List<WclvecnclsubholiDto> findWccnclsubholieqList(AprvdocmstrDto aprvdocmstrDto); // 대체휴가취소신청서 내용

    List<WcovtmpayreqDto> findWcovtmpayreqList(AprvdocmstrDto aprvdocmstrDto); // 근태수당신청서 내용

    List<WcplanschreqDto> findWcplanschreqList(AprvdocmstrDto aprvdocmstrDto); // 월근무계획신청서 내용
    List<WcwrkdescreqDto> chkWcwrkdescList(WcwrkdescreqDto wcwrkdescreqDto); //연간근무내역 중복데이터 확인용 리스트
    int chkWcwrkdesc(WcwrkdescreqDto wcwrkdescreqDto); //연간근무내역 중복데이터 확인
    int insertWcwrkdesc(WcwrkdescreqDto wcwrkdescreqDto); //연간근무내역 저장
    int deleteWcwrkdesc(WcwrkdescreqDto wcwrkdescreqDto); //연간근무내역 삭제

    List<WrkreqDto> findWrkreqList(AprvdocmstrDto aprvdocmstrDto);

    List<WctimedescreqDto> findWctimedescreqDtoList(AprvdocmstrDto aprvdocmstrDto);

    int insertWrk(WrkreqDto wrkreqDto);

    int insertApprcn600(WctimedescreqDto wctimedescreqDto);

    int insertWcreqToPlan(WcplanschreqDto wcplanschreqDto);
    int deleteWcplandesc(WcplanschreqDto wcplanschreqDto); // 근무계획일정 삭제
    int deleteWcwrkdescForHolipay(WcwrkdescreqDto wcwrkdescreqDto); //연간근무내역 삭제(대체휴가)
    int updateWclverplcreq(WclvecnclsubholiDto wclvecnclsubholiDto); // 대체휴가취소신청 결재완료 시 대체근무휴가신청서 수정
    int deleteWcwrkdescForCnclsubholi(WcwrkdescreqDto wcwrkdescreqDto);  // 대체휴가취소신청 결재완료 시 연간근무내역 삭제(대체휴가)
    
    String getWcwrkSeq(WcwrkdescreqDto wcwrkdescreqDto); //출장취소 시 연간근무내역의 시퀀스 값 가져오기
    int updateWctrpreq(WcwrkdescreqDto wcwrkdescreqDto); //출장취소 시 출장신청 테이블 취소여부 업데이트

    int chkWcplandesc(WcplanweekreqDto wcplanweekreqDto); // 유연근무신청 결재완료 시 근무계획일정 테이블 중복 확인
    int deletePlandesc(WcplanweekreqDto wcplanweekreqDto); // 유연근무신청 결재완료 시 근무계획일정 테이블 중복 확인 후 해당 데이터 삭제

    int insertWcplandesc(WcplanweekreqDto wcplanweekreqDto); // 유연근무신청 결재완료 시 근무계획일정 저장

    int insertFlexForWcplandesc(WcflexreqDto wcflexreqDto); // 유연근무신청 결재완료 시 근무계획일정 저장

    int insertReducedForWcplandesc(List<WcreducedreqDto> wcreducedreqDto); // 단축근무신청 결재완료 시 근무계획일정 저장

    int insertStopForWcwrkdesc(WcstopreqDto wcstopreqDto);

    int chkWctimedesc(WctimedescDto wctimedescDto); // 출퇴근변경신청 결재완료 시 출퇴근기록내역 테이블 중복 확인
    int insertWctimedesc(WctimedescDto wctimedescDto); // 출퇴근변경신청 결재완료 시 출퇴근기록내역 저장
    int updateWctimedesc(WctimedescDto wctimedescDto); // 출퇴근변경신청 결재완료 시 출퇴근기록내역 수정
    int deleteWcworkchn(WctimedescDto wctimedescDto); // 출퇴근변경신청 결재완료 시 (deletYn = Y) 기록 삭제


    int insertCnreq(CndocreqDto cndocreqDto); // 경조금신청 결재완료 시 복리후생 경조금신청 테이블에 저장
    int insertCnmstr(AprvdocmstrDto aprvdocmstrDto);

    int checkDupl(WcotmreqDto wcotmreqDto); // 초과근무신청 중복체크
    int checkDuplByOvtmpay(WcovtmpayreqDto wcovtmpayreqDto); // 근태수당신청서 중복 체크
    int checkDuplByCnclsubholi(WclvecnclsubholiDto wclvecnclsubholiDto); // 대체휴가신청서 중복 체크

    int updateWcotmreq(AprvdocmstrDto aprvdocmstrDto); // 초과근무신청서 지급구분 칼럼 수정

    List<WclvereqDto> findWclverplcreqList(AprvdocmstrDto dto);  //대체휴가신청서 내용
    String getCloseInd(WcwrkdescreqDto wcwrkdescreqDto);
    String getCloseExptInd(WcwrkdescreqDto wcwrkdescreqDto);

    List<EduclreqDto> findEduclreqList(AprvdocmstrDto aprvdocmstrDto); // 교육취소신청서 내용
    List<EdureqdocDto> findEdureqdocDtoList(AprvdocmstrDto aprvdocmstrDto); // 교육신청서 내용
    List<EdursltDto> findEdursltList(AprvdocmstrDto aprvdocmstrDto); // 교육결과보고서 내용
    int updateEdureq(EduclreqDto educlreqDto); // 교육신청서 취소여부 수정
    int updateEdurslt(EdursltDto edursltDto); // 교육신청서 교육결과보고서 작성여부 수정
    String getWrkCodePayInfo(WcwrkdescreqDto wcwrkdescreqDto); //무급여부 확인

    CamelCaseMap getWcPlanDesc(WcwrkdescreqDto wcwrkdescreqDto);            //신청한 유연근무 근무유형별 전일 시간 SET
    CamelCaseMap getFullDayTimeInWrkmng(WcwrkdescreqDto wcwrkdescreqDto);   //하루별 근무유형별 전일 시간 SET

    int updateWclverecv(WcwrkdescreqDto wcwrkdescreqDto); // 리프레쉬휴가 휴가개수 저장
    Integer selectRemainLsDays(WcwrkdescreqDto wcwrkdescreqDto);

    List<WcanlreqDto> findWcanlreqList(AprvdocmstrDto aprvdocmstrDto); // 지각/조퇴/외출/결근신청 내용
    int checkDuplByAnlreq(WcanlreqDto wcanlreqDto); // 지각/조퇴/외출/결근신청 중복체크
    int insertWcanldesc(WcanlreqDto wcanlreqDto);   // 지각/조퇴/외출/결근신청 저장

    List<WcanlcnclreqDto> findWcanlcnclreqList(AprvdocmstrDto aprvdocmstrDto); // 지각/조퇴/외출/결근취소신청 조회

    boolean isHoliday(WcwrkdescreqDto wcwrkdescreqDto); // 주말/휴일 여부 체크
}
