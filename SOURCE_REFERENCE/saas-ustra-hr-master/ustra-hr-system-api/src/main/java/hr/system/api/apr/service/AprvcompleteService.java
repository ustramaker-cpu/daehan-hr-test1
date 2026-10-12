package hr.system.api.apr.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvdocmstrDto;
import hr.system.api.apr.dto.WctimedescDto;
import hr.system.api.doc.docappr.dto.WcstopreqDto;
import hr.system.api.edu.edumil.dto.EduclreqDto;
import hr.system.api.edu.edumil.dto.EdursltDto;
import hr.system.api.empmenu.edumil.dto.EdureqdocDto;
import hr.system.api.uhr.docappr.dto.WctimedescreqDto;
import hr.system.api.apr.mapper.AprvcompleteMapper;
import hr.system.api.doc.docappr.dto.WcwrkdescreqDto;
import hr.system.api.uhr.docappr.dto.*;
import hr.system.api.wrk.wrkwork.dto.WcotmdescDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AprvcompleteService { // 결재완료 서비스

    private final AprvcompleteMapper aprvcompleteMapper;
    public static final String NO_DATA_CANCEL = "해당날짜에 취소할 연간근무내역이 존재하지 않습니다. 근태내역을 확인해주세요.";
    public static final String ALREADY_DATA = "신청한 일자에 중복되는 근무가 있습니다. 근태내역을 확인해주세요.";

    /**
     * 대체휴가신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 휴가신청서 결재완료시 연간근무내역(Wcwrkdescreq)에 반영
     */
    public int completeApprshbholi(AprvdocmstrDto dto) throws ParseException { //to-do
        int result = 0;
        List<WclvereqDto> wclvereqList = aprvcompleteMapper.findWclverplcreqList(dto);

        if (CollectionUtils.isNotEmpty(wclvereqList)) {
            for (WclvereqDto wclvereq : wclvereqList) {
                // 휴가신청서
                String lveDays = wclvereq.getLveDays();                            // 휴가일수
                String emplNo = wclvereq.getEmplNo();           // 사번
                String lveDate = wclvereq.getLveDate();         // 대체휴가일자
                String lveCode = wclvereq.getLveCode();         // 휴가코드
                String lveGubun = wclvereq.getLveGubun();                         // 휴가구분
                String lveTimeFrom = wclvereq.getLveTimeFrom(); // 휴가 시작시간
                String lveTimeTo = wclvereq.getLveTimeTo();     // 휴가 종료시간
                String lveHours = wclvereq.getLveHours();       // 휴가시간

                // 연간근무내역
                WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto(); // 연간근무내역 dto
                wcwrkdescreqDto.setEmplNo(emplNo);              // 사번
                wcwrkdescreqDto.setDate(lveDate);           // 근태일
                wcwrkdescreqDto.setWorkCodeKind(lveCode);       // 근태코드
                wcwrkdescreqDto.setHalfdayCode(lveGubun);       // 전일, 반일 구분 코드
                wcwrkdescreqDto.setStartTime(lveTimeFrom);      // 시작시간
                wcwrkdescreqDto.setEndTime(lveTimeTo);          // 종료시간
                wcwrkdescreqDto.setHour(lveHours);              // 휴가사용시간
                wcwrkdescreqDto.setDays(lveDays);               // 일수



                boolean isFullDay = lveGubun.equals("01"); // 전일

                if (isFullDay) { // 휴가구분이 전일(1일 이상)
                    int len = Math.round(Float.parseFloat(lveDays)); // 휴가일수
                    for (int i = 0; i < len; i++) {
                        String date = dateFormatConvert(lveDate, i);
                        wcwrkdescreqDto.setDate(date); // 일자

                        // 주말/휴일 체크 - WCCALEND의 HOLIDAY_CODE가 있으면 스킵
                        if (aprvcompleteMapper.isHoliday(wcwrkdescreqDto)) {
                            continue;
                        }

                        // 230516 휴가시간 사용으로 주석처리
                        wcwrkdescreqDto.setStartTime(lveTimeFrom);
                        wcwrkdescreqDto.setEndTime(lveTimeTo);
                        result += applyWorkdesc(wcwrkdescreqDto);
                    }
                } else { // 휴가 일수가 1일 미만 : 각 시간 넣어줌
                    result += invalidApprsubholi(wcwrkdescreqDto) ? aprvcompleteMapper.insertWcwrkdesc(wcwrkdescreqDto) : 0;
                }
            }
        }
        return result;
    }

    /**
     * 휴가신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 휴가신청서 결재완료시 연간근무내역(Wcwrkdescreq)에 반영
     */
    public int completeApprholi(AprvdocmstrDto dto) throws ParseException {
        int result = 0;
        List<WclvereqDto> wclvereqList = aprvcompleteMapper.findWclvereqList(dto);

        if (CollectionUtils.isNotEmpty(wclvereqList)) {
            for (WclvereqDto wclvereq : wclvereqList) {
                // 휴가신청서
                String lveDays = wclvereq.getLveDays();         // 휴가일수
                String emplNo = wclvereq.getEmplNo();           // 사번
                String lveDateFrom = wclvereq.getLveDateFrom(); // 휴가시작일자
                String lveDateTo = wclvereq.getLveDateTo();     // 휴가종료일자
                String lveCode = wclvereq.getLveCode();         // 휴가코드
                String lveGubun = wclvereq.getLveGubun();       // 휴가구분
                String lveTimeFrom = wclvereq.getLveTimeFrom(); // 휴가 시작시간
                String lveTimeTo = wclvereq.getLveTimeTo();     // 휴가 종료시간
                String lveHours = wclvereq.getLveHours();       // 휴가시간

                // 연간근무내역
                WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto(); // 연간근무내역 dto
                wcwrkdescreqDto.setEmplNo(emplNo);              // 사번
                wcwrkdescreqDto.setDate(lveDateFrom);           // 근태일
                wcwrkdescreqDto.setWorkCodeKind(lveCode);       // 근태코드
                wcwrkdescreqDto.setHalfdayCode(lveGubun);       // 전일, 반일 구분 코드
                wcwrkdescreqDto.setStartTime(lveTimeFrom);      // 시작시간
                wcwrkdescreqDto.setEndTime(lveTimeTo);          // 종료시간
                wcwrkdescreqDto.setHour(lveHours);              // 휴가사용시간
                wcwrkdescreqDto.setDays(lveDays);               // 일수
                wcwrkdescreqDto.setLveDateFrom(lveDateFrom);    // 휴가시작일
                wcwrkdescreqDto.setLveDateTo(lveDateTo);        // 휴가종료일
                wcwrkdescreqDto.setLveDays(lveDays);               // 휴가일수

                //BigDecimal reqDays = new BigDecimal(lveDays);

                //근태코드 관리에서 무급여부  확인 후 SET
                wcwrkdescreqDto.setNoPayInd(aprvcompleteMapper.getWrkCodePayInfo(wcwrkdescreqDto));

                // 출산휴가 누적일수는 DB에서 조회(추후 필요시)
                int useLbDays = 0;

                boolean isFullDay = lveGubun.equals("01"); // 전일

                SimpleDateFormat sdfb = new SimpleDateFormat("yyyyMMdd");
                Date lveDateFromDt = sdfb.parse(lveDateFrom);
                Date lveDateToDt = sdfb.parse(lveDateTo);

                if (isFullDay) { // 휴가구분이 전일(1일 이상)
                    int len = (int) DAYS.between(lveDateFromDt.toInstant(), lveDateToDt.toInstant()) + 1;

                    for (int i = 0; i < len; i++) {
                        String date = dateFormatConvert(lveDateFrom, i);

                        // 날짜 세팅
                        wcwrkdescreqDto.setDate(date);
                        wcwrkdescreqDto.setLveDateFrom(date);
                        wcwrkdescreqDto.setLveDateTo(date);

                        // 주말/휴일 체크 - WCCALEND의 HOLIDAY_CODE가 있으면 스킵
                        if (aprvcompleteMapper.isHoliday(wcwrkdescreqDto)) {
                            continue;
                        }

                        wcwrkdescreqDto.setDays("1");   // 전일은 무조건 1일

                        // 리프레쉬휴가 있을 경우 먼저 차감. lveCode 리프레쉬휴가(LS)로 등록
                        int remainLsDays = aprvcompleteMapper.selectRemainLsDays(wcwrkdescreqDto);
                        if(lveCode.equals("LY")) {
                            if(0 < remainLsDays) {
                                wcwrkdescreqDto.setWorkCodeKind("LS");
                            }
                        }

                        CamelCaseMap workPlan = aprvcompleteMapper.getWcPlanDesc(wcwrkdescreqDto);

                        if (ObjectUtils.isNotEmpty(workPlan)) {
                            Float workHour = workPlan.getFloat("workHour");
                            if (workHour != null) {
                                BigDecimal hourVal = BigDecimal.valueOf(workHour);
                                wcwrkdescreqDto.setHour(hourVal.stripTrailingZeros().toPlainString());
                            }
                            wcwrkdescreqDto.setStartTime(workPlan.getString("startTime"));
                            wcwrkdescreqDto.setEndTime(workPlan.getString("endTime"));
                        }

                        // ===== 출산휴가 60일 초과 무급 처리 =====
                        if ("LB".equals(wcwrkdescreqDto.getWorkCodeKind())) {
                            int currentOrdinal = useLbDays + i + 1;
                            if (currentOrdinal > 60) {
                                wcwrkdescreqDto.setNoPayInd("Y"); // 61일차부터 무급
                            }
                        }

                        result += applyWorkdesc(wcwrkdescreqDto);
                    }

                } else { // 휴가 일수가 1일 미만 : 각 시간 넣어줌

                    result += invalidApprsubholi(wcwrkdescreqDto) ? aprvcompleteMapper.insertWcwrkdesc(wcwrkdescreqDto) : 0;
                }

                String workCodeKind = wcwrkdescreqDto.getWorkCodeKind();
                boolean wrkleavemng100Yn = "LS".equals(workCodeKind);   // 리프레쉬휴가인경우 USE_CNT UPDATE
                if(wrkleavemng100Yn){
                    aprvcompleteMapper.updateWclverecv(wcwrkdescreqDto);
                }
            }
        }
        if (result <= 0) {
            throw new ServiceException("연간근무내역 반영에 실패하였습니다.");
        }
        return result;
    }

    /**
     * 출장신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 출장신청서 결재완료시 연간근무내역(Wcwrkdescreq)에 반영
     */
    public int completeApprtrav(AprvdocmstrDto dto) throws ParseException {
        int result = 0;
        List<WctrpreqDto> wctrpreqList = aprvcompleteMapper.findWctrpreqList(dto);

        if (CollectionUtils.isNotEmpty(wctrpreqList)) {
            for (WctrpreqDto wctrpreq : wctrpreqList) {
                // 출장신청서
                String biztripDaysDay = wctrpreq.getBiztripDaysDay();    // 출장일수
                String emplNo = wctrpreq.getEmplNo();                    // 사번
                String biztripKind = wctrpreq.getBiztripKind();          // 출장종류
                Integer biztripDays = Integer.parseInt(biztripDaysDay);  // 출장일수
                String startTime = wctrpreq.getStartTime();              // 시작시각
                String endTime = wctrpreq.getEndTime();                  // 종료시각
//                String hour = getTimeDiff(startTime, endTime);           // 시간

                // 연간근무내역
                WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto(); // 연간근무내역 dto
                wcwrkdescreqDto.setEmplNo(emplNo);                       // 사번
                wcwrkdescreqDto.setWorkCodeKind(biztripKind);            // 근태코드
                wcwrkdescreqDto.setHalfdayCode("01");                    // 전일, 반일 구분 코드
                wcwrkdescreqDto.setStartTime(startTime);
                wcwrkdescreqDto.setEndTime(endTime);
//                wcwrkdescreqDto.setHour(hour);

                boolean days = biztripDays > 1;

                if (days) { // 출장일수가 2일 이상
                    for (int i = 0; i < biztripDays; i++) {
                        String date = dateFormatConvert(wctrpreq.getBiztripDateFrom(), i);
                        wcwrkdescreqDto.setDate(date); // 일자
                        wcwrkdescreqDto.setDays("1");  // 일수 ( 전일 1 )
                        result += applyWorkdesc(wcwrkdescreqDto);
                    }
                } else { // 출장일수가 1일 이하
                    wcwrkdescreqDto.setDate(wctrpreq.getBiztripDateFrom());
                    wcwrkdescreqDto.setDays(wctrpreq.getBiztripDaysDay());
                    result += applyWorkdesc(wcwrkdescreqDto);
                }
            }
        }

        return result;
    }

    /**
     * 휴가취소신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 휴가취소신청서 결재완료시 연간근무내역(Wcwrkdescreq)에 반영(Delete)
     */
    public int completeApprholicncl(AprvdocmstrDto dto) {
        int result = 0;
        List<WclvecnclreqDto> wclvecnclreqList = aprvcompleteMapper.findWclvecnclreqList(dto);

        if (CollectionUtils.isNotEmpty(wclvecnclreqList)) {
            for (WclvecnclreqDto wclvecnclreq : wclvecnclreqList) {
                // 휴가취소신청서
                String emplNo = wclvecnclreq.getEmplNo();              // 사번
                String date = wclvecnclreq.getDate();                  // 휴가일자
                String workCodeKind = wclvecnclreq.getWorkCodeKind();  // 휴가코드
                String seqNo = wclvecnclreq.getSeqNo();                // Seq No

                WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto(); // 연간근무내역 삭제할 데이터
                wcwrkdescreqDto.setEmplNo(emplNo);                     // 사번
                wcwrkdescreqDto.setDate(date);                         // 일자
                wcwrkdescreqDto.setWorkCodeKind(workCodeKind);         // 근태코드
                wcwrkdescreqDto.setSeqNo(seqNo);                       // SeqNo

                result += cancelWorkdesc(wcwrkdescreqDto);
            }
        }
        return result;
    }

    /**
     * 출장취소신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 출장취소신청서 결재완료시 연간근무내역(Wcwrkdescreq)에 반영(Delete)
     */
    public int completeApprtravcncl(AprvdocmstrDto dto) throws ParseException {
        int result = 0;
        List<WctrpcnclreqDto> wctrpcnclreqList = aprvcompleteMapper.findWctrpcnclreqList(dto);

        if (CollectionUtils.isNotEmpty(wctrpcnclreqList)) {
            for (WctrpcnclreqDto wctrpcnclreq : wctrpcnclreqList) {
                // 시작일자와 종료일자의 차이가 1 이상이고, 휴가구분이 전일이면 날짜별로 하나씩 delete
                // 시작일자와 종료일자의 차이가 1 미만이고, 휴가구분이 전일이 아니면 바로 delete

                // 휴가취소신청서
                String emplNo = wctrpcnclreq.getEmplNo();                                    // 사번
                String biztripDateFrom = wctrpcnclreq.getBiztripDateFrom();                  // 출장시작일자
                String biztripKind = wctrpcnclreq.getBiztripKind();                          // 출장종류
                String bizTripDaysDay = wctrpcnclreq.getBiztripDaysDay();                    // 출장일수(일)
                String trpDocNo = wctrpcnclreq.getTrpDocNo();                                // 문서번호
                String trpSeqNo = wctrpcnclreq.getTrpSeqNo();                                // Seq No
                Integer biztripDays = Integer.parseInt(bizTripDaysDay);

                // 연간근무내역
                WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto();                     // 연간근무내역 dto
                wcwrkdescreqDto.setEmplNo(emplNo);                                           // 사번
                wcwrkdescreqDto.setDate(biztripDateFrom);                                    // 일자
                wcwrkdescreqDto.setWorkCodeKind(biztripKind);                                // 근태코드
                //wcwrkdescreqDto.setSeqNo(trpSeqNo);                                        // Seq No ----> 만약 두 건을 한꺼번에 신청한게 있을 시 trpSeqNo 넣어주면 안될거같음
                wcwrkdescreqDto.setTrpDocNo(trpDocNo);                                       // 문서번호 (출장)
                wcwrkdescreqDto.setTrpSeqNo(trpSeqNo);                                       // Seq No (출장)

                boolean days = biztripDays > 1;

                if (days) { // 출장일수가 2일 이상
                    for (int i = 0; i < biztripDays; i++) {
                        String date = dateFormatConvert(wctrpcnclreq.getBiztripDateFrom(), i);
                        wcwrkdescreqDto.setDate(date);
                        // 연간근무내역 데이터 확인 및 삭제 시 출장취소는 시퀀스 값이 없어 따로 가져옴
                        String wcwrkSeq = aprvcompleteMapper.getWcwrkSeq(wcwrkdescreqDto);
                        wcwrkdescreqDto.setSeqNo(wcwrkSeq);                                          // Seq No
                        result += cancelWorkdesc(wcwrkdescreqDto);
                    }
                } else {
                    // 연간근무내역 데이터 확인 및 삭제 시 출장취소는 시퀀스 값이 없어 따로 가져옴
                    String wcwrkSeq = aprvcompleteMapper.getWcwrkSeq(wcwrkdescreqDto);
                    wcwrkdescreqDto.setSeqNo(wcwrkSeq);                                           // Seq No
                    result += cancelWorkdesc(wcwrkdescreqDto);
                }
                aprvcompleteMapper.updateWctrpreq(wcwrkdescreqDto); // 출장신청 취소여부 Y
            }
        }
        return result;
    }

    /**
     * 유연근무신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 유연근무신청서 결재완료시 근무계획일정(Wcplandesc)에 반영
     */
    public int completeApprflex(AprvdocmstrDto dto) {
        int result = 0;
        List<WcflexreqDto> wcflexreqList = aprvcompleteMapper.findApprflexreqList(dto); // 유연근무신청서 내용

        if (CollectionUtils.isNotEmpty(wcflexreqList)) {
            for (WcflexreqDto wcflexreqDto : wcflexreqList) {

                //중복데이터가 있으면 UPDATE 없으면 INSERT
                result = aprvcompleteMapper.insertFlexForWcplandesc(wcflexreqDto);
            }
        }
        return result;
    }

    /**
     * 유연근무신청서 결재완료 (기존)
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 유연근무신청서 결재완료시 근무계획일정(Wcplandesc)에 반영
     */
//    public int completeApprflex(AprvdocmstrDto dto) {
//        int result = 0;
//        List<WcplanweekreqDto> wcplanweekreqList = aprvcompleteMapper.findWcplanweekreqList(dto); // 유연근무신청서 내용
//
//        if (CollectionUtils.isNotEmpty(wcplanweekreqList)) {
//            for (WcplanweekreqDto wcplanweekreqDto : wcplanweekreqList) {
//                //1.시작일과 종료일을 체크하여 해당 일수만큼 loop가 돌면서 select하고 데이터 여부에 따라 insert, update 한다.
//                //2. 1처럼 하려했으나... 구지 일수 체크해서 loop돌필요없이 쿼리에서 insert select로 결정
//
//                wcplanweekreqDto = changeNextDay(wcplanweekreqDto); // 퇴근 시간이 2400면 다음날 0000으로 변경
//
//                String[] arrDate = DayUtils.getDiffDays(wcplanweekreqDto.getStartDate().replaceAll("-", ""), wcplanweekreqDto.getEndDate().replaceAll("-", ""));
//                //로직변경으로 인해 loop로 변경
//                for (String strDate : arrDate) {
//                    wcplanweekreqDto.setWorkDate(strDate);
//
////                    //연간 달력에 날짜 등록 되지 않은 경우 체크
////                    int calCheck = aprvcompleteMapper.chkCal(wcplanweekreqDto);
//
//                    //중복데이터가 있으면 UPDATE 없으면 INSERT
//                    int cnt = aprvcompleteMapper.chkWcplandesc(wcplanweekreqDto);
//
//                    //8일 이상 신청시 wccalend에서 공휴일 제외 INSERT
//                    if ((int) Double.parseDouble(wcplanweekreqDto.getDays()) < 8) {
//                        if (cnt > 0) {
//                            aprvcompleteMapper.deletePlandesc(wcplanweekreqDto);
//                            result += aprvcompleteMapper.insertWcplandesc(wcplanweekreqDto);
//                        } else
//                            result += aprvcompleteMapper.insertWcplandesc(wcplanweekreqDto);
//                    }
//                    else{
//                        //WORKDATE 공휴일일 경우 1, 아닐 경우 0
//                        if (aprvcompleteMapper.checkHoli(wcplanweekreqDto) == 0){
//                            if (cnt > 0) {
//                                aprvcompleteMapper.deletePlandesc(wcplanweekreqDto);
//                                result += aprvcompleteMapper.insertWcplandesc(wcplanweekreqDto);
//                            } else
//                                result += aprvcompleteMapper.insertWcplandesc(wcplanweekreqDto);
//                        }
//                    }
//                }
//            }
//        }
//        return result;
//    }

    public int completeApprStopEmp(AprvdocmstrDto dto) throws ParseException {
        int result = 0;
        List<WcstopreqDto> wcstopreqDtoList = aprvcompleteMapper.findWcstopreqList(dto);

        if (CollectionUtils.isNotEmpty(wcstopreqDtoList)) {
            for(WcstopreqDto stopDto : wcstopreqDtoList) {
                result = aprvcompleteMapper.insertStopForWcwrkdesc(stopDto);
            }
        }

        if (result <= 0) {
            throw new ServiceException("연간근무내역 반영에 실패하였습니다.");
        }
        return result;
    }

    /**
     * 단축근로신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 단축근로신청 결재완료 시 개인별근무일정(wcwrkdayplan)에 반영
     */
    public int completeApprstopemp200(AprvdocmstrDto dto) throws ParseException {
        int result = 0;

        WcreducedreqDto wcreducedreqdto = aprvcompleteMapper.findWcreducedreqList(dto);
        if(wcreducedreqdto != null){

            //aprvcompleteMapper.insertReducedForWcplandesc(wcreducedreqdto);

            List<WcreducedreqDto> list = new ArrayList<>();

            DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

            LocalDate startDate = LocalDate.parse(wcreducedreqdto.getStartDate().replaceAll("-",""), DATE_FMT);
            LocalDate endDate = LocalDate.parse(wcreducedreqdto.getEndDate().replaceAll("-",""), DATE_FMT);

            String emplNo = wcreducedreqdto.getEmplNo();
            String workCode = wcreducedreqdto.getWorkCode();
            String startTime = wcreducedreqdto.getStartTime();
            String endTime = wcreducedreqdto.getEndTime();
            String workHour = wcreducedreqdto.getWorkHour();
            String breakHour = wcreducedreqdto.getBreakHour();

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {

                WcreducedreqDto rowDto = new WcreducedreqDto();
                rowDto.setEmplNo(emplNo);
                rowDto.setWorkCode(workCode);
                rowDto.setStartDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                rowDto.setStartTime(startTime);
                rowDto.setEndTime(endTime);
                rowDto.setWorkHour(workHour);
                rowDto.setBreakHour(breakHour);
                list.add(rowDto);

            }

            if(StringUtils.isNotEmpty(wcreducedreqdto.getStartDate2()) && StringUtils.isNotEmpty(wcreducedreqdto.getEndDate2()))
            {
                LocalDate startDate2 = LocalDate.parse(wcreducedreqdto.getStartDate2().replaceAll("-",""), DATE_FMT);
                LocalDate endDate2 = LocalDate.parse(wcreducedreqdto.getEndDate2().replaceAll("-",""), DATE_FMT);

                for (LocalDate date = startDate2; !date.isAfter(endDate2); date = date.plusDays(1)) {

                    WcreducedreqDto rowDto = new WcreducedreqDto();
                    rowDto.setEmplNo(emplNo);
                    rowDto.setWorkCode(workCode);
                    rowDto.setStartDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                    rowDto.setStartTime(startTime);
                    rowDto.setEndTime(endTime);
                    rowDto.setWorkHour(workHour);
                    rowDto.setBreakHour(breakHour);
                    list.add(rowDto);
                }
            }

            aprvcompleteMapper.insertReducedForWcplandesc(list);
        }

        return result;
    }

    private WcplanweekreqDto changeNextDay ( WcplanweekreqDto dto ) {

        if (dto.getMonEntm().equals("2400")) {
            dto.setMonEntm("0000");
            dto.setMonNextInd("Y");
        }
        if (dto.getTueEntm().equals("2400")) {
            dto.setTueEntm("0000");
            dto.setTueNextInd("Y");
        }
        if (dto.getWedEntm().equals("2400")) {
            dto.setWedEntm("0000");
            dto.setWedNextInd("Y");
        }
        if (dto.getThuEntm().equals("2400")) {
            dto.setThuEntm("0000");
            dto.setThuNextInd("Y");
        }
        if (dto.getFriEntm().equals("2400")) {
            dto.setFriEntm("0000");
            dto.setFriNextInd("Y");
        }
        if (dto.getSatEntm().equals("2400")) {
            dto.setSatEntm("0000");
            dto.setSatNextInd("Y");
        }
        if (dto.getSunEntm().equals("2400")) {
            dto.setSunEntm("0000");
            dto.setSunNextInd("Y");
        }

        return dto;
    }

    /**
     * 초과근무신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 초과근무신청서 결재완료시 validation check
     */
    public int completeApprovtm(AprvdocmstrDto dto) {
        int result = 0;
        List<WcotmreqDto> wcotmreqList = aprvcompleteMapper.findWcotmreqList(dto); // 초과근무신청서 내용
        if (CollectionUtils.isNotEmpty(wcotmreqList)) {
            for (WcotmreqDto wcotmreqDto : wcotmreqList) {
                int duplCnt = aprvcompleteMapper.checkDupl(wcotmreqDto);
                if (duplCnt > 0) {
                    throw new ServiceException(ALREADY_DATA);
                }
                // 초과근무신청 승인 완료시 해당 내역 WCOTMDESC 테이블에 저장
                result = aprvcompleteMapper.insertWcotmdesc(wcotmreqDto);
                if (result <= 0) {
                    throw new ServiceException("초과근무 승인에 실패했습니다.");
                }
            }
        }
        return result;
    }

    /**
     * 초과근무취소신청서 결재완료
     *
     * @param dto
     * @return int
     */
    public int completeApprcnclovtm(AprvdocmstrDto dto) {
        int result = 0;
        List<WcotmcnclreqDto> wcotmcnclreqList = aprvcompleteMapper.findWcotmcnclreqList(dto); // 초과근무취소신청
        if (CollectionUtils.isNotEmpty(wcotmcnclreqList)) {
            for (WcotmcnclreqDto wcotmcnclreqDto : wcotmcnclreqList) {
                WcotmdescDto wcotmdescDto = new WcotmdescDto();
                wcotmdescDto.setCoCode(wcotmcnclreqDto.getCoCode());
                wcotmdescDto.setDocNo(wcotmcnclreqDto.getRefDocNo());
                wcotmdescDto.setEmplNo(wcotmcnclreqDto.getEmplNo());
                wcotmdescDto.setWorkDate(wcotmcnclreqDto.getWorkDate());
                wcotmdescDto.setStartTime(wcotmcnclreqDto.getStartTime());

                result += aprvcompleteMapper.deleteWcotmdesc(wcotmdescDto);
            }
        }
        if (result <= 0) {
            throw new ServiceException("연장근무취소 승인에 실패했습니다.");
        }
        return result;
    }

    /**
     * 출퇴근변경신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 출퇴근변경신청서 결재완료시 출퇴근기록내역(Wctimedesc)에 반영(Insert or Update)
     */
    public int completeApprtmchng(AprvdocmstrDto dto) {
        int result = 0;
        List<WcworktmchngreqDto> wcworktmchngreqList = aprvcompleteMapper.findWcworktmchngreqList(dto);

        if (CollectionUtils.isNotEmpty(wcworktmchngreqList)) {
            for (WcworktmchngreqDto wcworktmchngreqDto : wcworktmchngreqList) {
                // 출퇴근변경신청서
                String emplNo = wcworktmchngreqDto.getEmplNo();            // 사번
                String workDate = wcworktmchngreqDto.getWorkDate();        // 근무일자
                String startTime = wcworktmchngreqDto.getChngStartTime();  // 시작시간
                String endTime = wcworktmchngreqDto.getChngEndTime();      // 종료시간
                String workHour = wcworktmchngreqDto.getChngWorkHour();    // 근무시간
                String nextDayInd = wcworktmchngreqDto.getNextDayInd();  // 익일여부

                WctimedescDto wctimedescDto = new WctimedescDto(); // 출퇴근내역
                wctimedescDto.setEmplNo(emplNo);
                wctimedescDto.setWorkDate(workDate);
                wctimedescDto.setStartTime(startTime);
                wctimedescDto.setEndTime(endTime);
                wctimedescDto.setWorkHour(workHour);
                wctimedescDto.setNextDayInd(nextDayInd);
                wctimedescDto.setChngInd("Y");

                // 익일여부 'Y'일 경우 endDate 다음날로 setting
                if("Y".equals(wctimedescDto.getNextDayInd())) {
                    String startDate = wctimedescDto.getWorkDate().replace("-", "");
                    String endDate = null;

                    try {
                        endDate = AddDate(startDate, 0, 0, 1);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    wctimedescDto.setEndDate(endDate);
                } else {
                    wctimedescDto.setEndDate("");
                }

                //출퇴근 기록 삭제 FLAG = Y 일때
                if ("Y".equals(wcworktmchngreqDto.getDeleteYn())){
                    aprvcompleteMapper.deleteWcworkchn(wctimedescDto);
                }
                else {
                    // 중복데이터가 있으면 UPDATE 없으면 INSERT
                    int cnt = aprvcompleteMapper.chkWctimedesc(wctimedescDto);
                    if (cnt > 0)
                        result += aprvcompleteMapper.updateWctimedesc(wctimedescDto);
                    else
                        result += aprvcompleteMapper.insertWctimedesc(wctimedescDto);
                }

            }
        }
        return result;
    }

    /**
     * 근태수당신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 근태수당신청서 결재완료시 초과근무신청서 테이블 지급구분 칼럼 UPDATE
     */
    public int completeApprovtmPay(AprvdocmstrDto dto) {
        int result = 0;
        List<WcovtmpayreqDto> wcovtmpayreqList = aprvcompleteMapper.findWcovtmpayreqList(dto);

        if (CollectionUtils.isNotEmpty(wcovtmpayreqList)) {
            for (WcovtmpayreqDto wcovtmpayreqDto : wcovtmpayreqList) {
                if (aprvcompleteMapper.checkDuplByOvtmpay(wcovtmpayreqDto) > 0) {
                    StringBuffer message = new StringBuffer();
                    message.append("결재 완료된 초과근무 신청서가 있습니다. 문서번호 :");
                    message.append(wcovtmpayreqDto.getRefDocNo());
//                    message.append(" / ");
//                    message.append(wcovtmpayreqDto.getRefSeqNo());
                    throw new ServiceException(message.toString());
                }
                result += aprvcompleteMapper.updateWcotmreq(wcovtmpayreqDto);
            }
        }

        return result;
    }

    /**
     * 경조금신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 경조금신청서 결재완료시 경조금지급신청(Cnreq)에 반영(Insert or Update)
     */
    public int completeApprcn(AprvdocmstrDto dto) {
        int result = 0;
        List<CndocreqDto> cndocreqList = aprvcompleteMapper.findCndocreqList(dto);

        if (CollectionUtils.isNotEmpty(cndocreqList)) {
            for (CndocreqDto cndocreqDto : cndocreqList) {
                // 경조금지급신청
                String leaveCnt = cndocreqDto.getLveDays();
                String wreathInd = cndocreqDto.getFlwrInd();
                String artmouInd = cndocreqDto.getItemInd();

                cndocreqDto.setLeaveCnt(leaveCnt);
                cndocreqDto.setWreathInd(wreathInd);
                cndocreqDto.setArtmouInd(artmouInd);
                cndocreqDto.setStatusCode("AP"); // statusCode - 승인

                result += aprvcompleteMapper.insertCnreq(cndocreqDto);
            }
        }
        return result;
    }

    /**
     * 경조 신청서 결재완료
     *
     * @param aprvdocmstrDto
     * @return int
     * @Description 경조 신청서 결재완료시 cnsmstr 반영
     */
    public int completeApprcl(AprvdocmstrDto aprvdocmstrDto) throws ParseException {
        int result = 0;
        result += aprvcompleteMapper.insertCnmstr(aprvdocmstrDto);
        return result;
    }

    /**
     * 휴일수당지급신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 휴일수당지급신청서 결재완료시 연간근무내역(Wcwrkdesc)에 해당하는 대체휴가일 삭제(Delete)
     */
    public int completeApprholipay(AprvdocmstrDto dto) {
        int result = 0;
        List<WcholpayreqDto> wcholpayreqList = aprvcompleteMapper.findWcholpayreqList(dto);

        if (CollectionUtils.isNotEmpty(wcholpayreqList)) {
            for (WcholpayreqDto wcholpayreqDto : wcholpayreqList) {

                String emplNo = wcholpayreqDto.getEmplNo();
                String date = wcholpayreqDto.getLveDate();
                String workCodeKind = wcholpayreqDto.getLveCode();

                WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto(); // 연간근무내역
                wcwrkdescreqDto.setEmplNo(emplNo);
                wcwrkdescreqDto.setDate(date);
                wcwrkdescreqDto.setWorkCodeKind(workCodeKind);

                result += aprvcompleteMapper.deleteWcwrkdescForHolipay(wcwrkdescreqDto);
            }
        }
        return result;
    }

    /**
     * 대체휴가취소신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     * @Description 대체휴가취소신청서 결재완료시 대체휴가신청서(wclverplcreq) 취소 칼럼 UPDATE,
     * 연간근무내역(Wcwrkdesc)에 해당하는 대체휴가일 삭제(Delete)
     */
    public int completeApprcnclsubholi(AprvdocmstrDto dto) {
        int updateCnt = 0;
        int deleteCnt = 0;

        List<WclvecnclsubholiDto> wclvecnclsubholiDtos = aprvcompleteMapper.findWccnclsubholieqList(dto);

        if (CollectionUtils.isNotEmpty(wclvecnclsubholiDtos)) {
            for (WclvecnclsubholiDto wclvecnclsubholiDto : wclvecnclsubholiDtos) {
                if (aprvcompleteMapper.checkDuplByCnclsubholi(wclvecnclsubholiDto) > 0) {
                    StringBuffer message = new StringBuffer();
                    message.append("결재 완료된 대체휴가 신청서가 있습니다. 문서번호 :");
                    message.append(wclvecnclsubholiDto.getRefDocNo());
                    throw new ServiceException(message.toString());
                }

//              대체휴가신청서 수정
                updateCnt += aprvcompleteMapper.updateWclverplcreq(wclvecnclsubholiDto);

                String emplNo = wclvecnclsubholiDto.getEmplNo();
                String date = wclvecnclsubholiDto.getLveDate();
                String workCodeKind = wclvecnclsubholiDto.getLveCode();
                String halfdayCode = wclvecnclsubholiDto.getLveGubun();

                WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto(); // 연간근무내역
                wcwrkdescreqDto.setEmplNo(emplNo);
                wcwrkdescreqDto.setDate(date);
                wcwrkdescreqDto.setWorkCodeKind(workCodeKind);
                wcwrkdescreqDto.setHalfdayCode(halfdayCode);

//              연간근무내역 삭제
                deleteCnt += aprvcompleteMapper.deleteWcwrkdescForCnclsubholi(wcwrkdescreqDto);

                if (updateCnt != deleteCnt) throw new ServiceException("관리자 확인이 필요합니다.");// 대체휴가신청서, 연간근무내역 데이터 확인 필요
            }
        }
        return deleteCnt;
    }


    /**
     * 휴가신청서 validation check
     *
     * @param WcwrkdescreqDto
     * @return boolean
     * @Description 휴가신청서 1일 미만 반영시 validation check
     */
    public boolean invalidApprsubholi(WcwrkdescreqDto wcwrkdescreqDto) {
        boolean haveData = aprvcompleteMapper.chkWcwrkdesc(wcwrkdescreqDto) > 0;

        if (haveData) {

            boolean isHour = wcwrkdescreqDto.getHalfdayCode().equals("TM");    // 근태코드 : 휴가(시간)

            Float days = Float.parseFloat(wcwrkdescreqDto.getDays());          // 일수
            Float hour = Float.parseFloat(wcwrkdescreqDto.getHour());          // 시간 
            Float sumDt = days;                                                // 1일 초과 check.
            Float sumTt = hour;                                                // 8시간 초과 check.

            List<WcwrkdescreqDto> workdescList = aprvcompleteMapper.chkWcwrkdescList(wcwrkdescreqDto); // 같은 날짜에 존재하는 연간근무내역

            for (WcwrkdescreqDto work : workdescList) {
                if (StringUtils.isEmpty(wcwrkdescreqDto.getDays()) || StringUtils.isEmpty(work.getDays()))
                    throw new ServiceException("휴가일수의 값이 부정확합니다.");

                sumDt += Float.parseFloat(work.getDays());
                sumTt += Float.parseFloat(work.getHour());

                /*if (sumDt > 1 || work.getDays().equals("1"))
                    throw new ServiceException("해당날짜에 신청할 수 있는 연간근무내역이 초과되었습니다. 근태내역을 확인해주세요.");*/

                /* 하루 8시간 초과 체크로 수정 */
                if (sumTt > 8)
                    throw new ServiceException("하루에 신청할 수 있는 휴가는 8시간입니다.");

                if (isHour) {
                    // 기존 연간근무내역에 저장되어있는 시간과 겹치지 않게
                    Long chkSTime = work.getStartTime().equals("") ? Long.parseLong("0000") : Long.parseLong(work.getStartTime());
                    Long chkETime = work.getEndTime().equals("") ? Long.parseLong("0000") : Long.parseLong(work.getEndTime());
                    Long targetSTime = Long.parseLong(wcwrkdescreqDto.getStartTime());
                    Long targetETime = Long.parseLong(wcwrkdescreqDto.getEndTime());

                    if (chkSTime < targetETime && chkETime > targetSTime)
                        throw new ServiceException(ALREADY_DATA);
                }
            }
        }
        return true;
    }

    /**
     * 일근태집계신청서 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     */
    public int completeWrk(AprvdocmstrDto dto) {
        int result = 0;
        List<WrkreqDto> reqList = aprvcompleteMapper.findWrkreqList(dto);

        if (CollectionUtils.isNotEmpty(reqList)) {
            for (WrkreqDto wrkreqDto : reqList) {
                result += aprvcompleteMapper.insertWrk(wrkreqDto);
            }
        }
        return result;
    }


    /**
     * 출/퇴근기록 변경 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     */
    public int completeApprcn600(AprvdocmstrDto dto) {
        int result = 0;
        List<WctimedescreqDto> reqList = aprvcompleteMapper.findWctimedescreqDtoList(dto);

        if (CollectionUtils.isNotEmpty(reqList)) {
            for (WctimedescreqDto wctimedescreqDto : reqList) {
                result += aprvcompleteMapper.insertApprcn600(wctimedescreqDto);
            }
        }
        return result;
    }

    /**
     * 월근무계획신청(업로드) 결재완료
     *
     * @param AprvdocmstrDto
     * @return int
     */
    public int completeMonthlyWorkPlan(AprvdocmstrDto dto) {
        int result = 0;
        List<WcplanschreqDto> reqList = aprvcompleteMapper.findWcplanschreqList(dto);

        if (CollectionUtils.isNotEmpty(reqList)) {
            for (WcplanschreqDto wcplanschreqDto : reqList) {
                aprvcompleteMapper.deleteWcplandesc(wcplanschreqDto); // 회사코드, 사번, 계획근무일이 동일한 기존 내역은 삭제
                result += aprvcompleteMapper.insertWcreqToPlan(wcplanschreqDto);
            }
        }

        return result;
    }

    /**
     * 연간근무내역 신청서 반영
     *
     * @param WcwrkdescreqDto
     * @return int
     * @Description 연간근무내역(Wcwrkdescreq)에 근태반영
     */
    public int applyWorkdesc(WcwrkdescreqDto wcwrkdescreqDto) {
        int result = 0;

        boolean haveData = aprvcompleteMapper.chkWcwrkdesc(wcwrkdescreqDto) > 0;
        // 1일 이상 신청하는 휴가, 출장은 해당 날짜에 데이터가 없어야 함.
        if (haveData) {
            throw new ServiceException(ALREADY_DATA);
        } else {

            //해당일자 근무유형별 전일 시간 SET
            //CamelCaseMap fullDayTime = aprvcompleteMapper.getWcPlanDesc(wcwrkdescreqDto);
            //CamelCaseMap fullDayTime = aprvcompleteMapper.getFullDayTimeInWrkmng(wcwrkdescreqDto);
            //wcwrkdescreqDto.setStartTime(fullDayTime.getString("workStartTime"));
            //wcwrkdescreqDto.setEndTime(fullDayTime.getString("workEndTime"));
            //wcwrkdescreqDto.setHour(fullDayTime.getString("setHour"));

            result += aprvcompleteMapper.insertWcwrkdesc(wcwrkdescreqDto);
        }
        return result;
    }

    /**
     * 연간근무내역 취소신청서 반영
     *
     * @param WcwrkdescreqDto
     * @return int
     * @Description 연간근무내역(Wcwrkdescreq)에 근태반영(delete)
     */
    public int cancelWorkdesc(WcwrkdescreqDto wcwrkdescreqDto) {
        int result = 0;

        boolean haveData = aprvcompleteMapper.chkWcwrkdesc(wcwrkdescreqDto) > 0;

        if (haveData) {
            result += aprvcompleteMapper.deleteWcwrkdesc(wcwrkdescreqDto);
        } else {
            throw new ServiceException(NO_DATA_CANCEL);
        }
        return result;
    }

    /**
     * 교육신청서 결재완료
     *
     * @param aprvdocmstrDto
     * @return int
     * @Description 교육신청서 결재완료시 연간근무내역(Wcwrkdescreq)에 반영
     */
    public int completeEdureq(AprvdocmstrDto aprvdocmstrDto) throws ParseException {
        int result = 0;
        List<EdureqdocDto> edureqdocDtoList = aprvcompleteMapper.findEdureqdocDtoList(aprvdocmstrDto);
        String headerText = "교육신청 알림 메일입니다.";
        String contentsText = aprvdocmstrDto.getDocTitlNm() + "님 교육신청 내역입니다.";
        String contents = "";

        if (CollectionUtils.isNotEmpty(edureqdocDtoList)) {
            for (EdureqdocDto edureqdocDto : edureqdocDtoList) {
                // 교육신청서
                String eduDaysDay = edureqdocDto.getEduDays();       // 교육일수
                String emplNo = edureqdocDto.getEmplNo();                // 사번
                String emplNameHan = edureqdocDto.getEmplNameHan();                // 사번
                Integer eduDays = Integer.parseInt(eduDaysDay);  // 교육일수
                String eduDateFrom = edureqdocDto.getEduDateFrom();
                String eduDateTo = edureqdocDto.getEduDateTo();
                String courseName = edureqdocDto.getCourseName();
                String eduMethodCode = edureqdocDto.getEduMethodCode();
                String startTime = edureqdocDto.getStartTime();              // 시작시각
                String endTime = edureqdocDto.getEndTime();                  // 종료시각

                SimpleDateFormat sdfb = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat sdfa = new SimpleDateFormat("yyyy년MM월dd일");
                Date lveDateFromDt = sdfb.parse(eduDateFrom);
                Date lveDateToDt = sdfb.parse(eduDateTo);
                contents += sdfa.format(lveDateFromDt) + " ~ " + sdfa.format(lveDateToDt) + " : " + courseName + " (" + emplNameHan + ")<br>";

                    // 연간근무내역
                    WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto(); // 연간근무내역 dto
                    wcwrkdescreqDto.setEmplNo(emplNo);                       // 사번
                    wcwrkdescreqDto.setWorkCodeKind(eduMethodCode);          // 근태코드
                    wcwrkdescreqDto.setHalfdayCode("05");             // 전일, 반일 구분 코드
                    wcwrkdescreqDto.setStartTime(startTime);
                    wcwrkdescreqDto.setEndTime(endTime);

                    boolean days = eduDays > 1;

                    if (days) { // 출장일수가 2일 이상
                        for (int i = 0; i < eduDays; i++) {
                            String date = dateFormatConvert(edureqdocDto.getEduDateFrom(), i);
                            wcwrkdescreqDto.setDate(date); // 일자
                            wcwrkdescreqDto.setDays("1");  // 일수 ( 전일 1 )
                            result += applyWorkdesc(wcwrkdescreqDto);
                        }

                    } else { // 출장일수가 1일 이하
                        wcwrkdescreqDto.setDate(edureqdocDto.getEduDateFrom());
                        wcwrkdescreqDto.setDays(edureqdocDto.getEduDays());
                        result += applyWorkdesc(wcwrkdescreqDto);
                    }

            }
        }
        return result;
    }

    /**
     * 교육취소신청 결재완료
     * @param aprvdocmstrDto
     * @return int
     * @throws ParseException
     * @Description EDUREQ 테이블의 CANCEL_IND 값을 'Y'로 업데이트, 근태반영여부가 'Y'인 교육의 경우 연간근무내역 삭제
     */
    public int completeEduclreq(AprvdocmstrDto aprvdocmstrDto) throws ParseException {
        int result = 0;
        List<EduclreqDto> educlreqList = aprvcompleteMapper.findEduclreqList(aprvdocmstrDto);
        String headerText = "교육취소신청 알림 메일입니다.";
        String contentsText = aprvdocmstrDto.getDocTitlNm() + "님 교육취소신청 내역입니다.";
        String contents = "";

        if (CollectionUtils.isNotEmpty(educlreqList)) {
            for (EduclreqDto educlreqDto : educlreqList) {
                /* 근태반영여부가 'Y'인 경우만 근무내역 삭제
                 - 시작일자와 종료일자의 차이가 1 이상이고, 휴가종류(WC12)가 전일이면 날짜별로 하나씩 delete
                 - 시작일자와 종료일자가 같고, 휴가종류가 전일이 아니면 바로 delete
                */
                // 교육취소신청 정보
                String emplNo = educlreqDto.getEmplNo();
                String eduDateFrom = educlreqDto.getEduDateFrom();
                String eduDateTo = educlreqDto.getEduDateTo();
                int eduDays = Integer.parseInt(educlreqDto.getEduDays());
                String halfdayCode = educlreqDto.getHalfdayCode();
                String courseName = educlreqDto.getCourseName();
                String eduMethodCode = educlreqDto.getEduMethodCode();
                String startTime = educlreqDto.getStartTime();

                SimpleDateFormat sdfb = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat sdfa = new SimpleDateFormat("yyyy년MM월dd일");
                Date lveDateFromDt = sdfb.parse(eduDateFrom);
                Date lveDateToDt = sdfb.parse(eduDateTo);
                contents += sdfa.format(lveDateFromDt) + " ~ " + sdfa.format(lveDateToDt) + " : " + courseName + "<br>";

                //if (("Y").equals(educlreqDto.getWcwrkInd())) {

                    // 연간근무내역
                    WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto();
                    wcwrkdescreqDto.setEmplNo(emplNo);
                    wcwrkdescreqDto.setHalfdayCode(halfdayCode);
                    wcwrkdescreqDto.setWorkCodeKind(eduMethodCode);  // WCWORKCD의 교육 근무코드
                    wcwrkdescreqDto.setStartTime(startTime);

                    for (int i = 0; i < eduDays; i++) {
                        String date = dateFormatConvert(eduDateFrom, i);
                        wcwrkdescreqDto.setDate(date);
                        // 출장 신청과 마찬가지로 시퀀스 값 따로 가져옴
                        wcwrkdescreqDto.setSeqNo(aprvcompleteMapper.getWcwrkSeq(wcwrkdescreqDto));
                        result += aprvcompleteMapper.deleteWcwrkdescForCnclsubholi(wcwrkdescreqDto);
                    }
                //}
                result += aprvcompleteMapper.updateEdureq(educlreqDto);
            }
        }

        return result;
    }

    /**
     * 교육결과보고서 결재완료
     * @param aprvdocmstrDto
     * @return int
     * @throws ParseException
     * @Description EDUREQ 테이블의 WRITE_YN 값을 'Y'로 업데이트
     */
    public int completeEdurslt(AprvdocmstrDto aprvdocmstrDto) throws ParseException {
        int result = 0;
        List<EdursltDto> edursltList = aprvcompleteMapper.findEdursltList(aprvdocmstrDto);

        if (CollectionUtils.isNotEmpty(edursltList)) {
            for (EdursltDto edursltDto : edursltList) {
                result += aprvcompleteMapper.updateEdurslt(edursltDto);
            }
        }
        return result;
    }

    /**
     * 지각/조퇴/외출/결근신청 결재완료
     *
     * @param dto
     * @return int
     * @Description 지각/조퇴/외출/결근신청 결재완료시 validation check 및 연간근무내역에 반영
     */
    public int completeAppranl(AprvdocmstrDto dto) {
        int result = 0;
        List<WcanlreqDto> wcanlreqList = aprvcompleteMapper.findWcanlreqList(dto); // 지각/조퇴/외출/결근신청 내용
        if (CollectionUtils.isNotEmpty(wcanlreqList)) {
            for (WcanlreqDto wcanlreqDto : wcanlreqList) {
                int duplCnt = aprvcompleteMapper.checkDuplByAnlreq(wcanlreqDto);
                if (duplCnt > 0) {
                    throw new ServiceException(ALREADY_DATA);
                }

                // 지각/조퇴/외출/결근신청 승인 완료시 해당 내역 WCOTMDESC 테이블에 저장
                result += aprvcompleteMapper.insertWcanldesc(wcanlreqDto);

                // 연간근무내역
                WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto(); // 연간근무내역 dto
                wcwrkdescreqDto.setEmplNo(wcanlreqDto.getEmplNo());                       // 사번
                wcwrkdescreqDto.setWorkCodeKind(wcanlreqDto.getAnlCode());          // 근태코드
                wcwrkdescreqDto.setHalfdayCode("05");             // WC12 휴가종류 05-시간
                wcwrkdescreqDto.setDate(wcanlreqDto.getAnlDate());
                wcwrkdescreqDto.setStartTime(wcanlreqDto.getAnlStartTime());
                wcwrkdescreqDto.setEndTime(wcanlreqDto.getAnlEndTime());
                wcwrkdescreqDto.setHour(wcanlreqDto.getAnlTime());              // 휴가사용시간
                wcwrkdescreqDto.setDays("1");               // 일수
                result += applyWorkdesc(wcwrkdescreqDto);

                if (result <= 0) {
                    throw new ServiceException("지각/조퇴/외출/결근 승인에 실패했습니다.");
                }

            }
        }
        return result;
    }

    /**
     * 지각/조퇴/외출/결근취소 신청 결재완료
     *
     * @param dto
     * @return int
     * @Description 지각/조퇴/외출/결근취소 신청 결재완료시 삭제 후 연간근무내역에 반영
     */
    public int completeApprcnclanl(AprvdocmstrDto dto) {
        int result = 0;
        List<WcanlcnclreqDto> wcanlcnclreqList = aprvcompleteMapper.findWcanlcnclreqList(dto); // 지각/조퇴/외출/결근취소 신청
        if (CollectionUtils.isNotEmpty(wcanlcnclreqList)) {
            for (WcanlcnclreqDto wcanlcnclreqDto : wcanlcnclreqList) {
                WcotmdescDto wcotmdescDto = new WcotmdescDto();
                wcotmdescDto.setCoCode(wcanlcnclreqDto.getCoCode());
                wcotmdescDto.setDocNo(wcanlcnclreqDto.getRefDocNo());
                wcotmdescDto.setEmplNo(wcanlcnclreqDto.getEmplNo());
                wcotmdescDto.setWorkDate(wcanlcnclreqDto.getAnlDate());
                wcotmdescDto.setStartTime(wcanlcnclreqDto.getAnlStartTime());
                //WCOTMDESC 테이블에 삭제
                result += aprvcompleteMapper.deleteWcotmdesc(wcotmdescDto);

                // 연간근무내역 삭제
                WcwrkdescreqDto wcwrkdescreqDto = new WcwrkdescreqDto(); // 연간근무내역 삭제할 데이터
                wcwrkdescreqDto.setEmplNo(wcanlcnclreqDto.getEmplNo());                     // 사번
                wcwrkdescreqDto.setDate(wcanlcnclreqDto.getAnlDate());                         // 일자
                wcwrkdescreqDto.setWorkCodeKind(wcanlcnclreqDto.getAnlCode());         // 근태코드
//                wcwrkdescreqDto.setSeqNo(String.valueOf(wcanlcnclreqDto.getSeqNo()));                       // SeqNo

                result += cancelWorkdesc(wcwrkdescreqDto);

            }
        }
        if (result <= 0) {
            throw new ServiceException("지각/조퇴/외출/결근취소 승인에 실패했습니다.");
        }
        return result;
    }

    /**
     * date convert
     *
     * @param String str, int amount
     * @return String
     */
    public String dateFormatConvert(String str, int amount) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = dateFormat.parse(str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return dateFormat.format(cal.getTime());
    }

    private static String AddDate(String strDate, int year, int month, int day) throws Exception {
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();

        Date dt = dtFormat.parse(strDate);

        cal.setTime(dt);

        cal.add(Calendar.YEAR,  year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DATE,  day);

        return dtFormat.format(cal.getTime());
    }

    private String getTimeDiff(String startTime, String endTime) throws ParseException {
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd HHmm");

        Calendar cal = Calendar.getInstance();
        String today = dtFormat.format(cal.getTime());

        // 시작시간이 종료시간보다 더 큰 경우
        if (startTime.compareTo(endTime) > 0) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        String nextDay = dtFormat.format(cal.getTime());

        String start = today + " " + startTime;
        String end = nextDay + " " + endTime;

        Date dateStart = timeFormat.parse(start);
        Date dateEnd = timeFormat.parse(end);

        long timeDiff = dateEnd.getTime() - dateStart.getTime();

        double hour = Math.ceil(timeDiff / (double)(1000 * 60 * 60) * 100) / 100.0;

        return Double.toString(hour);
    }
}

