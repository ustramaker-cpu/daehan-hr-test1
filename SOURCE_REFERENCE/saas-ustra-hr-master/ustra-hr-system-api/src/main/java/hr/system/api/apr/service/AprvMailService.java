package hr.system.api.apr.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.common.AprvType;
import hr.system.api.apr.dto.AprvdetailDto;
import hr.system.api.apr.dto.AprvdocmstrDto;
import hr.system.api.apr.mapper.AprvlinesetMapper;
import hr.system.core.api.msg.dto.MsgApiDto;
import hr.system.core.api.msg.service.MsgApiService;
import hr.system.core.company.service.CompBasService;
import hr.system.core.emp.psmstinfo.dto.PsmstrDto;
import hr.system.core.emp.psmstinfo.mapper.Emppsmstinfo100Mapper;
import hr.system.core.mail.dto.MailDto;
import hr.system.core.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AprvMailService {

    private final MailService mailService;

    private final Emppsmstinfo100Mapper emppsmstinfo100Mapper;

    private final AprvlinesetMapper aprvlinesetMapper;

    private final CompBasService compBasService;

    private final MsgApiService msgApiService;
    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 결재 메일 발송
     */
    public void approvalMailSend(AprvdocmstrDto aprvdocmstrDto) {
        log.info(":::결재 메일 발송 시작");

        String statusCode = aprvdocmstrDto.getStatusCode();

        switch (statusCode) {
            /* 결재 요청 */
            case AprvType.APPROVAL_REQ:
                approvalReqMailSend(aprvdocmstrDto);
                break;
            /* 결재 완료 */
            case AprvType.APPROVAL:
                approvalCompleteMailSend(aprvdocmstrDto);
                //approvalCompleteViewerMailSend(aprvdocmstrDto); 수신자 메신저 알림은 안함.
                break;
            /* 결재 기각 */
            case AprvType.DISMISSAL:
                approvalDismissMailSend(aprvdocmstrDto);
                break;
            /* 회수 (메신저 알림 사용 안함) */
            case AprvType.APPROVAL_CNCL:
                //approvalCancelMailSend(aprvdocmstrDto);
                break;
            case AprvType.RE_DRAFT:
                break;
            default:
                log.info(":::존재하지 않는 결재 메일 발송 코드 : " + statusCode);
                throw new ServiceException("존재하지 않는 결재 메일 발송 코드입니다.");
        }
    }


    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 다음 결재자에게 결재 요청 메일 발송.
     */
    public void approvalReqMailSend(AprvdocmstrDto aprvdocmstrDto) {
        try {
            log.info(":::결재 요청 메일 발송!");
            PsmstrDto psmstrDto = new PsmstrDto();
            AprvdetailDto nextApprover = getNextApprover(aprvdocmstrDto);
            psmstrDto.setCoCode(nextApprover.getLoginCoId());
            psmstrDto.setEmplNo(nextApprover.getAprvEmplNo());
            CamelCaseMap psmstr = getPsmstr(psmstrDto);

            String subject = aprvdocmstrDto.getDocTitlNm(); // ex) **신청서-홍길동
            String receiverEmail = getEmail(psmstr);
            String receiverName = getEmplNameHan(psmstrDto);
            String mailDomain = getMailDomain(receiverEmail);
            String coId = compBasService.findCompany(mailDomain);
            String message = aprvdocmstrDto.getCreateEmplName()  + "님이 결재를 요청했습니다."; // ex) ***님이 결재를 요청했습니다.

            MailDto mailDto = new MailDto
                    .Builder(
                      MailService.SENDER_EMAIL    // 발신인  메일
                    , MailService.SENDER_NAME     // 발신인 이름
                    , receiverEmail               // 수신인 메일
                    , receiverName                // 수신인 이름
                    , subject                     // 제목
                    , message                     // 내용
                    , MailService.MailType.MAIL_APRV_REQ
            )
                    .build();
            mailDto.setCoCode(coId);
            setTemplateVariable(mailDto, aprvdocmstrDto);

            mailService.mailSend(mailDto);

            /* 메일 발송으로 복원. 메신저 연동은 사용 안함.
            log.info(":::결재 요청 메신저 발송!");
            AprvdetailDto nextApprover = getNextApprover(aprvdocmstrDto);

            String subject = "[결재요청] " + aprvdocmstrDto.getDocTitlNm();
            String message = aprvdocmstrDto.getDocTitlNm() + " 결재할 문서가 도착했습니다.";

            MsgApiDto msgDto = new MsgApiDto
                    .Builder(
                    aprvdocmstrDto.getCreateEmplNo(),       // 발송자 사번
                    List.of(nextApprover.getAprvEmplNo()),  // 수신자 사번
                    subject,
                    message,
                    aprvdocmstrDto.getDocNo(),
                    MsgApiService.MsgApiType.NOTE_LINK
            )
                    .build();

            msgApiService.msgApiSend(msgDto);
            */

        } catch (ServiceException e) {
            log.info(":::결재 요청 메일 발송 실패 : " + e.getMessage());
        }
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 결재 완료(승인) 메일 발송. To 기안자
     */
    public void approvalCompleteMailSend(AprvdocmstrDto aprvdocmstrDto) {
        try {
            log.info(":::결재 완료 메일 발송!");
            PsmstrDto psmstrDto = new PsmstrDto();
            AprvdetailDto drafter = getDrafter(aprvdocmstrDto);
            psmstrDto.setCoCode(drafter.getLoginCoId());
            psmstrDto.setEmplNo(drafter.getAprvEmplNo());
            CamelCaseMap psmstr = getPsmstr(psmstrDto);

            String subject = "[E-HR 결재완료] " + aprvdocmstrDto.getDocTitlNm();
            String receiverEmail = getEmail(psmstr);
            String receiverName = getEmplNameHan(psmstrDto);
            String mailDomain = getMailDomain(receiverEmail);
            String coId = compBasService.findCompany(mailDomain);
            String message = aprvdocmstrDto.getDocTitlNm() + " 문서가 결재 승인 되었습니다.";

            MailDto mailDto = new MailDto
                    .Builder(
                      MailService.SENDER_EMAIL     // 발신인 메일
                    , MailService.SENDER_NAME      // 발신인 이름
                    , receiverEmail                // 수신인 메일
                    , receiverName                 // 수신인 이름
                    , subject                      // 제목
                    , message                      // 내용
                    , MailService.MailType.MAIL_APRV_COMPLETE
            )
                    .build();
            mailDto.setCoCode(coId);
            setTemplateVariable(mailDto, aprvdocmstrDto);

            mailService.mailSend(mailDto);

            /* 메일 발송으로 복원. 메신저 연동은 사용 안함.
            log.info(":::결재 완료 메신저 발송!");
            AprvdetailDto drafter = getDrafter(aprvdocmstrDto);

            String subject = "[결재완료] " + aprvdocmstrDto.getDocTitlNm();
            String message = aprvdocmstrDto.getDocTitlNm() + " 문서가 결재 완료되었습니다.";

            MsgApiDto msgDto = new MsgApiDto
                    .Builder(
                    "",                      // 발송자 사번
                    List.of(drafter.getAprvEmplNo()),  // 수신자 사번
                    subject,
                    message,
                    aprvdocmstrDto.getDocNo(),
                    MsgApiService.MsgApiType.NOTE_LINK
            )
                    .build();

            msgApiService.msgApiSend(msgDto);
            */

        } catch (ServiceException e) {
            log.info(":::결재 승인 메일 발송 실패 : " + e.getMessage());
        }
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 수신 메일 발송. To 수신자
     */
    public void approvalCompleteViewerMailSend(AprvdocmstrDto aprvdocmstrDto) {
        log.info(":::수신 메일 발송!");
        aprvlinesetMapper.findViewers(aprvdocmstrDto).forEach(row -> {
            try {
                PsmstrDto psmstrDto = new PsmstrDto();
                psmstrDto.setCoCode(row.getLoginCoId());
                psmstrDto.setEmplNo(row.getEmplNo());
                CamelCaseMap psmstr = getPsmstr(psmstrDto);

                String subject = "[E-HR 문서수신] " + aprvdocmstrDto.getDocTitlNm();
                String receiverEmail = getEmail(psmstr);
                String receiverName = getEmplNameHan(psmstrDto);
                String mailDomain = getMailDomain(receiverEmail);
                String coId = compBasService.findCompany(mailDomain);
                String message = aprvdocmstrDto.getCreateEmplName()  + "님의 문서가 수신되었습니다.";

                MailDto mailDto = new MailDto
                        .Builder(
                          MailService.SENDER_EMAIL    // 발신인 메일
                        , MailService.SENDER_NAME     // 발신인 이름
                        , receiverEmail               // 수신인 메일
                        , receiverName                // 수신인 이름
                        , subject                     // 제목
                        , message                     // 내용
                        , MailService.MailType.MAIL_APRV_VIEWER
                )
                        .build();
                mailDto.setCoCode(coId);
                setTemplateVariable(mailDto, aprvdocmstrDto);

                mailService.mailSend(mailDto);
            } catch (ServiceException e) {
                log.info(":::결재 수신 메일 발송 실패 : " + e.getMessage());
            }

        });

    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 결재 기각 메일 발송. To 기안자
     */
    public void approvalDismissMailSend(AprvdocmstrDto aprvdocmstrDto) {
        try {
            log.info(":::결재 기각 메일 발송!");
            PsmstrDto psmstrDto = new PsmstrDto();
            AprvdetailDto drafter = getDrafter(aprvdocmstrDto);
            psmstrDto.setCoCode(drafter.getLoginCoId());
            psmstrDto.setEmplNo(drafter.getAprvEmplNo());
            CamelCaseMap psmstr = getPsmstr(psmstrDto);


            String subject = aprvdocmstrDto.getDocTitlNm();
            String receiverEmail = getEmail(psmstr);
            String receiverName = getEmplNameHan(psmstrDto);
            String mailDomain = getMailDomain(receiverEmail);
            String coId = compBasService.findCompany(mailDomain);
            String message = aprvdocmstrDto.getDocTitlNm() + " 문서가 기각/반려 되었습니다.";

            MailDto mailDto = new MailDto
                    .Builder(
                      MailService.SENDER_EMAIL    // 발신인 메일
                    , MailService.SENDER_NAME     // 발신인 이름
                    , receiverEmail               // 수신인 메일
                    , receiverName                // 수신인 이름
                    , subject                     // 제목
                    , message                     // 내용
                    , MailService.MailType.MAIL_APRV_DISMISS
            )
                    .build();
            mailDto.setCoCode(coId);
            setTemplateVariable(mailDto, aprvdocmstrDto);

            mailService.mailSend(mailDto);

            /* 메일 발송으로 복원. 메신저 연동은 사용 안함.
            log.info(":::결재 반려 메신저 발송!");
            AprvdetailDto drafter = getDrafter(aprvdocmstrDto);

            String subject = "[결재반려] " + aprvdocmstrDto.getDocTitlNm();
            String message = aprvdocmstrDto.getDocTitlNm() + " 문서가 반려 되었습니다.";

            MsgApiDto msgDto = new MsgApiDto
                    .Builder(
                    "",                      // 발송자 사번
                    List.of(drafter.getAprvEmplNo()),  // 수신자 사번
                    subject,
                    message,
                    aprvdocmstrDto.getDocNo(),
                    MsgApiService.MsgApiType.NOTE_LINK
            )
                    .build();

            msgApiService.msgApiSend(msgDto);
            */

        } catch (ServiceException e) {
            log.info(":::결재 기각 메일 발송 실패 : " + e.getMessage());
        }
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 결재 회수 메일 발송. To 기안자
     */
    public void approvalCancelMailSend(AprvdocmstrDto aprvdocmstrDto) {
        try {
            log.info(":::결재 회수 메일 발송!");
            PsmstrDto psmstrDto = new PsmstrDto();
            AprvdetailDto drafter = getDrafter(aprvdocmstrDto);
            psmstrDto.setCoCode(drafter.getLoginCoId());
            psmstrDto.setEmplNo(drafter.getAprvEmplNo());
            CamelCaseMap psmstr = getPsmstr(psmstrDto);

            String subject = aprvdocmstrDto.getDocTitlNm();
            String receiverEmail = getEmail(psmstr);
            String receiverName = getEmplNameHan(psmstrDto);
            String mailDomain = getMailDomain(receiverEmail);
            String coId = compBasService.findCompany(mailDomain);
            String message = aprvdocmstrDto.getDocTitlNm() + " 문서가 결재 회수 되었습니다.";

            MailDto mailDto = new MailDto
                    .Builder(
                      MailService.SENDER_EMAIL    // 발신인 메일
                    , MailService.SENDER_NAME     // 발신인 이름
                    , receiverEmail               // 수신인 메일
                    , receiverName                // 수신인 이름
                    , subject                     // 제목
                    , message                     // 내용
                    , MailService.MailType.MAIL_APRV_CANCEL
            )
                    .build();
            mailDto.setCoCode(coId);
            setTemplateVariable(mailDto, aprvdocmstrDto);

            mailService.mailSend(mailDto);
        } catch (ServiceException e) {
            log.info(":::결재 회수 메일 발송 실패 : " + e.getMessage());
        }
    }

    /**
     * @param psmstrDto
     * @return void
     * @Description 이름정보 가져오기
     */
    public String getEmplNameHan(PsmstrDto psmstrDto) {
        return Optional
                .ofNullable(aprvlinesetMapper.findPsmstinfo(psmstrDto).getEmplNameHan())
                .filter(StringUtils::isNotEmpty)
                .orElseThrow(() -> new ServiceException("메일이 발송되지 않았습니다. 수신자의 이름 정보가 없습니다."));
    }

    /**
     * @param map
     * @return void
     * @Description 메일정보 가져오기
     */
    public String getEmail(CamelCaseMap map) {
        return Optional
                .ofNullable(map.getString("email"))
                .filter(StringUtils::isNotEmpty)
                .orElseThrow(() -> new ServiceException("메일이 발송되지 않았습니다. 수신자의 메일 정보가 없습니다."));
    }

    /**
     * @param psmstrDto
     * @return void
     * @Description 결재자 정보 가져오기
     */
    public CamelCaseMap getPsmstr(PsmstrDto psmstrDto) {
        return Optional
                .ofNullable(emppsmstinfo100Mapper.findEmppsmstinfo120(psmstrDto))
                .orElseThrow(() -> new ServiceException("메일이 발송되지 않았습니다. 수신자의 정보가 존재하지 않습니다."));
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 기안자 정보 가져오기
     */
    public AprvdetailDto getDrafter(AprvdocmstrDto aprvdocmstrDto) {
        return aprvlinesetMapper.findAprvdetail(aprvdocmstrDto)
                .stream()
                .filter(row -> row.getAprvType().equals(AprvType.DRAFTER))
                .findFirst()
                .orElseThrow(() -> new ServiceException("메일이 발송되지 않았습니다. 기안자의 정보가 존재하지 않습니다."));
    }

    /**
     * @param aprvdocmstrDto
     * @return void
     * @Description 다음 결재자 정보 가져오기
     */
    public AprvdetailDto getNextApprover(AprvdocmstrDto aprvdocmstrDto) {
        return aprvlinesetMapper.findAprvdetail(aprvdocmstrDto)
                .stream()
                .filter(row -> StringUtils.isEmpty(row.getAprvDate()))
                .findFirst()
                .orElseThrow(() -> new ServiceException("메일이 발송되지 않았습니다. 기안자의 정보가 존재하지 않습니다."));
    }

    /**
     * @param mail
     * @return void
     * @Description 메일 도메인 주소 return
     */
    public String getMailDomain(String mail) {
        return mail.substring(mail.indexOf("@") + 1);
    }

    public String getMailId(String mail) {
        return mail.substring(0, mail.indexOf("@"));
    }

    private void setTemplateVariable(MailDto mailDto, AprvdocmstrDto aprvdocmstrDto) {
        mailDto.addVariable("receiverName", mailDto.getToName());
        mailDto.addVariable("docSubject", aprvdocmstrDto.getDocTitlNm());
        mailDto.addVariable("docCreateName", aprvdocmstrDto.getCreateEmplName());
    }
}
