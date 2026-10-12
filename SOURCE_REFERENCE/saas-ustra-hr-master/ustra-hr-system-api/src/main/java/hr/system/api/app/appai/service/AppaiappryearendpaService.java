package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendpDto;
import hr.system.api.app.appai.mapper.AppaiappryearendpMapper;
import hr.system.api.app.appai.mapper.AppaiappryearendpaMapper;
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
public class AppaiappryearendpaService {

    private final AppaiappryearendpaMapper appaiappryearendpaMapper;

    /**
     * 중간점검(관리자) 조회
     * @param appaiappryearendpDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappryearendpaList(AppaiappryearendpDto appaiappryearendpDto){

        appaiappryearendpDto.setTypeCodeAppr("P"); // 중간점검 데이터 유무 확인하기위해
        if( appaiappryearendpaMapper.checkAptask(appaiappryearendpDto) > 0 ) {
            return appaiappryearendpaMapper.findAppaiappryearendpaList(appaiappryearendpDto);
        }else {
            appaiappryearendpDto.setTypeCodeAppr("C"); // 없다면 목표설정 데이터 가져오기(초기)
            return appaiappryearendpaMapper.findAppaiappryearendpaList(appaiappryearendpDto);
        }
    }

    /**
     * 중간점검(관리자) 오픈여부
     * @param appaiappryearendpDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkInd(AppaiappryearendpDto appaiappryearendpDto){
        return appaiappryearendpaMapper.checkInd(appaiappryearendpDto);
    }

    /**
     * 중간점검(관리자) 평가자 조회
     * @param appaiappryearendpDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkApprserList(AppaiappryearendpDto appaiappryearendpDto){
        return appaiappryearendpaMapper.checkApprserList(appaiappryearendpDto);
    }

    /**
     * 중간점검(관리자) 저장/수정
     * @param appaiappryearendpList - 등록할 정보가 담긴 appaiappryearendpList
     * @return
     */
    public int saveAppaiappryearendp(List<AppaiappryearendpDto> appaiappryearendpList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappryearendpList)) {
            for(AppaiappryearendpDto appaiappryearendpDto : appaiappryearendpList) {
                switch(appaiappryearendpDto.getRowStatus()) {
                    case DataConst.UPDATE :

                        // appaiappryearendpDto.setTypeCodeAppr("P"); // 평가유형코드 중간점검으로(P) - 관리자화면은 필요없음.

                        if ((appaiappryearendpDto.getMakerCode() == null || appaiappryearendpDto.getMakerCode().length() == 0) &&
                                (appaiappryearendpDto.getTalkOpn() == null || appaiappryearendpDto.getTalkOpn().length() == 0)) {
                            // 개인과업 테이블
                            if( appaiappryearendpaMapper.checkInsertAptask(appaiappryearendpDto) == 0 ) {
                                result += appaiappryearendpaMapper.insertAptaskp(appaiappryearendpDto);
                            }else { // 데이터 있으면 업데이트
                                result += appaiappryearendpaMapper.updateAptaskp(appaiappryearendpDto);
                            }
                        }else {
                            // 개인과업 테이블
                            if( appaiappryearendpaMapper.checkInsertAptask(appaiappryearendpDto) == 0 ) {
                                result += appaiappryearendpaMapper.insertAptaskp(appaiappryearendpDto);
                            }else { // 데이터 있으면 업데이트
                                result += appaiappryearendpaMapper.updateAptaskp(appaiappryearendpDto);
                            }

                            // 평가의견 테이블
                            if( appaiappryearendpaMapper.checkInsertAptlkrsltp(appaiappryearendpDto) == 0 ) {
                                result += appaiappryearendpaMapper.insertAptlkrsltp(appaiappryearendpDto);
                            }else { // 데이터 있으면 업데이트
                                result += appaiappryearendpaMapper.updateAptlkrsltp(appaiappryearendpDto);
                            }
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

}
