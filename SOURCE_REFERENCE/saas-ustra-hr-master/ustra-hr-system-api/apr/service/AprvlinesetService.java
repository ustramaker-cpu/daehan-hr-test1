package hr.system.api.apr.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.common.AprvType;
import hr.system.api.apr.dto.AprvPopDto;
import hr.system.api.apr.dto.AprvdetailDto;
import hr.system.api.apr.dto.AprvdocmstrDto;
import hr.system.api.apr.dto.AprvlinesetDto;
import hr.system.api.apr.mapper.AprvlinesetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AprvlinesetService {

    private final AprvlinesetMapper aprvlinesetMapper;

    private final AprvcompleteService aprvcompleteService;

    private final AprvMailService aprvMailService;

    /**
     * @param aprvlinesetDto
     * @return List<CamelCaseMap>
     * @Description Default 결재선 라인 조회
     * 기안자 -> 확인자(결재전) -> 결재자 -> 확인자(결재후)
     */
    public List<CamelCaseMap> findDefaultAprvline(AprvlinesetDto aprvlinesetDto) {
        List<CamelCaseMap> list = null;

        CamelCaseMap findAprvform = aprvlinesetMapper.findAprvform(aprvlinesetDto);
        String depthYn = findAprvform.getString("depthYn");

        if("N".equals(depthYn)){
            // 확인자관리에 설정된 결재라인만 조회
            list = aprvlinesetMapper.findAprvconfirmAprvline(aprvlinesetDto);
        }else{
            // 기안자의 조직장을 포함한 확인자관리에 설정된 결재라인 조회
            list = aprvlinesetMapper.findDefaultAprvline(aprvlinesetDto);
        }

        return list;
    }

    /**
     * @param aprvlinesetDto
     * @return List<CamelCaseMap>
     * @Description 상신된 결재선 라인 조회
     */
    public List<CamelCaseMap> findAprvline(AprvlinesetDto aprvlinesetDto) {
        return aprvlinesetMapper.findAprvline(aprvlinesetDto);
    }

    /**
     * @param aprvlinesetDto
     * @return String
     * @Description 문서번호 조회
     */
    public String findDocNo(AprvlinesetDto aprvlinesetDto) {
        return aprvlinesetMapper.findDocNo(aprvlinesetDto);
    }

    /**
     * @param aprvdetailDto
     * @return boolean
     * @Description 본인 결재 순서 체크
     */
    public boolean checkMyApprovalTurn(AprvdetailDto aprvdetailDto) {
        return aprvlinesetMapper.checkMyApprovalTurn(aprvdetailDto) > 0;
    }

    /**
     * @param aprvdetailDto
     * @return boolean
     * @Description 회수 가능 여부 체크
     */
    public boolean checkCancelPossible(AprvdetailDto aprvdetailDto) {
        return aprvlinesetMapper.checkCancelPossible(aprvdetailDto) == 1;
    }

    /**
     * @param aprvdocmstrDto
     * @return boolean
     * @Description 문서 존재 여부 체크
     */
    public boolean isDocumentExist(AprvdocmstrDto aprvdocmstrDto) {
        return aprvlinesetMapper.checkAprvdocmstr(aprvdocmstrDto) > 0;
    }

    /**
     * 결재 요청 담당 Method
     *
     * @Description 각 상태별 테이블 상태는 아래와 같다.
     * aprvdocmstr(문서마스터), aprvdetail(결재상세)
     * 임시저장(1)  : aprvdocmstr 테이블 최초 insert , aprvdetail 테이블 최초 insert
     * 결재요청(3)  : aprvdocmstr 테이블 상태코드 결재요청(3)으로 update, aprvdetail 테이블 delete 후 재 insert
     * 기각(4)     : aprvdocmstr 테이블 상태코드 기각(4)으로 update, aprvdetail 테이블 결재자 기각으로(4) update
     * 결재완료(5)  : aprvdocmstr 테이블 모든결재가 완료됐을때만 결재완료(5)로 update, aprvdetail 테이블 결재자 결재완료(5)로 update
     * 회수(6)     : aprvdocmstr 테이블 회수(6)로 update
     * 재기안(7)   : 기존 문서번호로 aprvdocmstr 테이블 재기안(7)로 update, 그 외는 결재요청과 동일
     */
    public boolean saveApproval(AprvdocmstrDto aprvdocmstrDto) {

        String statusCode = aprvdocmstrDto.getStatusCode();

        switch (statusCode) {
            case AprvType.TEMP_SAVE:      // 임시저장
                tempSave(aprvdocmstrDto);
                break;
            case AprvType.APPROVAL_REQ:   // 결재요청
                approvalRequest(aprvdocmstrDto);
                break;
            case AprvType.DISMISSAL:      // 기각
                approvalDismissal(aprvdocmstrDto);
                break;
            case AprvType.APPROVAL:       // 승인
                approvalAgree(aprvdocmstrDto);
                break;
            case AprvType.APPROVAL_CNCL:  // 회수
                approvalCancel(aprvdocmstrDto);
                break;
            default:
                log.info(":::존재하지 않는 결재코드 : " + statusCode);
                throw new ServiceException("존재하지 않는 결재 코드입니다.");
        }
        return true;
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 결재 임시저장
     */
    public void tempSave(AprvdocmstrDto aprvdocmstrDto) {
        boolean reDraft = aprvlinesetMapper.checkAprvdocmstrBfDocNo(aprvdocmstrDto) > 0;

        /* 재기안일경우, 기존 문서 재기안(7)상태로 업데이트 */
        if (reDraft) {
            aprvdocmstrDto.setStatusCode(AprvType.RE_DRAFT);
            aprvlinesetMapper.updateAprvdocmstrBfDocNo(aprvdocmstrDto);
        }

        /* 20220810 HY 문서 수정시 결재선 변경 적용 */
        boolean docExist = aprvlinesetMapper.checkAprvdocmstr(aprvdocmstrDto) > 0;

        if (!docExist) {
            aprvlinesetMapper.insertAprvdocmstr(aprvdocmstrDto);
        }

        /* 임시저장과 결재요청사이 결재선이 달라졌을 경우가 있어 aprvdetail delete 후 insert */
        aprvlinesetMapper.deleteAprvdetail(aprvdocmstrDto);
        aprvlinesetMapper.deleteAprvviewer(aprvdocmstrDto);

        /* 결재라인이 수정될 경우 마스터 path order도 수정 */
        aprvlinesetMapper.updateAprvdocmstrAprvPathOrder(aprvdocmstrDto);

        /* 결재선 저장 */
        aprvdocmstrDto.getAprvdetailDtoList().forEach(row ->
                {
                    /* 대리결재자 체크 */
                    setTransfer(row);
                    /* 결재자 저장 */
                    saveAprvline(row);
                }
        );
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 결재 요청
     */
    public void approvalRequest(AprvdocmstrDto aprvdocmstrDto) {
        /* 현재 문서 결재요청 지정 */
        aprvdocmstrDto.setStatusCode(AprvType.APPROVAL_REQ);

        boolean docExist = aprvlinesetMapper.checkAprvdocmstr(aprvdocmstrDto) > 0;

        if (docExist) {
            /* 임시저장 하여 이미 docmstr이 존재할 경우 */
            aprvlinesetMapper.updateAprvdocmstrStatus(aprvdocmstrDto);

        } else {
            /* 임시저장 없이 바로 결재 요청한 경우 */
            aprvlinesetMapper.insertAprvdocmstr(aprvdocmstrDto);
        }

        boolean reDraft = aprvlinesetMapper.checkAprvdocmstrBfDocNo(aprvdocmstrDto) > 0;

        /* 재기안일경우, 기존 문서 재기안(7)상태로 업데이트 */
        if (reDraft) {
            aprvdocmstrDto.setStatusCode(AprvType.RE_DRAFT);
            aprvlinesetMapper.updateAprvdocmstrBfDocNo(aprvdocmstrDto);
        }

        /* 임시저장과 결재요청사이 결재선이 달라졌을 경우가 있어 aprvdetail delete 후 insert */
        aprvlinesetMapper.deleteAprvdetail(aprvdocmstrDto);
        aprvlinesetMapper.deleteAprvviewer(aprvdocmstrDto);

        /* 결재라인이 수정될 경우 마스터 path order도 수정 */
        aprvlinesetMapper.updateAprvdocmstrAprvPathOrder(aprvdocmstrDto);

        /* 결재선 저장 */
        aprvdocmstrDto.getAprvdetailDtoList().forEach(row ->
                {
                    /* 첫번째 순서 = 기안자. 상태 값 : 결재완료 */
                    if (row.getAprvSeqNo() == 1) {
                        row.setStatusCode(AprvType.APPROVAL);
                        row.setAprvDateYN("Y");

                        /* 두번째 순서 = 결재자 or 확인자. 상태 값 : 결재요청 */
                    } else if (row.getAprvSeqNo() == 2) {
                        row.setStatusCode(AprvType.APPROVAL_REQ);

                        /* 세번째 이상 순서 = 결재자 or 확인자. 상태 값 : 결재대기 */
                    } else {
                        row.setStatusCode(AprvType.APPROVAL_WAIT);
                    }

                    /* 대리결재자 체크 */
                    setTransfer(row);
                    /* 결재자 저장 */
                    saveAprvline(row);
                }
        );

        /* 모든 결재자 데이터 */
        List<AprvdetailDto> aprvlines = aprvlinesetMapper.findAprvdetail(aprvdocmstrDto);
        if (aprvlines.isEmpty()) {
            throw new ServiceException("저장할 결재자의 정보가 없습니다.");
        }

        /* 결재자가 없는 경우 자동 승인 처리 */
        if (handleAutoApprovalForSingleDrafter(aprvdocmstrDto, aprvlines)) {
            return;
        }

        int result = 1;
        Iterator<AprvdetailDto> iterator = aprvlines.iterator();

        while(iterator.hasNext()) {
            AprvdetailDto approver = iterator.next();
            AprvdetailDto nextApporver = getNextApporver(aprvlines, approver);

            if (nextApporver != null && approver.getAprvEmplNo().equals(nextApporver.getAprvEmplNo())) {
                if (!aprvdocmstrDto.getAprvdetailDtoList().isEmpty()) {
                    aprvdocmstrDto.getAprvdetailDtoList().remove(0);
                }
                approvalAgree(aprvdocmstrDto);
                result++;
            } else {
                break;
            }

        }

        if (result != aprvlines.size()) {
            // 메일 발송
            aprvMailService.approvalMailSend(aprvdocmstrDto);
        }
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 결재 기각
     */
    public void approvalDismissal(AprvdocmstrDto aprvdocmstrDto) {
        /* 현재 결재자 데이터 */
        AprvdetailDto approver = aprvdocmstrDto.getAprvdetailDtoList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ServiceException("저장할 결재자의 정보가 없습니다."));

        /* 현재 결재자 반려 지정 */
        approver.setStatusCode(AprvType.DISMISSAL);
        approver.setAprvDateYN("Y");
        approver.setAprvCmnt(aprvdocmstrDto.getAprvCmnt());
        aprvlinesetMapper.updateAprvdetailStatus(approver);

        /* 현재 문서 반려 지정 */
        aprvdocmstrDto.setStatusCode(AprvType.DISMISSAL);
        aprvdocmstrDto.setApprvDateYN("Y");
        aprvlinesetMapper.updateAprvdocmstrStatus(aprvdocmstrDto);

        // 메일발송
        aprvMailService.approvalMailSend(aprvdocmstrDto);
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 결재 승인
     */
    public void approvalAgree(AprvdocmstrDto aprvdocmstrDto) {
        /* 현재 문서 결재요청 지정 */
        aprvdocmstrDto.setStatusCode(AprvType.APPROVAL_REQ);

        /* 현재 결재자 데이터 */
        AprvdetailDto approver = aprvdocmstrDto.getAprvdetailDtoList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ServiceException("저장할 결재자의 정보가 없습니다."));

        /* 현재 결재자 승인 지정 */
        approver.setStatusCode(AprvType.APPROVAL);
        approver.setAprvDateYN("Y");
        approver.setAprvCmnt(aprvdocmstrDto.getAprvCmnt());

        //log.info(">>>>결제자 의견>>>>"+approver.getAprvCmnt());

        aprvlinesetMapper.updateAprvdetailStatus(approver);

        /* 모든 결재자 데이터 */
        List<AprvdetailDto> aprvlines = aprvlinesetMapper.findAprvdetail(aprvdocmstrDto);

        /* 다음 결재자 데이터 */
        AprvdetailDto nextApprover = getNextApporver(aprvlines, approver);

        /* 모든 결재 완료 여부 */
        boolean approvalComplete = ObjectUtils.isEmpty(nextApprover);

        /* 모든 결재 완료시 연간근태내역 반영 */
        if (approvalComplete) {
            try {
                String formId = aprvdocmstrDto.getFormId();

                // 휴가신청서 || 휴가신청서(시간)
                if (formId.equals("LV") || formId.equals("LT")) {
                    aprvcompleteService.completeApprholi(aprvdocmstrDto);

                // 출장신청서
                } else if (formId.equals("TR")) {
                    aprvcompleteService.completeApprtrav(aprvdocmstrDto);
                }

                // 휴가 취소신청
                else if (formId.equals("LC")) {
                    aprvcompleteService.completeApprholicncl(aprvdocmstrDto);
                }

                // 출장 취소신청
                else if (formId.equals("TC")) {
                    aprvcompleteService.completeApprtravcncl(aprvdocmstrDto);
                }

                // 유연근무신청
//                else if (formId.equals("FW")) {
//                    aprvcompleteService.completeApprflex(aprvdocmstrDto);
//                }

                // 휴,복직 신청서
                else if (formId.equals("ST"))
                {
                    aprvcompleteService.completeApprStopEmp(aprvdocmstrDto);
                }

                // 단축근로신청
                else if(formId.equals("STS")){
                    aprvcompleteService.completeApprstopemp200(aprvdocmstrDto);
                }

                // 초과근무신청, 초과근무신청(부서장)
                else if (formId.equals("OT") || formId.equals("OTL")) {
                    aprvcompleteService.completeApprovtm(aprvdocmstrDto);
                }

                // 초과근무취소신청
                else if (formId.equals("OTC") || formId.equals("OTCL")) {
                    aprvcompleteService.completeApprcnclovtm(aprvdocmstrDto);
                }

                // 유연근무신청(신규)
                else if (formId.equals("WM")) {
                    aprvcompleteService.completeApprflex(aprvdocmstrDto);
                }

                // 대체휴가신청서
                else if (formId.equals("LF")) {
                    aprvcompleteService.completeApprshbholi(aprvdocmstrDto);
                }

                // 출퇴근변경신청서
                else if (formId.equals("CH")) {
                    aprvcompleteService.completeApprtmchng(aprvdocmstrDto);
                }

                // 경조금신청서
                else if (formId.equals("CN")) {
                    aprvcompleteService.completeApprcn(aprvdocmstrDto);
                }

                // 경조금신청서 (SHD)
                else if (formId.equals("CL")) {
                    aprvcompleteService.completeApprcl(aprvdocmstrDto);
                }

                // 근태수당신청서
                else if (formId.equals("OTP")) {
                    aprvcompleteService.completeApprovtmPay(aprvdocmstrDto);
                }

                // 휴일수당지급신청서
                else if (formId.equals("HP")) {
                    aprvcompleteService.completeApprholipay(aprvdocmstrDto);
                }

                // 대체휴가취소신청서
                else if (formId.equals("LFC")) {
                    aprvcompleteService.completeApprcnclsubholi(aprvdocmstrDto);
                } else if (formId.equals("WK")) {
                    aprvcompleteService.completeWrk(aprvdocmstrDto);
                } else if (formId.equals("TD")) {
                    aprvcompleteService.completeApprcn600(aprvdocmstrDto);
                }

                // 월근무계획신청서(업로드)
                else if (formId.equals("MW")) {
                    aprvcompleteService.completeMonthlyWorkPlan(aprvdocmstrDto);
                }

                // 교육신청
                else if (formId.equals("EDU")) {
                    aprvcompleteService.completeEdureq(aprvdocmstrDto);
                }
                // 교육취소신청
                else if (formId.equals("EDUC")) {
                    aprvcompleteService.completeEduclreq(aprvdocmstrDto);
                }
                // 교육결과보고서
                else if (formId.equals("EDUR")) {
                    aprvcompleteService.completeEdurslt(aprvdocmstrDto);
                }

                // 지각/조퇴/외출/결근신청, 지각/조퇴/외출/결근신청(부서장)
                else if (formId.equals("ANL") || formId.equals("ANLL")) {
                    aprvcompleteService.completeAppranl(aprvdocmstrDto);
                }

                // 지각/조퇴/외출/결근취소신청
                else if (formId.equals("ANLC") || formId.equals("ANLCL")) {
                    aprvcompleteService.completeApprcnclanl(aprvdocmstrDto);
                }

                /* 현재 문서 승인 지정 */
                aprvdocmstrDto.setStatusCode(AprvType.APPROVAL);
                aprvdocmstrDto.setApprvDateYN("Y");
                aprvlinesetMapper.updateAprvdocmstrStatus(aprvdocmstrDto);

                // 메일 발송
                aprvMailService.approvalMailSend(aprvdocmstrDto);

            } catch (ParseException e) {
                log.info(":::최종승인 실패. error msg : " + e.getMessage());
                throw new ServiceException("최종승인에 실패했습니다.");
            }

        } else {
            /* 최종 결재가 아니고 다음 결재자가 있을 경우, 상태 값 결재요청(APPROVAL_REQ)으로 변경 */
            nextApprover.setStatusCode(AprvType.APPROVAL_REQ);
            aprvlinesetMapper.updateAprvdetailStatus(nextApprover);

            // 다음 결재자/확인자에게 메신저 알림 발송
            aprvMailService.approvalMailSend(aprvdocmstrDto);
        }
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 결재 회수
     */
    public void approvalCancel(AprvdocmstrDto aprvdocmstrDto) {
        /* 회수 가능 여부 확인 */
        AprvdetailDto aprvdetailDto = new AprvdetailDto().of(aprvdocmstrDto);
        if(!checkCancelPossible(aprvdetailDto)) {
            throw new ServiceException("해당 문서는 이미 처리되어 회수 불가합니다.");
        }
        /* 현재 문서 회수 지정 */
        aprvdocmstrDto.setStatusCode(AprvType.APPROVAL_CNCL);
        aprvdocmstrDto.setApprvDateYN("Y");
        aprvlinesetMapper.updateAprvdocmstrStatus(aprvdocmstrDto);

        // 메일발송
        aprvMailService.approvalMailSend(aprvdocmstrDto);
    }


    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 문서, 결재라인 삭제
     */
    public void aprvlDelete(AprvdocmstrDto aprvdocmstrDto) {
        aprvlinesetMapper.deleteAprvmstr(aprvdocmstrDto);
        aprvlinesetMapper.deleteAprvdetail(aprvdocmstrDto);
    }

    /**
     * @param aprvlines, approver
     * @return AprvdetailDto
     * @Description param의 현재 결재자와 비교하여 다음 결재자 return
     */
    public AprvdetailDto getNextApporver(List<AprvdetailDto> aprvlines, AprvdetailDto nowApprover) {
        return aprvlines.stream()
                .filter(aprvline -> aprvline.getAprvSeqNo() == nowApprover.getAprvSeqNo() + 1)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param aprvdetailDto
     * @return
     * @Description 결재자의 대리결재자가 있을 경우 dto값 setting
     */
    public void setTransfer(AprvdetailDto aprvdetailDto) {
        /* 결재자의 대리결재자가 있을 경우 */
        if (aprvdetailDto.isApprover()) {
            CamelCaseMap transfer = aprvlinesetMapper.findAprvtransfer(aprvdetailDto);
            if (transfer != null && !transfer.isEmpty()) {
                String transEmplNo = transfer.get("transEmplNo").toString();
                String transInd = transfer.get("transInd").toString();
                aprvdetailDto.setTransEmplNo(transEmplNo);
                aprvdetailDto.setTransInd(transInd);
            }
        }
    }

    /**
     * @param aprvdetailDto
     * @return
     * @Description 결재자, 확인자, 수신자 저장
     */
    public void saveAprvline(AprvdetailDto aprvdetailDto) {
        /* 수신자 저장 */
        if (aprvdetailDto.isViewer()) {
            aprvlinesetMapper.insertAprvviewer(aprvdetailDto);
            /* 결재자, 확인자 저장 */
        } else {
            aprvlinesetMapper.insertAprvdetail(aprvdetailDto);
        }
    }

    /**
     * @param aprvdocmstrDto
     * @return
     * @Description 결재자 validate
     */
    private void validateApporvers(AprvdocmstrDto aprvdocmstrDto) {
        aprvdocmstrDto.getAprvdetailDtoList().stream()
                .filter(row -> row.isApprover() || row.isChecker())
                .findAny()
                .orElseThrow(() -> new ServiceException("결재자나 확인자의 정보를 입력해주세요."));
    }

    /**
     * @param aprvdetailDto
     * @return boolean
     * @Description 기안자 인지 체크
     */
    public boolean checkCreate(AprvdetailDto aprvdetailDto) {
        return aprvlinesetMapper.checkCreate(aprvdetailDto) > 0;
    }

    /**
     * 결재선설정(approval-pop), 문서양식결재선관리(approval-confirm-pop) 팝업에서 사용
     * 조직도 조회
     * @param aprvPopDto
     * @return
     */
    public List<CamelCaseMap> findAprvPopOrgChart(AprvPopDto aprvPopDto) {
        return aprvlinesetMapper.findAprvPopOrgChart(aprvPopDto);
    }

    /**
     * 결재선설정(approval-pop), 문서양식결재선관리(approval-confirm-pop) 팝업에서 사용
     * 사원 정보 검색에 의한 사원 조회
     * @param aprvPopDto
     * @return
     */
    public List<CamelCaseMap> findAprvPopEmplBySearch(AprvPopDto aprvPopDto) {
        return aprvlinesetMapper.findAprvPopEmplBySearch(aprvPopDto);
    }

    /**
     * 결재선설정(approval-pop), 문서양식결재선관리(approval-confirm-pop) 팝업에서 사용
     * 부서 선택에 의한 사원 조회
     * @param aprvPopDto
     * @return
     */
    public List<CamelCaseMap> findAprvPopEmplByOrg(AprvPopDto aprvPopDto) {
        return aprvlinesetMapper.findAprvPopEmplByOrg(aprvPopDto);
    }

    /**
     * @param aprvdocmstrDto
     * @return List<CamelCaseMap>
     * @Description 결재선 결재의견 리스트 조회 (1차~n차 결재자)
     */
    public List<CamelCaseMap> findDocAprvdetailList(AprvdocmstrDto aprvdocmstrDto) {
        return aprvlinesetMapper.findDocAprvdetailList(aprvdocmstrDto);
    }

    /**
     * 결재자가 기안자 1명뿐인 경우 자동 승인 처리
     *
     * 조직장이 상위 결재자 없이 직접 신청하는 경우,
     * 기안자를 결재자로도 추가하고 즉시 승인 처리하여 문서를 완료 상태로 만듦
     *
     * @param aprvdocmstrDto 결재문서 마스터 DTO
     * @param aprvlines 결재라인 목록
     * @return 자동 승인 처리 여부 (true: 자동 승인 완료, false: 일반 프로세스 진행)
     */
    private boolean handleAutoApprovalForSingleDrafter(AprvdocmstrDto aprvdocmstrDto, List<AprvdetailDto> aprvlines) {
        // 결재자가 기안자 1명뿐인지 확인
        if (aprvlines.size() != 1 || !AprvType.DRAFTER.equals(aprvlines.get(0).getAprvType())) {
            return false; // 일반 결재 프로세스 진행
        }

        // 기안자 정보
        AprvdetailDto drafter = aprvlines.get(0);

        // 기안자가 조직장인지 확인
        int leaderCount = aprvlinesetMapper.checkIfLeader(drafter);
        if (leaderCount == 0) {
            // 조직장이 아닌 일반 직원은 결재자 필수
            throw new ServiceException("결재자가 없습니다. 결재선을 설정해주세요.");
        }

        log.info(":::조직장 자가결재 - 기안자를 결재자로 추가 후 자동 승인 처리 - 문서번호: {}, 사번: {}",
                 aprvdocmstrDto.getDocNo(), drafter.getAprvEmplNo());

        // 기안자를 결재자로도 추가 (APRV_SEQ_NO = 2, 결재요청 상태)
        AprvdetailDto autoApprover = new AprvdetailDto();
        autoApprover.setDocNo(drafter.getDocNo());
        autoApprover.setAprvSeqNo(2L);
        autoApprover.setAprvType(AprvType.APPROVER);  // 결재자
        autoApprover.setAprvEmplNo(drafter.getAprvEmplNo());
        autoApprover.setStatusCode(AprvType.APPROVAL_REQ);
        autoApprover.setTransInd("");
        autoApprover.setTransEmplNo("");

        // DB에 결재자 추가
        aprvlinesetMapper.insertAprvdetail(autoApprover);

        // DTO에 담아서 approvalAgree() 호출
        aprvdocmstrDto.getAprvdetailDtoList().clear();
        aprvdocmstrDto.getAprvdetailDtoList().add(autoApprover);

        // 기존 승인 로직 호출 (모든 formId별 후처리 자동 실행)
        approvalAgree(aprvdocmstrDto);

        log.info(":::자동 승인 완료 - 문서번호: {}", aprvdocmstrDto.getDocNo());

        return true;
    }
}
