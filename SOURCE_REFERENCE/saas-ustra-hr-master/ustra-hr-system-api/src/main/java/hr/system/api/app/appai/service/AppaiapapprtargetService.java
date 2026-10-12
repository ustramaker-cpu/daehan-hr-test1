package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiapapprcapaDto;
import hr.system.api.app.appai.dto.AppaiapapprindDto;
import hr.system.api.app.appai.dto.AppaiappraiserDto;
import hr.system.api.app.appai.mapper.AppaiapapprdetailMapper;
import hr.system.api.app.appai.mapper.AppaiapapprtargetMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AppaiapapprtargetService {

    private final AppaiapapprdetailMapper appaiapapprdetailMapper;
    private final AppaiapapprtargetMapper appaiapapprtargetMapper;

    /**
     * 평가 대상자 조회
     * @param : appaiapapprindDto
     * @return
     */
    public List<CamelCaseMap> findAppraisementTargetList(AppaiapapprindDto appaiapapprindDto){
        // 평가자 seqNo count
        Integer apappraiserSeqno = appaiapapprtargetMapper.countApappraiserSeqno(appaiapapprindDto);
        List<Integer> seqnoList = new ArrayList<>();
        if(apappraiserSeqno != null) {
            for (int i = 0; i <= apappraiserSeqno; i++) {
                seqnoList.add(i);
            }
        }
        appaiapapprindDto.setSeqnoList(seqnoList);

        return appaiapapprtargetMapper.findAppraisementTargetList(appaiapapprindDto);
    }

    /**
     * 대상자관리 추가/저장/삭제
     * @param : 등록할 정보가 담긴 appaiapapprindDtoList
     * @return
     */
    public int saveAppaiapapprindlist(List<AppaiapapprindDto> appaiapapprindDtoList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiapapprindDtoList)) {
            for (AppaiapapprindDto appaiapapprindDto : appaiapapprindDtoList) {
                AppaiapapprcapaDto appaiapapprcapaDto = new AppaiapapprcapaDto();
                appaiapapprcapaDto.setApprYear(appaiapapprindDto.getApprYear());
                appaiapapprcapaDto.setApprSeqNo(appaiapapprindDto.getApprSeqNo());
                appaiapapprcapaDto.setTypeCodeAppr(appaiapapprindDto.getTypeCodeAppr());
                appaiapapprcapaDto.setEmplNo(appaiapapprindDto.getEmplNo());
                appaiapapprcapaDto.setApprGroupCode(appaiapapprindDto.getApprGroupCode());

                switch (appaiapapprindDto.getRowStatus()) {
                    case DataConst.INSERT :
                        // 평가예외자 여부 확인
                        if((appaiapapprtargetMapper.checkApexcpter(appaiapapprindDto)) > 0) {
                            throw new ServiceException("[" + appaiapapprindDto.getEmplNo() + " - " + appaiapapprindDto.getEmplNameHan() + "] " + " 평가 예외자입니다.");
                        }

                        // 평가대상자 저장
                        result += appaiapapprtargetMapper.insertApapprind(appaiapapprindDto);

                        // 평가자 저장
                        appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getEmplNo());
                        appaiapapprindDto.setAppraiserSeqNo("0");
                        result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                        if(appaiapapprindDto.getAppraiserEmplNo1() != null) {
                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1());
                            appaiapapprindDto.setAppraiserSeqNo("1");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);
                        }

                        if(appaiapapprindDto.getAppraiserEmplNo2() != null) {
                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2());
                            appaiapapprindDto.setAppraiserSeqNo("2");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);
                        }

                        /*// 평가점수 저장
                        switch(appaiapapprindDto.getTypeCodeAppr()) {
                            case "C":   //목표설정
                                appaiapapprdetailMapper.insertTargetApapprcapa(appaiapapprcapaDto);
                                break;

                            case "P":   // 중간점검
//                            case "Y":   // 연말평가 *검증 후 적용
                                appaiapapprcapaDto.setSearchTypeCodeAppr("C");
                                appaiapapprdetailMapper.insertselectTargetApapprcapa(appaiapapprcapaDto);
                                break;

                            default:
                                log.debug("typeCodeAppr : {}", appaiapapprindDto.getTypeCodeAppr());
                                throw new ServiceException("msg.system.access.wrong");
                        }*/
                        break;

                    case DataConst.UPDATE :
                        // 평가대상자 수정
                        result += appaiapapprtargetMapper.updateApapprind(appaiapapprindDto);

                        // 평가자 수정
                        appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getEmplNo());
                        appaiapapprindDto.setAppraiserSeqNo("0");
                        appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto);

                        if(appaiapapprindDto.getAppraiserEmplNo1() != null) {
                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1());
                            appaiapapprindDto.setAppraiserSeqNo("1");
                            if ( appaiapapprtargetMapper.checkAppraiser(appaiapapprindDto) > 0 ) {
                                result += appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto);
                            }else {
                                result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);
                            }
                        }

                        if(appaiapapprindDto.getAppraiserEmplNo2() != null) {
                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2());
                            appaiapapprindDto.setAppraiserSeqNo("2");
                            if ( appaiapapprtargetMapper.checkAppraiser(appaiapapprindDto) > 0 ) {
                                result += appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto);
                            }else {
                                result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);
                            }
                        }
                        break;

                    case DataConst.DELETE :
                        int confirmApprInd = appaiapapprtargetMapper.checkConfmApprInd(appaiapapprindDto);
                        if(confirmApprInd > 0) {
                            throw new ServiceException("확정된 평가대상자는 삭제할 수 없습니다.");
                        }else {
                            result += appaiapapprtargetMapper.deleteApapprind(appaiapapprindDto);
                            result += appaiapapprtargetMapper.deleteAppraiser(appaiapapprindDto);
                            result += appaiapapprdetailMapper.deleteApapprcapa(appaiapapprcapaDto);
                            result += appaiapapprdetailMapper.deleteApapprpoint(appaiapapprcapaDto);
                        }
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiapapprindDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }

        return result;
    }

    /**
     * 대상자관리 저장/수정/삭제
     * @param : 등록할 정보가 담긴 appaiapapprindDtoList
     * @return
     */
    public int saveAppaiapapprindlist_(List<AppaiapapprindDto> appaiapapprindDtoList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiapapprindDtoList)) {
            for(AppaiapapprindDto appaiapapprindDto : appaiapapprindDtoList) {

                AppaiapapprcapaDto appaiapapprcapaDto = new AppaiapapprcapaDto();
                appaiapapprcapaDto.setApprYear(appaiapapprindDto.getApprYear());
                appaiapapprcapaDto.setApprSeqNo(appaiapapprindDto.getApprSeqNo());
                appaiapapprcapaDto.setTypeCodeAppr(appaiapapprindDto.getTypeCodeAppr());
                appaiapapprcapaDto.setEmplNo(appaiapapprindDto.getEmplNo());
                appaiapapprcapaDto.setApprGroupCode(appaiapapprindDto.getApprGroupCode());

                switch(appaiapapprindDto.getRowStatus()) {
                    case DataConst.INSERT :
                        // 평가예외자 여부 확인
                        if((appaiapapprtargetMapper.checkApexcpter(appaiapapprindDto)) > 0) {
                            throw new ServiceException("[" + appaiapapprindDto.getEmplNo() + " - " + appaiapapprindDto.getEmplNameHan() + "] " + " 평가 예외자입니다.");
                        }

                        appaiapapprindDto.setConfmIndSend("N");
                        result += appaiapapprtargetMapper.insertApapprind(appaiapapprindDto);

                        // 평가자 입력
                        // 1, 2차 평가자 둘 다 없을 때
                        if ( (appaiapapprindDto.getAppraiserEmplNo1st() == null || appaiapapprindDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiapapprindDto.getAppraiserEmplNo2nd() == null || appaiapapprindDto.getAppraiserEmplNo2nd().equals("") ) ) {

                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getEmplNo());
                            appaiapapprindDto.setAppraiserSeqNo("0");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);
                        }

                        // 1차 평가자만 있을 때
                        else if (  (appaiapapprindDto.getAppraiserEmplNo1st() != null || !appaiapapprindDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiapapprindDto.getAppraiserEmplNo2nd() == null || appaiapapprindDto.getAppraiserEmplNo2nd().equals("") )){

                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getEmplNo());
                            appaiapapprindDto.setAppraiserSeqNo("0");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1st());
                            appaiapapprindDto.setAppraiserSeqNo("1");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                        }

                        // 2차 평가자만 있을 때 ( 이 경우는 없음 )
                        else if( (appaiapapprindDto.getAppraiserEmplNo1st() == null || appaiapapprindDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiapapprindDto.getAppraiserEmplNo2nd() != null || !appaiapapprindDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getEmplNo());
                            appaiapapprindDto.setAppraiserSeqNo("0");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2nd());
                            appaiapapprindDto.setAppraiserSeqNo("2");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);
                        }

                        // 둘 다 있을 때
                        else if( (appaiapapprindDto.getAppraiserEmplNo1st() != null || !appaiapapprindDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiapapprindDto.getAppraiserEmplNo2nd() != null || !appaiapapprindDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getEmplNo());
                            appaiapapprindDto.setAppraiserSeqNo("0");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1st());
                            appaiapapprindDto.setAppraiserSeqNo("1");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                            appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2nd());
                            appaiapapprindDto.setAppraiserSeqNo("2");
                            result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);
                        }

                        // 평가점수 입력
                        switch(appaiapapprindDto.getTypeCodeAppr()) {
                            case "C":   //목표설정
                                appaiapapprdetailMapper.insertTargetApapprcapa(appaiapapprcapaDto);
                                break;

                            case "P":
                                appaiapapprdetailMapper.insertTargetApapprcapa(appaiapapprcapaDto);
                                break;

                            default:
                                log.debug("typeCodeAppr : {}", appaiapapprindDto.getTypeCodeAppr());
                                throw new ServiceException("msg.system.access.wrong");
                        }

                        break;

                    case DataConst.UPDATE :

                        appaiapapprindDto.setConfmIndSend("N");
                        result += appaiapapprtargetMapper.updateApapprind(appaiapapprindDto);
                        // result += appaiappobjlistgMapper.updateApobjdtl(appaiapapprindlistDto);

                        appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getEmplNo());
                        appaiapapprindDto.setAppraiserSeqNo("0");
                        appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto); // 평가조직을 수정 했을 때 0차엔 업데이트가 안되어서 추가

                        // 1, 2차 평가자 둘 다 없을 때
                        if ( (appaiapapprindDto.getAppraiserEmplNo1st() == null || appaiapapprindDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiapapprindDto.getAppraiserEmplNo2nd() == null || appaiapapprindDto.getAppraiserEmplNo2nd().equals("") ) ) {
                            break;
                        }
                        // 1차 평가자만 있을 때
                        if (  (appaiapapprindDto.getAppraiserEmplNo1st() != null || !appaiapapprindDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiapapprindDto.getAppraiserEmplNo2nd() == null || appaiapapprindDto.getAppraiserEmplNo2nd().equals("") )){

                            appaiapapprindDto.setAppraiserSeqNo("1");
                            if ( appaiapapprtargetMapper.checkAppraiser(appaiapapprindDto) == 1 ) {

                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1st());
                                result += appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto);

                            }else if ( appaiapapprtargetMapper.checkAppraiser(appaiapapprindDto) == 0 ) {

                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1st());
                                appaiapapprindDto.setAppraiserSeqNo("1");
                                result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);
                            }

                        }

                        // 2차 평가자만 있을 때 ( 이 경우는 없음 )
                        else if( (appaiapapprindDto.getAppraiserEmplNo1st() == null || appaiapapprindDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiapapprindDto.getAppraiserEmplNo2nd() != null || !appaiapapprindDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiapapprindDto.setAppraiserSeqNo("2");
                            if ( appaiapapprtargetMapper.checkAppraiser(appaiapapprindDto) == 1 ) {

                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2nd());
                                result += appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto);

                            }else if ( appaiapapprtargetMapper.checkAppraiser(appaiapapprindDto) == 0 ) {

                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2nd());
                                appaiapapprindDto.setAppraiserSeqNo("2");
                                result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);
                            }
                        }

                        // 둘 다 있을 때
                        else if( (appaiapapprindDto.getAppraiserEmplNo1st() != null || !appaiapapprindDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiapapprindDto.getAppraiserEmplNo2nd() != null || !appaiapapprindDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiapapprindDto.setAppraiserSeqNo("1");
                            int seqOne = appaiapapprtargetMapper.checkAppraiser(appaiapapprindDto);
                            appaiapapprindDto.setAppraiserSeqNo("2");
                            int seqTwo = appaiapapprtargetMapper.checkAppraiser(appaiapapprindDto);

                            if ( seqOne == 0 && seqTwo == 0 ) {

                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1st());
                                appaiapapprindDto.setAppraiserSeqNo("1");
                                result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2nd());
                                appaiapapprindDto.setAppraiserSeqNo("2");
                                result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                            }else if ( seqOne == 1 && seqTwo == 0 ) {
                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1st());
                                appaiapapprindDto.setAppraiserSeqNo("1");
                                result += appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto);

                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2nd());
                                appaiapapprindDto.setAppraiserSeqNo("2");
                                result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                            } else if ( seqOne == 0 && seqTwo == 1 ) {
                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1st());
                                appaiapapprindDto.setAppraiserSeqNo("1");
                                result += appaiapapprtargetMapper.insertAppraiser(appaiapapprindDto);

                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2nd());
                                appaiapapprindDto.setAppraiserSeqNo("2");
                                result += appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto);

                            } else if ( seqOne == 1 && seqTwo == 1 ) {
                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo1st());
                                appaiapapprindDto.setAppraiserSeqNo("1");
                                result += appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto);

                                appaiapapprindDto.setAppraiserEmplNo(appaiapapprindDto.getAppraiserEmplNo2nd());
                                appaiapapprindDto.setAppraiserSeqNo("2");
                                result += appaiapapprtargetMapper.updateAppraiser(appaiapapprindDto);

                            }

                        }
                        break;

                    case DataConst.DELETE :
                        // 확정여부 확인 후 삭제
                        if(0 < appaiapapprtargetMapper.checkConfmApprInd(appaiapapprindDto)) {
                            result = -1;
                        }else {
                            result += appaiapapprtargetMapper.deleteApapprind(appaiapapprindDto);
                            result += appaiapapprtargetMapper.deleteAppraiser(appaiapapprindDto);
                            result += appaiapapprdetailMapper.deleteApapprcapa(appaiapapprcapaDto);
                            result += appaiapapprdetailMapper.deleteApapprpoint(appaiapapprcapaDto);
                        }
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiapapprindDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }


    /**
     * 대상자관리 확정/취소/오픈/오픈취소/최종마감/최종마감취소
     * @param appaiapapprindDtoList
     * @return int
     */
    public int updateApapprindInd(List<AppaiapapprindDto> appaiapapprindDtoList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiapapprindDtoList)) {
            for (AppaiapapprindDto appaiapapprindDto : appaiapapprindDtoList) {
//                result += appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);
                result += appaiapapprtargetMapper.updateApapprindInd(appaiapapprindDto);
            }
        }

        return result;
    }

    /**
     * 대상자관리의 상태를 수정한다. (대상자 확정 여부, 담당확인 여부)
     * @param appaiapapprindDtoList
     * @return int
     */
    public int updateConfirm(List<AppaiapapprindDto> appaiapapprindDtoList, String status) {
        int result = 0;

        List<AppaiappraiserDto> fieldList = new ArrayList<AppaiappraiserDto>();

        if(CollectionUtils.isNotEmpty(appaiapapprindDtoList)) {
            for (AppaiapapprindDto appaiapapprindDto : appaiapapprindDtoList) {
                if ("CONFM".equals(status)) {  // 대상자 확정
                    appaiapapprindDto.setConfmInd("Y");
                    result += appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);

                } else if ("CONFM_CANCEL".equals(status)) {  // 대상자 확정 취소

                    appaiapapprindDto.setConfmInd("N");
                    result += appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);

                } else if ("CONFM_SEND".equals(status)) {  // 담당 확인

                    appaiapapprindDto.setConfmIndSend("Y");
                    result += appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);

                } else if ("CONFM_SEND_CANCEL".equals(status)) {  // 담당 확인 취소

                    appaiapapprindDto.setConfmIndSend("N");
                    result += appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);

                } else if ("OPEN".equals(status)) {  // 오픈 확인

                    appaiapapprindDto.setOpenInd("Y");
                    result += appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);

                } else if ("OPEN_CANCEL".equals(status)) {  // 오픈 취소

                    appaiapapprindDto.setOpenInd("N");
                    result += appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);

                } else if ("CLOSE_LAST".equals(status)) {  // 최종마감

                    appaiapapprindDto.setCloseInd("Y");
                    appaiapapprindDto.setCloseIndLast("Y");
                    result += appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);

                } else if ("CLOSE_LAST_CANCEL".equals(status)) {  // 최종마감 취소

                    appaiapapprindDto.setCloseInd("N");
                    appaiapapprindDto.setCloseIndLast("N");
                    result += appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);

                } else if ("CLOSE_EXEC_IND".equals(status)) {  // 1차평가전송

                    AppaiappraiserDto appaiappraiserDto = new AppaiappraiserDto();
                    appaiappraiserDto.setApprYear(appaiapapprindDto.getApprYear());
                    appaiappraiserDto.setApprSeqNo(appaiapapprindDto.getApprSeqNo());
                    appaiappraiserDto.setEmplNo(appaiapapprindDto.getEmplNo());
                    appaiappraiserDto.setTypeCodeAppr(appaiapapprindDto.getTypeCodeAppr());
                    appaiappraiserDto.setOrgCodeFactor(appaiapapprindDto.getOrgCodeFactor());
                    appaiappraiserDto.setAppraiserSeqNo("1");
                    appaiappraiserDto.setExecInd("Y");

                    result += appaiapapprdetailMapper.updateAppraiserFalg(appaiappraiserDto);

                } else if ("CLOSE_EXEC_IND_CANCEL".equals(status)) {  // 1차평가전송취소

                    AppaiappraiserDto appaiappraiserDto = new AppaiappraiserDto();
                    appaiappraiserDto.setApprYear(appaiapapprindDto.getApprYear());
                    appaiappraiserDto.setApprSeqNo(appaiapapprindDto.getApprSeqNo());
                    appaiappraiserDto.setEmplNo(appaiapapprindDto.getEmplNo());
                    appaiappraiserDto.setTypeCodeAppr(appaiapapprindDto.getTypeCodeAppr());
                    appaiappraiserDto.setOrgCodeFactor(appaiapapprindDto.getOrgCodeFactor());
                    appaiappraiserDto.setAppraiserSeqNo("1");
                    appaiappraiserDto.setExecInd("N");

                    result += appaiapapprdetailMapper.updateAppraiserFalg(appaiappraiserDto);
                }
            }
        }

        return result;
    }

    /**
     * 오픈여부 확인 데이터
     * @param appaiapapprindDto
     * @return
     */
    public List<CamelCaseMap> checkApprInd(AppaiapapprindDto appaiapapprindDto){
        return appaiapapprtargetMapper.checkApprInd(appaiapapprindDto);
    }

    /**
     * 평가의견 데이터
     * @param appaiapapprindDto
     * @return
     */
    public List<CamelCaseMap> findAppraiseInd(AppaiapapprindDto appaiapapprindDto){
        return appaiapapprtargetMapper.findAppraiseInd(appaiapapprindDto);
    }
}
