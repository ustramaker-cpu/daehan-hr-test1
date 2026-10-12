package hr.system.api.app.appai.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistyDto;
import hr.system.api.app.appai.dto.AppaiappryearendyDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistyMapper;
import hr.system.api.app.appai.mapper.AppaiappryearendyaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppaiappryearendyaService {

    private final AppaiappryearendyaMapper appaiappryearendyaMapper;

    private final AppaiappobjlistyMapper appaiappobjlistyMapper;

    /**
     * 연말평가(관리자) 조회
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappryearendyaList(AppaiappryearendyDto appaiappryearendyDto){

        //데이터가 없으면 화면에서 추가해줌..
        appaiappryearendyDto.setTypeCodeAppr("Y"); // 연말평가 데이터 유무 확인하기위해
        return appaiappryearendyaMapper.findAppaiappryearendyaList(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 오픈여부
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkInd(AppaiappryearendyDto appaiappryearendyDto){
        AppaiappobjlistyDto appaiappobjlistyDto = new AppaiappobjlistyDto();
        appaiappobjlistyDto.setApprYear(appaiappryearendyDto.getApprYear());
        appaiappobjlistyDto.setApprSeqNo(appaiappryearendyDto.getApprSeqNo());
        appaiappobjlistyDto.setTypeCodeAppr(appaiappryearendyDto.getTypeCodeAppr());
        appaiappobjlistyDto.setEmplNo(appaiappryearendyDto.getUserId());
        return appaiappobjlistyMapper.findAppaiappobjlistyList(appaiappobjlistyDto);
    }

    /**
     * 연말평가(관리자) 오픈여부
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkIndEnd(AppaiappryearendyDto appaiappryearendyDto){
        AppaiappobjlistyDto appaiappobjlistyDto = new AppaiappobjlistyDto();
        appaiappobjlistyDto.setApprYear(appaiappryearendyDto.getApprYear());
        appaiappobjlistyDto.setApprSeqNo(appaiappryearendyDto.getApprSeqNo());
        appaiappobjlistyDto.setTypeCodeAppr(appaiappryearendyDto.getTypeCodeAppr());
        appaiappobjlistyDto.setEmplNo(appaiappryearendyDto.getUserId());
        return appaiappobjlistyMapper.findAppaiappobjlistyListEnd(appaiappobjlistyDto);
    }

    /**
     * 연말평가(관리자) 등급가져오기..
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> getGrade(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaMapper.getGrade(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 직무수행능력및 태도
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    public CamelCaseMap getWorkEvaluation(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaMapper.getWorkEvaluation(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 직무수행능력및 태도
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> getEvaluation(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaMapper.getEvaluation(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 평가자 조회
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkApprserList(AppaiappryearendyDto appaiappryearendyDto){
        return appaiappryearendyaMapper.checkApprserList(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 평가자 조회
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkApprserList2(AppaiappryearendyDto appaiappryearendyDto){
        return appaiappryearendyaMapper.checkApprserList2(appaiappryearendyDto);
    }

    //평가요소별 비중
    public CamelCaseMap getFactwgt(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaMapper.getFactwgt(appaiappryearendyDto);
    }

    //평가자별 기준
    public CamelCaseMap getAprswgt(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaMapper.getAprswgt(appaiappryearendyDto);
    }

    //평가자별 기준
    public List<CamelCaseMap> getPoint(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaMapper.getPoint(appaiappryearendyDto);
    }

    //평가자별 기준
    public List<CamelCaseMap> getAvgPoint(AppaiappryearendyDto appaiappryearendyDto) {
        return appaiappryearendyaMapper.getAvgPoint(appaiappryearendyDto);
    }

    /**
     * 연말평가(관리자) 저장/수정
     * @param appaiappryearendyList - 등록할 정보가 담긴 appaiappryearendyList
     * @return
     */
    public int saveAppaiappryearendy(AppaiappryearendyDto appaiappryearendyDto, List<AppaiappryearendyDto> appaiappryearendyList) {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(appaiappryearendyList)) {
            for(AppaiappryearendyDto innerappaiappryearendyDto : appaiappryearendyList) {

                //종합평정의견은 한건만 저장하므로 0번째만 등록한다.
                if( cnt == 0) {
                    innerappaiappryearendyDto.setMakerCode(appaiappryearendyDto.getAppraiserSeqNo());

                    if( "1".equals(appaiappryearendyDto.getAppraiserSeqNo() )) {
                        innerappaiappryearendyDto.setTalkOpn(appaiappryearendyDto.getTalkOpn1());
                    } else if ("2".equals(appaiappryearendyDto.getAppraiserSeqNo() )) {
                        innerappaiappryearendyDto.setTalkOpn(appaiappryearendyDto.getTalkOpn2());
                    }
                    // 평가의견 테이블
                    if( appaiappryearendyaMapper.checkInsertAptlkrslty(innerappaiappryearendyDto) == 0 ) {
                        result += appaiappryearendyaMapper.insertAptlkrslty(innerappaiappryearendyDto);
                    }else { // 데이터 있으면 업데이트
                        result += appaiappryearendyaMapper.updateAptlkrslty(innerappaiappryearendyDto);
                    }
                }

                log.info("innerappaiappryearendyDto.getRowStatus()===>"+innerappaiappryearendyDto.getRowStatus());
                innerappaiappryearendyDto.setTypeCodeAppr("Y"); // 평가유형코드 연말평가로(Y)
                innerappaiappryearendyDto.setApprGroupCode(appaiappryearendyDto.getApprGroupCode());
                innerappaiappryearendyDto.setAppraiserEmplNo(appaiappryearendyDto.getAppraiserEmplNo());
                innerappaiappryearendyDto.setAppraiserSeqNo(appaiappryearendyDto.getAppraiserSeqNo());

                innerappaiappryearendyDto.setCapaCode(innerappaiappryearendyDto.getTaskSeqNo());

                //1차인 경우는 실적횟수별로 업무평가가 이루어지지만
                //2차인 경우는 근무성적평정이라는것으로 데이터 한개만 들어감...
                //2차는 난이도, 완성도, 적시성, 계를 inner로 옮겨서 한건만 저장한다.

                //2차인경우...
                innerappaiappryearendyDto.setFactorCodeAppr(appaiappryearendyDto.getFactorCodeAppr());

                if( "2".equals(appaiappryearendyDto.getAppraiserSeqNo() )) {
                    innerappaiappryearendyDto.setLevelCode(appaiappryearendyDto.getLevelCode());
                    innerappaiappryearendyDto.setLevelPoint(appaiappryearendyDto.getLevelPoint());
                    innerappaiappryearendyDto.setCompCode(appaiappryearendyDto.getCompCode());
                    innerappaiappryearendyDto.setCompPoint(appaiappryearendyDto.getCompPoint());
                    innerappaiappryearendyDto.setTimeCode(appaiappryearendyDto.getTimeCode());
                    innerappaiappryearendyDto.setTimePoint(appaiappryearendyDto.getTimePoint());
                    innerappaiappryearendyDto.setCalPoint(appaiappryearendyDto.getCalPoint());
                    innerappaiappryearendyDto.setApprGroupCode("03");
                    innerappaiappryearendyDto.setFactorCodeAppr("PE");
                }

                // 팡가자 평가 등록  테이블 APCAPAAPPR
                if( appaiappryearendyaMapper.checkInsertApcapaappry(innerappaiappryearendyDto) == 0 ) {
                    result += appaiappryearendyaMapper.insertApcapaappry(innerappaiappryearendyDto);
                }else { // 데이터 있으면 업데이트
                    result += appaiappryearendyaMapper.updateApcapaappry(innerappaiappryearendyDto);
                }

                //2차인경우 한건만 넣고 break;
                if( "2".equals(appaiappryearendyDto.getAppraiserSeqNo() )) {
                    break;
                }
                cnt++;
            }

            //1차인경우 환산점수를 APPOINT 테이블에 데이터를 넣어준다.
            if( appaiappryearendyaMapper.checkInsertAppointy(appaiappryearendyDto) == 0 ) {
                result += appaiappryearendyaMapper.insertAppointy(appaiappryearendyDto);
            }else { // 데이터 있으면 업데이트
                result += appaiappryearendyaMapper.updateAppointy(appaiappryearendyDto);
            }

        }
        return result;
    }

    /**
     * 연말평가(관리자) 저장/수정
     * @param appaiappryearendyList - 등록할 정보가 담긴 appaiappryearendyList
     * @return
     */
    public int saveAppaiappryearendy2(AppaiappryearendyDto appaiappryearendyDto, List<AppaiappryearendyDto> appaiappryearendyList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappryearendyList)) {
            for(AppaiappryearendyDto innerappaiappryearendyDto : appaiappryearendyList) {
                log.info("innerappaiappryearendyDto.getRowStatus()===>"+innerappaiappryearendyDto.getRowStatus());

                innerappaiappryearendyDto.setTypeCodeAppr("Y"); // 평가유형코드 연말평가로(Y)
                innerappaiappryearendyDto.setAppraiserEmplNo(appaiappryearendyDto.getAppraiserEmplNo());
                innerappaiappryearendyDto.setAppraiserSeqNo(appaiappryearendyDto.getAppraiserSeqNo());

                //1차인경우...
                if( "1".equals(appaiappryearendyDto.getAppraiserSeqNo() )) {
                    innerappaiappryearendyDto.setLevelCode(innerappaiappryearendyDto.getAppraiserPoint1st());
                    innerappaiappryearendyDto.setLevelPoint(innerappaiappryearendyDto.getAppraiserPoint1stPoint());
                } else if( "2".equals(appaiappryearendyDto.getAppraiserSeqNo() )) {
                    innerappaiappryearendyDto.setLevelCode(innerappaiappryearendyDto.getAppraiserPoint2nd());
                    innerappaiappryearendyDto.setLevelPoint(innerappaiappryearendyDto.getAppraiserPoint2ndPoint());
                }

                innerappaiappryearendyDto.setOrgCodeFactor(appaiappryearendyDto.getOrgCodeFactor());
                innerappaiappryearendyDto.setEmplNo(appaiappryearendyDto.getUserId());
                innerappaiappryearendyDto.setApprYear(appaiappryearendyDto.getApprYear());
                innerappaiappryearendyDto.setApprSeqNo(appaiappryearendyDto.getApprSeqNo());
                innerappaiappryearendyDto.setFactorCodeAppr(appaiappryearendyDto.getFactorCodeAppr());

                int cnt = appaiappryearendyaMapper.checkOrleader(innerappaiappryearendyDto);
                if( cnt > 0) //부서장
                    innerappaiappryearendyDto.setApprGroupCode("01");
                else
                    innerappaiappryearendyDto.setApprGroupCode("02");

                // 팡가자 평가 등록  테이블 APCAPAAPPR
                if( appaiappryearendyaMapper.checkInsertApcapaappry(innerappaiappryearendyDto) == 0 ) {
                    result += appaiappryearendyaMapper.insertApcapaappry(innerappaiappryearendyDto);
                }else { // 데이터 있으면 업데이트
                    result += appaiappryearendyaMapper.updateApcapaappry(innerappaiappryearendyDto);
                }
            }
        }
        return result;
    }

    /**
     * 연말평가(관리자) 전송 및 전송취소
     * @param appaiappryearendyDto, status
     * @return int
     */
    public int updateSubmitStatus(AppaiappryearendyDto appaiappryearendyDto, String status) {
        int result = 0;
        if ("SUB".equals(status)){
            appaiappryearendyDto.setTypeCodeAppr("Y");  // 연말평가
            appaiappryearendyDto.setExecInd("Y");
            result += appaiappryearendyaMapper.updateSubmitStatus(appaiappryearendyDto);
        }else if ("CANCEL".equals(status)) {
            appaiappryearendyDto.setTypeCodeAppr("Y");  // 연말평가
            appaiappryearendyDto.setExecInd("N");
            result += appaiappryearendyaMapper.updateSubmitStatus(appaiappryearendyDto);

        }
        return result;
    }

}
