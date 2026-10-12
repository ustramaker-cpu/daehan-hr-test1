package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.*;
import hr.system.api.app.appai.mapper.AppaiapappraiserMapper;
import hr.system.api.app.appai.mapper.AppaiapapprdetailMapper;
import hr.system.api.app.appai.mapper.AppaiapapprtargetMapper;
import hr.system.api.app.appmng.dto.AppmngapgradeDto;
import hr.system.api.app.appmng.mapper.AppmngapgradeMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AppaiapapprdetailService {

    private final AppaiapapprtargetService appaiapapprtargetService;

    private final AppaiapapprdetailMapper appaiapapprdetailMapper;
    private final AppaiapapprtargetMapper appaiapapprtargetMapper;
    private final AppaiapappraiserMapper appaiapappraiserMapper;
    private final AppmngapgradeMapper appmngapgradeMapper;

    /**
     * 목표설정, 중간점검 조회
     * @param appaiapapprcapaDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiapprdetailList(AppaiapapprcapaDto appaiapapprcapaDto){
        List<CamelCaseMap> result;

        AppaiapapprindDto appaiapapprindDto = new AppaiapapprindDto();
        appaiapapprindDto.setApprYear(appaiapapprcapaDto.getApprYear());
        appaiapapprindDto.setTypeCodeAppr(appaiapapprcapaDto.getTypeCodeAppr());
        appaiapapprindDto.setApprSeqNo(appaiapapprcapaDto.getApprSeqNo());
        appaiapapprindDto.setEmplNo(appaiapapprcapaDto.getEmplNo());

        // 평가자 seqNo count
        Integer apappraiserSeqno = appaiapapprtargetMapper.countApappraiserSeqno(appaiapapprindDto);
        List<Integer> seqnoList = new ArrayList<>();
        if(apappraiserSeqno != null) {
            for (int i = 0; i <= apappraiserSeqno; i++) {
                seqnoList.add(i);
            }
        }
        appaiapapprcapaDto.setSeqnoList(seqnoList);

        result = appaiapapprdetailMapper.findAppaiapprdetailList(appaiapapprcapaDto);
        if(result.isEmpty()) {
            if(appaiapapprcapaDto.getTypeCodeAppr().equals("P") || appaiapapprcapaDto.getTypeCodeAppr().equals("Y")) {
                appaiapapprcapaDto.setSearchTypeCodeAppr("C");
            }
            result = appaiapapprdetailMapper.findAppaiapprdetailDefaultList(appaiapapprcapaDto);
        }

        for (CamelCaseMap r : result) {
            if (r.getString("capaKind") != null) {
                r.replace("capaKind", HtmlUtils.htmlUnescape(r.getString("capaKind")));
            }
            if (r.getString("capaName") != null) {
                r.replace("capaName", HtmlUtils.htmlUnescape(r.getString("capaName")));
            }
            if (r.getString("capaDef") != null) {
                r.replace("capaDef", HtmlUtils.htmlUnescape(r.getString("capaDef")));
            }
            r.put("apappraiserSeqno", apappraiserSeqno);
        }

        return result;
    }

    /**
     * 성과 및 역량에 셋팅된 등급 조회하기
     * */
    public List<CamelCaseMap> findAppmngapgradeList(AppmngapgradeDto appmngapgradeDto){
        return appmngapgradeMapper.findAppmngapgradeList(appmngapgradeDto);
    }


    /**
     * 평자대상자에 따른 평가차수 조회(공통)
     * */
    public Integer findApappraiserSeqno(AppaiapapprindDto appaiapapprindDto){
        return appaiapapprtargetMapper.countApappraiserSeqno(appaiapapprindDto);
    }


    /**
     * 평가자 조회
     * @param appaiappraiserDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkApprserList(AppaiappraiserDto appaiappraiserDto){
        List<CamelCaseMap> result;

        result = appaiapapprdetailMapper.findApprserList(appaiappraiserDto);
        for (CamelCaseMap r : result) {
            if (r.getString("note") != null) {
                r.replace("note", HtmlUtils.htmlUnescape(r.getString("note")));
            }
        }

        return result;
    }

    /**
     * 평가데이터 성과 저장/수정/삭제
     * @param appaiapapprcapaDto
     * @return List<CamelCaseMap>
     */
    public int saveAppaiapapprcapa(AppaiapapprcapaDto appaiapapprcapaDto) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiapapprcapaDto.getAppaiapapprcapaList())) {
            for(AppaiapapprcapaDto appaiapapprcapa : appaiapapprcapaDto.getAppaiapapprcapaList()) {

                //특수 입력 처리 엔티티
               /* log.info(org.springframework.web.util.HtmlUtils.htmlUnescape(appaiapapprcapaDto.getCapaKind()));
                appaiapapprcapaDto.setCapaKind(org.springframework.web.util.HtmlUtils.htmlUnescape(appaiapapprcapaDto.getCapaKind()));
                appaiapapprcapaDto.setCapaName(org.springframework.web.util.HtmlUtils.htmlUnescape(appaiapapprcapaDto.getCapaName()));
                appaiapapprcapaDto.setCapaDef(org.springframework.web.util.HtmlUtils.htmlUnescape(appaiapapprcapaDto.getCapaDef()));*/

                //역량,성과 구분 코드
//                appaiapapprcapa.setTaskCode(appaiapapprcapaDto.getTaskCode());

                //평가자 정보
                appaiapapprcapa.setAppraiserEmplNo(appaiapapprcapaDto.getAppraiserEmplNo());
                appaiapapprcapa.setAppraiserSeqNo(appaiapapprcapaDto.getAppraiserSeqNo());

                //평가 차수별 포인트
                if(appaiapapprcapa.getAppraiserSeqNo().equals("0")){
                    appaiapapprcapa.setPoint(appaiapapprcapa.getPoint0());
                }else if(appaiapapprcapa.getAppraiserSeqNo().equals("1")){
                    appaiapapprcapa.setPoint(appaiapapprcapa.getPoint1());
                }else if(appaiapapprcapa.getAppraiserSeqNo().equals("2")){
                    appaiapapprcapa.setPoint(appaiapapprcapa.getPoint2());
                }else if(appaiapapprcapa.getAppraiserSeqNo().equals("3")){
                    appaiapapprcapa.setPoint(appaiapapprcapa.getPoint3());
                }


                switch(appaiapapprcapaDto.getRowStatus()) {
                    case DataConst.INSERT : case DataConst.UPDATE :
                        //그리드
                        result += appaiapapprdetailMapper.saveApapprcapa(appaiapapprcapa);
                        //포인트
                        result += appaiapapprdetailMapper.saveApapprpoint(appaiapapprcapa);
                        break;


                    case DataConst.DELETE :
                        result += appaiapapprdetailMapper.deleteApapprcapa(appaiapapprcapa);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiapapprcapaDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }

            if(appaiapapprcapaDto.getAppraiserSeqNo().equals("0")){
                appaiapapprcapaDto.setPeNote(appaiapapprcapaDto.getPeNote0());
                appaiapapprcapaDto.setCeNote(appaiapapprcapaDto.getCeNote0());
            }else if(appaiapapprcapaDto.getAppraiserSeqNo().equals("1")){
                appaiapapprcapaDto.setPeNote(appaiapapprcapaDto.getPeNote1());
                appaiapapprcapaDto.setCeNote(appaiapapprcapaDto.getCeNote1());
            }else if(appaiapapprcapaDto.getAppraiserSeqNo().equals("2")){
                appaiapapprcapaDto.setPeNote(appaiapapprcapaDto.getPeNote2());
                appaiapapprcapaDto.setCeNote(appaiapapprcapaDto.getCeNote2());
            }else if(appaiapapprcapaDto.getAppraiserSeqNo().equals("3")){
                appaiapapprcapaDto.setPeNote(appaiapapprcapaDto.getPeNote3());
                appaiapapprcapaDto.setCeNote(appaiapapprcapaDto.getCeNote3());
            }
            appaiapapprcapaDto.setTaskCode(appaiapapprcapaDto.getAppaiapapprcapaList().get(0).getTaskCode());
            result += appaiapapprtargetMapper.updateAppraiserNote(appaiapapprcapaDto);

        }
        return result;
    }


    /**
     * 전송 및 전송취소
     * @param appaiapappraiserDto
     * @return int
     */
    public int updateDetailInd(AppaiapappraiserDto appaiapappraiserDto) {
        int result = 0;

        if(appaiapappraiserDto != null) {
            // 평가대상자, 평가자의 전송 및 승인
            result += appaiapappraiserMapper.updateApappraiserInd(appaiapappraiserDto);

            // 평가대상자의 평가상태 변경
            if(appaiapappraiserDto.getAppraiserSeqNo().equals("0")) {
                AppaiapapprindDto appaiapapprindDto = new AppaiapapprindDto();
                appaiapapprindDto.setApprYear(appaiapappraiserDto.getApprYear());
                appaiapapprindDto.setApprSeqNo(appaiapappraiserDto.getApprSeqNo());
                appaiapapprindDto.setEmplNo(appaiapappraiserDto.getEmplNo());
                appaiapapprindDto.setTypeCodeAppr(appaiapappraiserDto.getTypeCodeAppr());
                appaiapapprindDto.setOrgCodeFactor(appaiapappraiserDto.getOrgCodeFactor());
                appaiapapprindDto.setConfmInd(appaiapappraiserDto.getConfmInd());
                appaiapapprindDto.setOpenInd(appaiapappraiserDto.getOpenInd());
                appaiapapprindDto.setSendInd(appaiapappraiserDto.getSendInd());
                appaiapapprindDto.setCloseInd(appaiapappraiserDto.getCloseInd());
                appaiapapprindDto.setCloseIndLast(appaiapappraiserDto.getCloseIndLast());
                result += appaiapapprtargetMapper.updateApapprindInd(appaiapapprindDto);
            }
        }

        return result;
    }

    /**
     * 평가데이터 성과 저장/수정/삭제
     * @param appaiapapprcapaDto
     * @return List<CamelCaseMap>
     */
    public int saveAppaiapapprcapa_(AppaiapapprcapaDto appaiapapprcapaDto) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiapapprcapaDto.getAppaiapapprcapaList())) {
            for(AppaiapapprcapaDto appaiapapprcapa : appaiapapprcapaDto.getAppaiapapprcapaList()) {

                //특수 입력 처리 엔티티
               /* log.info(org.springframework.web.util.HtmlUtils.htmlUnescape(appaiapapprcapaDto.getCapaKind()));
                appaiapapprcapaDto.setCapaKind(org.springframework.web.util.HtmlUtils.htmlUnescape(appaiapapprcapaDto.getCapaKind()));
                appaiapapprcapaDto.setCapaName(org.springframework.web.util.HtmlUtils.htmlUnescape(appaiapapprcapaDto.getCapaName()));
                appaiapapprcapaDto.setCapaDef(org.springframework.web.util.HtmlUtils.htmlUnescape(appaiapapprcapaDto.getCapaDef()));*/

                //역량,성과 구분 코드
                appaiapapprcapa.setTaskCode(appaiapapprcapaDto.getTaskCode());

                //평가자 정보
                appaiapapprcapa.setAppraiserEmplNo(appaiapapprcapaDto.getAppraiserEmplNo());
                appaiapapprcapa.setAppraiserSeqNo(appaiapapprcapaDto.getAppraiserSeqNo());

                switch(appaiapapprcapa.getRowStatus()) {
                    case DataConst.INSERT :
                        int checkCount = appaiapapprdetailMapper.checkApapprcapa(appaiapapprcapa);
                        if( checkCount == 0) {
                            appaiapapprcapa.setCapaSeqNo("1");
                        }else {
                            int lastSeq = appaiapapprdetailMapper.checkApprcapaSeqNo(appaiapapprcapa);
                            appaiapapprcapa.setCapaSeqNo(String.valueOf(lastSeq+1));
                        }
                        result += appaiapapprdetailMapper.insertApapprcapa(appaiapapprcapa);

                        break;

                    case DataConst.UPDATE :
                        if( appaiapapprdetailMapper.checkInsertApapprcapa(appaiapapprcapa) == 0 ) {
                            result += appaiapapprdetailMapper.insertApapprcapa(appaiapapprcapa);
                        }else { // 데이터 있으면 업데이트
                            result += appaiapapprdetailMapper.updateApapprcapa(appaiapapprcapa);
                        }

                        if(appaiapapprcapa.getTypeCodeAppr().equals("P")) {  //중간평가
                            // 평가점수
                            if( appaiapapprdetailMapper.checkInsertApapprpoint(appaiapapprcapa) == 0 ) {
                                result += appaiapapprdetailMapper.insertApapprpoint(appaiapapprcapa);
                            }else { // 데이터 있으면 업데이트
                                result += appaiapapprdetailMapper.updateApapprpoint(appaiapapprcapa);
                            }

                            // 평가의견
                            if(appaiapapprcapa.getMakerCode() != null) {
//                                    if (appaiapapprtargetMapper.checkAppraiser() == 0) {
//                                        result += appaiapapprtargetMapper.insertAppraiserNote(appaiapapprcapa);
//                                    } else {
                                   result += appaiapapprtargetMapper.updateAppraiserNote(appaiapapprcapa);
//                                    }
                            }

                        }
                        break;
                    case DataConst.DELETE :
                        result += appaiapapprdetailMapper.deleteApapprcapa(appaiapapprcapa);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiapapprcapa.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }

        }
        return result;
    }

    /**
     * 전송 및 전송취소
     * @param appaiappraiserDto, status
     * @return int
     */
    public int updateSubmitStatus(AppaiappraiserDto appaiappraiserDto, String status) {
        int result = 0;

        AppaiapapprindDto appaiapapprindDto = new AppaiapapprindDto();
        appaiapapprindDto.setApprYear(appaiappraiserDto.getApprYear());
        appaiapapprindDto.setApprSeqNo(appaiappraiserDto.getApprSeqNo());
        appaiapapprindDto.setEmplNo(appaiappraiserDto.getEmplNo());
        appaiapapprindDto.setTypeCodeAppr(appaiappraiserDto.getTypeCodeAppr());
        appaiapapprindDto.setOrgCodeFactor(appaiappraiserDto.getOrgCodeFactor());

        if ("SUB".equals(status)){
            if(appaiappraiserDto.getAppraiserSeqNo().equals("0")){
                // 평가상태 전송여부
                appaiapapprindDto.setSendInd("Y");
                appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);
            }
            //appaiappryearendDto.setTypeCodeAppr("P");  // 중간점검
            appaiappraiserDto.setExecInd("Y");
            result += appaiapapprdetailMapper.updateAppraiserFalg(appaiappraiserDto);
        }else if ("CANCEL".equals(status)) {
            if(appaiappraiserDto.getAppraiserSeqNo().equals("0")){
                // 평가상태 전송여부
                appaiapapprindDto.setSendInd("N");
                appaiapapprtargetMapper.updateApapprindFalg(appaiapapprindDto);
            }
            //appaiappryearendDto.setTypeCodeAppr("P");  // 중간점검
            appaiappraiserDto.setExecInd("N");
            result += appaiapapprdetailMapper.updateAppraiserFalg(appaiappraiserDto);
        }else if ("READ".equals(status)) {
            //appaiappryearendDto.setTypeCodeAppr("P");  // 중간점검
            appaiappraiserDto.setExecInd("R");
            result += appaiapapprdetailMapper.updateAppraiserFalg(appaiappraiserDto);
        }
        return result;
    }


}
