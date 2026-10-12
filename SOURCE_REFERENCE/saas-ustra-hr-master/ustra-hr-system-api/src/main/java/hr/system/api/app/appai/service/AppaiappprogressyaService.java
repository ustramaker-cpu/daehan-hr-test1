package hr.system.api.app.appai.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistyDto;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import hr.system.api.app.appai.dto.AppaiappryearendDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistyMapper;
import hr.system.api.app.appai.mapper.AppaiappprogressyaMapper;
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
public class AppaiappprogressyaService {

    private final AppaiappprogressyaMapper appaiappprogressyaMapper;

    private final AppaiappobjlistyMapper appaiappobjlistyMapper;

    /**
     * 중간점검 평가진행현황관리(관리자) 조회
     * @param appaiappobjlistyDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappprogressyaList(AppaiappobjlistyDto appaiappobjlistyDto){
        // 연말평가대상자 리스트 쿼리와 동일
        return appaiappobjlistyMapper.findAppaiappobjlistyList(appaiappobjlistyDto);
    }

    public List<CamelCaseMap> findAppaiappprogressyaListadmin(AppaiappobjlistyDto appaiappobjlistyDto){
        // 연말평가대상자 리스트 쿼리와 동일
        return appaiappobjlistyMapper.findAppaiappobjlistyListadmin(appaiappobjlistyDto);
    }


    public int appaiappprogressyaSave(AppaiappobjlistyDto appaiappobjlistyDto) {
        int result = 0;

        for(AppaiappobjlistyDto dto : appaiappobjlistyDto.getAppaiappobjlistyList()) {

            dto.setAppraiserEmplNo(appaiappobjlistyDto.getAppraiserEmplNo());
            dto.setAppraiserSeqNo(appaiappobjlistyDto.getAppraiserSeqNo());

            if(appaiappobjlistyDto.getAppraiserSeqNo().equals("1")){
                dto.setLastPoint(dto.getLastPoint1());
                dto.setMidPoint(dto.getMidPoint1());
                dto.setNote(dto.getNote1());
                dto.setTotAllotPoint(dto.getTotAllotPoint());
            } else if(appaiappobjlistyDto.getAppraiserSeqNo().equals("2")){
                dto.setLastPoint(dto.getLastPoint2());
                dto.setMidPoint(dto.getMidPoint2());
                dto.setNote(dto.getNote2());
            } else {
                dto.setLastPoint(dto.getLastPoint3());
                dto.setMidPoint(dto.getMidPoint3());
                dto.setNote(dto.getNote3());
            }

            result += appaiappobjlistyMapper.appaiappprogressyaSave(dto);
        }

        return result;
    }

    public List<CamelCaseMap> getAVGTotalPoint(AppaiappprogressyDto appaiappprogressyDto){
        return appaiappprogressyaMapper.getAVGTotalPoint(appaiappprogressyDto);
    }

    public List<CamelCaseMap> getGradeCount(AppaiappprogressyDto appaiappprogressyDto) {
        return appaiappprogressyaMapper.getGradeCount(appaiappprogressyDto);
    }

    public List<CamelCaseMap> getOrgleader(AppaiappprogressyDto appaiappprogressyDto){
        return appaiappprogressyaMapper.getOrgleader(appaiappprogressyDto);
    }

    public  int checkind(AppaiappprogressyDto appaiappprogressyDto){
        return  appaiappprogressyaMapper.checkind(appaiappprogressyDto);
    }

    public int checksend(AppaiappprogressyDto appaiappprogressyDto){
        return  appaiappprogressyaMapper.checksend(appaiappprogressyDto);
    }

    public List<CamelCaseMap> getApportion(AppaiappprogressyDto appaiappprogressyDto){
        return appaiappprogressyaMapper.getApportion(appaiappprogressyDto);
    }
    /**
     * 중간점검(관리자) 평가자 R 업데이트
     * @param appaiappprogressyDto, status
     * @return int
     */
    public int updateSubmitStatus(AppaiappprogressyDto appaiappprogressyDto, String status) {
        int result = 0;
        if ("READING".equals(status)) {
            appaiappprogressyDto.setExecInd("R");
            result += appaiappprogressyaMapper.update1stStatus(appaiappprogressyDto);

        }
        return result;
    }

