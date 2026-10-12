package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendpDto;
import hr.system.api.app.appai.mapper.AppaiappryearendpMapper;
import hr.system.core.DataConst;
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
public class AppaiappryearendpService {

    private final AppaiappryearendpMapper appaiappryearendpMapper;

    /**
     * 중간점검(담당자) 조회
     * @param appaiappryearendpDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappryearendpList(AppaiappryearendpDto appaiappryearendpDto){

        appaiappryearendpDto.setTypeCodeAppr("P"); // 중간점검 데이터 유무 확인하기위해
        if( appaiappryearendpMapper.checkAptask(appaiappryearendpDto) > 0 ) {
            return appaiappryearendpMapper.findAppaiappryearendpList(appaiappryearendpDto);
        }else {
            appaiappryearendpDto.setTypeCodeAppr("C"); // 없다면 목표설정 데이터 가져오기(초기)
            return appaiappryearendpMapper.findAppaiappryearendpList(appaiappryearendpDto);
        }
    }

    /**
     * 중간점검(담당자) 오픈여부
     * @param appaiappryearendpDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkInd(AppaiappryearendpDto appaiappryearendpDto){
        return appaiappryearendpMapper.checkInd(appaiappryearendpDto);
    }

    /**
     * 중간점검(담당자) 평가자 조회
     * @param appaiappryearendpDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkApprserList(AppaiappryearendpDto appaiappryearendpDto){
        return appaiappryearendpMapper.checkApprserList(appaiappryearendpDto);
    }

    /**
     * 중간점검(담당자) 저장/수정
     * @param appaiappryearendpList - 등록할 정보가 담긴 appaiappryearendpList
     * @return
     */
    public int saveAppaiappryearendp(List<AppaiappryearendpDto> appaiappryearendpList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappryearendpList)) {
            for(AppaiappryearendpDto appaiappryearendpDto : appaiappryearendpList) {
                switch(appaiappryearendpDto.getRowStatus()) {
                    case DataConst.UPDATE :

                        appaiappryearendpDto.setTypeCodeAppr("P"); // 평가유형코드 중간점검으로(P)
                        appaiappryearendpDto.setMakerCode("1"); // 담당자는 모든 컨트롤 가능 (저장 시 1차 평가자로 들어가는 처리)

                        // 개인과업 테이블
                        if( appaiappryearendpMapper.checkInsertAptask(appaiappryearendpDto) == 0 ) {
                            result += appaiappryearendpMapper.insertAptaskp(appaiappryearendpDto);
                        }else { // 데이터 있으면 업데이트
                            result += appaiappryearendpMapper.updateAptaskp(appaiappryearendpDto);
                        }
                        // 평가의견 테이블
                        if( appaiappryearendpMapper.checkInsertAptlkrsltp(appaiappryearendpDto) == 0 ) {
                            result += appaiappryearendpMapper.insertAptlkrsltp(appaiappryearendpDto);
                        }else { // 데이터 있으면 업데이트
                            result += appaiappryearendpMapper.updateAptlkrsltp(appaiappryearendpDto);
                        }
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappryearendpDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 중간점검(담당자) 전송 및 전송취소
     * @param appaiappryearendpDto, status
     * @return int
     */
    public int updateSubmitStatus(AppaiappryearendpDto appaiappryearendpDto, String status) {
        int result = 0;
        if ("SUB".equals(status)){
            appaiappryearendpDto.setTypeCodeAppr("P");  // 중간점검
            appaiappryearendpDto.setExecInd("Y");
            result += appaiappryearendpMapper.updateSubmitStatus(appaiappryearendpDto);
        }else if ("CANCEL".equals(status)) {
            appaiappryearendpDto.setTypeCodeAppr("P");  // 중간점검
            if (appaiappryearendpDto.getAppraiserSeqNo().equals("0")){
                appaiappryearendpDto.setExecInd("N");
            } else if(appaiappryearendpDto.getAppraiserSeqNo().equals("1")) {
                appaiappryearendpDto.setExecInd("N"); // 1차 평가자 취소시엔 R 로 업데이트? -> N 으로 업데이트(21.10.21)
            }
            result += appaiappryearendpMapper.updateSubmitStatus(appaiappryearendpDto);

        }
        return result;
    }

}
