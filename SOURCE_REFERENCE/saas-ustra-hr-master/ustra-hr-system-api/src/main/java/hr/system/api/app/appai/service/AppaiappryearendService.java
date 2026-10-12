package hr.system.api.app.appai.service;


import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.*;
import hr.system.api.app.appai.mapper.AppaiappryearendMapper;
import hr.system.api.app.appai.mapper.AppaiappryearendyaMapper;
import hr.system.api.app.appmng.dto.AppmngapgradeDto;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
/**
 * 평가 관련 공통 로직 서비스
**/
public class AppaiappryearendService {

    private final AppaiappryearendMapper appaiappryearendMapper;

    private final AppaiappryearendyaMapper appaiappryearendyaMapper;

    public List<CamelCaseMap> findAppaiappryearendList(AppaiappryearendDto appaiappryearendDto){

        return appaiappryearendMapper.findAppaiappryearendList(appaiappryearendDto);
    }

    public List<CamelCaseMap> findApprcapaList(AppaiappryearendDto appaiappryearendDto){

        return appaiappryearendMapper.findApprcapaList(appaiappryearendDto);
    }

    /**
     * 성과 및 역량에 셋팅된 등급 조회하기
     * */
    public List<CamelCaseMap> findGradeList(AppmngapgradeDto appmngapgradeDto){
        AppaiappryearendyDto appaiappryearendyDto = new AppaiappryearendyDto();
        return appaiappryearendyaMapper.getGrade(appaiappryearendyDto);
    }

    /**
     * 평가자 차수 가져오기
     * */
    public List<CamelCaseMap> getAppraiserind(AppaiappryearendDto appaiappryearendDto){
        return appaiappryearendMapper.getAppraiserind(appaiappryearendDto);
    }

    /**
     * 평가자 조회
     * @param appaiappryearendDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkApprserList(AppaiappryearendDto appaiappryearendDto){
        return appaiappryearendMapper.checkApprserList(appaiappryearendDto);
    }

    /**
     * 평가데이터 성과 저장/수정/삭제
     * @param appaiappryearendDto
     * @return List<CamelCaseMap>
     */
    public int saveAppaiappryearend(AppaiappryearendDto appaiappryearendDto) {
        int result = 0;
        double pointCvtSum = 0; // 가중점수 합계

        for(AppaiappryearendDto dto : appaiappryearendDto.getAppaiappryearendList()) {

            // 추진결과 내역이 없는지 확인
            if (dto.getCapaDefAf() != null) {
                //특수문자 처리
                dto.setCapaDefAf(org.springframework.web.util.HtmlUtils.htmlUnescape(dto.getCapaDefAf()));
            }

            // 본인평가일 경우
            if(appaiappryearendDto.getAppraiserSeqNo().equals("0")){
                // 평가항목 수정(추진결과 및 성과 수정)
                result += appaiappryearendMapper.apapprcapaUpdate(dto);
            }

            dto.setAppraiserSeqNo(appaiappryearendDto.getAppraiserSeqNo());
            dto.setAppraiserEmplNo(appaiappryearendDto.getAppraiserEmplNo());
            if(appaiappryearendDto.getAppraiserSeqNo().equals("0")){
                dto.setPoint(dto.getPoint0());
                pointCvtSum += dto.getPointCvt0();
            } else if(appaiappryearendDto.getAppraiserSeqNo().equals("1")){
                dto.setPoint(dto.getPoint1());
                dto.setComment(dto.getComment1());
                pointCvtSum += dto.getPointCvt1();
            }
            // 평가점수 등록
            appaiappryearendMapper.apapprpointSave(dto);
        }

        // 평가요소별 비중 가져오기
        double weight = appaiappryearendMapper.apfactwgtWeight(appaiappryearendDto);
        double peCePoint = pointCvtSum * weight;

        if(appaiappryearendDto.getFactorCodeAppr().equals("PE")){
            appaiappryearendDto.setPePoint(peCePoint);

            if(appaiappryearendDto.getAppraiserSeqNo().equals("0")){
                appaiappryearendDto.setPeNote(appaiappryearendDto.getPeNote0());
            } else {
                appaiappryearendDto.setPeNote(appaiappryearendDto.getPeNote1());
            }
        } else {
            appaiappryearendDto.setCePoint(peCePoint);

            if(appaiappryearendDto.getAppraiserSeqNo().equals("0")){
                appaiappryearendDto.setCeNote(appaiappryearendDto.getCeNote0());
            } else {
                appaiappryearendDto.setCeNote(appaiappryearendDto.getCeNote1());
            }
        }

        // 평가자별 평가정보 저장
        result += appaiappryearendMapper.appraiserUpdate(appaiappryearendDto);

        /*for(AppaiappryearendDto appaiappryearend : appaiappryearendDto.getAppaiappryearendList()) {
            switch(appaiappryearend.getRowStatus()) {

                case DataConst.INSERT :

                    int checkCount = appaiappryearendMapper.checkAptask(appaiappryearend);
                    if( checkCount == 0) {
                        appaiappryearend.setTaskSeqNo("1");
                    }else {
                        int lastSeq = appaiappryearendMapper.checkYearSeqNo(appaiappryearend);
                        appaiappryearend.setTaskSeqNo(String.valueOf(lastSeq+1));
                    }
                    result += appaiappryearendMapper.insertAppryearend(appaiappryearend);

                    break;

                case DataConst.UPDATE :

                    // 개인과업 테이블
                    if( appaiappryearendMapper.checkInsertAptask(appaiappryearend) == 0 ) {
                        result += appaiappryearendMapper.insertAptaskp(appaiappryearend);
                    }else { // 데이터 있으면 업데이트
                        result += appaiappryearendMapper.updateAptaskp(appaiappryearend);
                    }

                    if (StringUtils.isNotEmpty(appaiappryearendDto.getMakerCode()))
                    {
                        appaiappryearend.setMakerCode(appaiappryearendDto.getMakerCode());
                        appaiappryearend.setTalkOpn(appaiappryearendDto.getTalkOpn());

                        // 평가의견 테이블
                        if( appaiappryearendMapper.checkInsertAptlkrsltp(appaiappryearend) == 0 ) {
                            result += appaiappryearendMapper.insertAptlkrsltp(appaiappryearend);
                        }else { // 데이터 있으면 업데이트
                            result += appaiappryearendMapper.updateAptlkrsltp(appaiappryearend);
                        }
                    }

                    break;
                case DataConst.DELETE :
                    result += appaiappryearendMapper.deleteAppryearend(appaiappryearend);
                    break;

                default :
                    log.debug("RowStatus : {}", appaiappryearend.getRowStatus());
                    throw new ServiceException("msg.system.access.wrong");
            }
        }*/
        return result;
    }