    /**
     * 중간점검 평가진행현황관리(관리자) 상태를 수정한다. (오픈 여부, 평가마감 여부, 최종마감 여부)
     * @param appaiappprogressyList
     * @return int
     */
    public int updateStatusInd(List<AppaiappprogressyDto> appaiappprogressyList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappprogressyList)) {
            for (AppaiappprogressyDto appaiappprogressyDto : appaiappprogressyList) {
                if( "OPEN".equals(status) ) {  // 오픈
                    appaiappprogressyDto.setOpenInd("Y");
                    result += appaiappprogressyaMapper.updatAppprogresspaOpenInd(appaiappprogressyDto);

                } else if ( "OPEN_CANCEL".equals(status) ) {  // 오픈 취소
                    appaiappprogressyDto.setOpenInd("N");
                    result += appaiappprogressyaMapper.updatAppprogresspaOpenInd(appaiappprogressyDto);

                } else if ( "CLOSE".equals(status) ) {  // 평가마감
                    appaiappprogressyDto.setCloseInd("Y");
                    result += appaiappprogressyaMapper.updatAppprogresspaCloseInd(appaiappprogressyDto);

                } else if ( "CLOSE_CANCEL".equals(status) ) {  // 평가마감 취소
                    appaiappprogressyDto.setCloseInd("N");
                    result += appaiappprogressyaMapper.updatAppprogresspaCloseInd(appaiappprogressyDto);

                } else if ( "CLOSE_LAST".equals(status) ) {  // 최종마감
                    appaiappprogressyDto.setCloseIndLast("Y");
                    result += appaiappprogressyaMapper.updatAppprogresspaCloseIndLast(appaiappprogressyDto);

                } else if ( "CLOSE_LAST_CANCEL".equals(status) ) {  // 최종마감 취소
                    appaiappprogressyDto.setCloseIndLast("N");
                    result += appaiappprogressyaMapper.updatAppprogresspaCloseIndLast(appaiappprogressyDto);

                } else if ( "SUBMIT".equals(status) ) {  // 전송
                    appaiappprogressyDto.setTypeCodeAppr("Y");  // 연말평가
                    appaiappprogressyDto.setExecInd("Y");
                    //1차 전송완료이면
                    if( "Y".equals(appaiappprogressyDto.getAppraiserExecInd1st()) ) {
                        if( "Y".equals(appaiappprogressyDto.getAppraiserExecInd2nd()) ) {
                            //2차 전송완료
                            appaiappprogressyDto.setAppraiserEmplNo(appaiappprogressyDto.getAppraiserEmplNo2nd());
                            appaiappprogressyDto.setAppraiserSeqNo("2");
                        }
                        else{
                            //1차 전송후 2차 전송
                            appaiappprogressyDto.setAppraiserEmplNo(appaiappprogressyDto.getAppraiserEmplNo2nd());
                            appaiappprogressyDto.setAppraiserSeqNo("2");
                        }
                    } else {
                        //1차 전송
                        appaiappprogressyDto.setAppraiserEmplNo(appaiappprogressyDto.getAppraiserEmplNo1st());
                        appaiappprogressyDto.setAppraiserSeqNo("1");
                    }
                    result += appaiappprogressyaMapper.updateSubmitStatus(appaiappprogressyDto);

                } else if ( "SUBMIT_CANCEL".equals(status) ) {  // 전송 취소
                    appaiappprogressyDto.setTypeCodeAppr("Y");  // 연말평가
                    appaiappprogressyDto.setExecInd("N");
                    //1차 전송완료이면
                    if( "Y".equals(appaiappprogressyDto.getAppraiserExecInd1st()) ) {
                        if( "Y".equals(appaiappprogressyDto.getAppraiserExecInd2nd()) ) {
                            //2차 전송완료인경우 2차 전송취소..
                            appaiappprogressyDto.setAppraiserEmplNo(appaiappprogressyDto.getAppraiserEmplNo2nd());
                            appaiappprogressyDto.setAppraiserSeqNo("2");
                        }
                        else{
                            //1차 전송후 2차 전송
                            appaiappprogressyDto.setAppraiserEmplNo(appaiappprogressyDto.getAppraiserEmplNo1st());
                            appaiappprogressyDto.setAppraiserSeqNo("1");
                        }
                    } else {
                        appaiappprogressyDto.setAppraiserEmplNo(appaiappprogressyDto.getAppraiserEmplNo1st());
                        appaiappprogressyDto.setAppraiserSeqNo("1");
                    }
                    result += appaiappprogressyaMapper.updateSubmitStatus(appaiappprogressyDto);

                }
            }
        }
        return result;
    }

    public int pointupdate(AppaiappprogressyDto appaiappprogressyDto) {
        int result = 0;

        result += appaiappprogressyaMapper.updatePoint(appaiappprogressyDto);
        return result;
    }

    public int send(AppaiappobjlistyDto appaiappobjlistyDto) {
        int cnt = 0;

        for(AppaiappobjlistyDto dto : appaiappobjlistyDto.getAppaiappobjlistyList()){
            dto.setAppraiserSeqNo(appaiappobjlistyDto.getAppraiserSeqNo());
            dto.setAppraiserEmplNo(appaiappobjlistyDto.getAppraiserEmplNo());
            dto.setExecInd(appaiappobjlistyDto.getExecInd());
            cnt += appaiappprogressyaMapper.send(dto);
        }
        return cnt;
    }

    public int clear(AppaiappobjlistyDto appaiappobjlistyDto) {
        int result = 0;

        for(AppaiappobjlistyDto dto : appaiappobjlistyDto.getAppaiappobjlistyList()){
            dto.setAppraiserSeqNo(appaiappobjlistyDto.getAppraiserSeqNo());

            switch (dto.getAppraiserSeqNo()) {
                case "1":
                    dto.setAppraiserEmplNo(dto.getAppraiserEmplNo1st());
                    // 1차일 경우 평가표 내역까지 초기화 시켜야 한다.
                    result += appaiappprogressyaMapper.clearApapprpoint(dto);
                    break;
                case "2":
                    dto.setAppraiserEmplNo(dto.getAppraiserEmplNo2nd());
                    break;
                case "3":
                    dto.setAppraiserEmplNo(dto.getAppraiserEmplNo3nd());
                    break;
            }

            result += appaiappprogressyaMapper.clearAppraiser(dto); // 평가자 내역까지 초기화
        }

        return result;
    }
    public int apprSavePoint(AppaiappobjlistyDto appaiappobjlistyDto) {
        int result = 0;
        for(AppaiappobjlistyDto dto : appaiappobjlistyDto.getAppaiappobjlistyList()) {
            result += appaiappobjlistyMapper.apprSavePoint(dto);
            appaiappobjlistyMapper.apprUpdApprInd(dto);
        }
        return result;
    }
}