    /**
     * 평가데이터 성과 저장/수정/삭제
     * @param appaiappryearendDto
     * @return List<CamelCaseMap>
     */
    public int saveAppaiappryearendCapa(AppaiappryearendDto appaiappryearendDto) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappryearendDto.getAppaiappryearendList())) {
            for(AppaiappryearendDto appaiappryearend : appaiappryearendDto.getAppaiappryearendList()) {

                appaiappryearend.setEmplNo(appaiappryearendDto.getEmplNo());
                appaiappryearend.setFactorCodeAppr(appaiappryearendDto.getFactorCodeAppr());
                appaiappryearend.setTypeCodeAppr(appaiappryearendDto.getTypeCodeAppr());
                appaiappryearend.setOrgCodeFactor(appaiappryearendDto.getOrgCodeFactor());
                appaiappryearend.setAppraiserSeqNo(appaiappryearendDto.getAppraiserSeqNo());
                appaiappryearend.setAppraiserEmplNo(appaiappryearendDto.getAppraiserEmplNo());

                if(appaiappryearend.getAppraiserSeqNo().equals("0")) {
                    appaiappryearend.setLevelCode(appaiappryearend.getLevelCode0st());
                    appaiappryearend.setCalPoint(appaiappryearend.getCalPoint0st());
                }
                else if(appaiappryearend.getAppraiserSeqNo().equals("1")) {
                    appaiappryearend.setLevelCode(appaiappryearend.getLevelCode1st());
                    appaiappryearend.setCalPoint(appaiappryearend.getCalPoint1st());
                }

                switch(appaiappryearend.getRowStatus()) {

                    case DataConst.INSERT :

                        if( appaiappryearendMapper.checkApcapaappr(appaiappryearend) == 0 ) {
                            result += appaiappryearendMapper.insertAppryearend(appaiappryearend);
                        }

                        break;

                    case DataConst.UPDATE :

                        // 개인과업 테이블
                        if( appaiappryearendMapper.checkApcapaappr(appaiappryearend) == 0 ) {
                            result += appaiappryearendMapper.insertApcapaappr(appaiappryearend);
                        }else { // 데이터 있으면 업데이트
                            result += appaiappryearendMapper.updateApcapaappr(appaiappryearend);
                        }

                        if (StringUtils.isNotEmpty(appaiappryearendDto.getMakerCode()))
                        {
                            appaiappryearend.setMakerCode(appaiappryearendDto.getMakerCode());
                            appaiappryearend.setTalkOpn(appaiappryearendDto.getTalkOpn());

                            // 평가의견 테이블
                            if( appaiappryearendMapper.checkInsertAptlkrsltp(appaiappryearend) == 0 ) {
                                result += appaiappryearendMapper.insertAptlkrsltp(appaiappryearend);
                            }else { // 데이터 있으면 업데이트
                                result += appaiappryearendMapper.updateAptlkrsltp(appaiappryearend);
                            }
                        }

                        break;
                    case DataConst.DELETE :
                        result += appaiappryearendMapper.deleteAppryearend(appaiappryearend);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappryearend.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 오픈여부 확인 데이터
     * @param appaiappryearendDto
     * @return
     */
    public List<CamelCaseMap> checkInd(AppaiappryearendDto appaiappryearendDto){
        return appaiappryearendMapper.checkInd(appaiappryearendDto);
    }

    /**
     * 전송 및 전송취소
     * @param appaiappryearendDto, status
     * @return int
     */
    public int updateSubmitStatus(AppaiappryearendDto appaiappryearendDto, String status) {
        int result = 0;

        // 평가자 테이블 업데이트
        result += appaiappryearendMapper.updateSubmitStatus(appaiappryearendDto);

        // 평가대상자 테이블 업데이트(전송여부)
        //appaiappryearendMapper.apapprindSendIndYn(appaiappryearendDto);

        return result;
    }
}
