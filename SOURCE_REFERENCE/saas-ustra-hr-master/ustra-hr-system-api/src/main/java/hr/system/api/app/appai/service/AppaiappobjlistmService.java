package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.dto.AppaiappobjlistmDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistgMapper;
import hr.system.api.app.appai.mapper.AppaiappobjlistmMapper;
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
public class AppaiappobjlistmService {

    private final AppaiappobjlistmMapper appaiappobjlistmMapper;

    /**
     * 다면평가 평가대상자 조회
     * @param appaiappobjlistmDto
     * @return
     */
    public List<CamelCaseMap> findAppaiappobjlistmObjList(AppaiappobjlistmDto appaiappobjlistmDto){
        return appaiappobjlistmMapper.findAppaiappobjlistmObjList(appaiappobjlistmDto);
    }

    /**
     * 다면평가 평가자 조회
     * @param appaiappobjlistmDto
     * @return
     */
    public List<CamelCaseMap> findAppaiappobjlistmSerList(AppaiappobjlistmDto appaiappobjlistmDto) {
        return appaiappobjlistmMapper.findAppaiappobjlistmSerList(appaiappobjlistmDto);
    }

    /**
     * 다면평가 평가대상자 저장/추가/삭제
     * @param appaiappobjlistmList - 등록할 정보가 담긴 appaiappobjlistmList
     * @return
     */
    public int saveApapprobj(List<AppaiappobjlistmDto> appaiappobjlistmList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistmList)) {
            for(AppaiappobjlistmDto appaiappobjlistmDto : appaiappobjlistmList) {
                switch(appaiappobjlistmDto.getRowStatus()) {
                    case DataConst.INSERT :
                        result += appaiappobjlistmMapper.insertApapprobj(appaiappobjlistmDto);
                        break;

                    case DataConst.UPDATE :
                        result += appaiappobjlistmMapper.updateApapprobj(appaiappobjlistmDto);
                        break;

                    case DataConst.DELETE :
                        result += appaiappobjlistmMapper.deleteApapprobj(appaiappobjlistmDto);
                        result += appaiappobjlistmMapper.deleteApapprsermAll(appaiappobjlistmDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappobjlistmDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 다면평가 평가자 저장/추가/삭제
     * @param appaiappobjlistmList - 등록할 정보가 담긴 appaiappobjlistmList
     * @return
     */
    public int saveApapprserm(List<AppaiappobjlistmDto> appaiappobjlistmList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistmList)) {
            for(AppaiappobjlistmDto appaiappobjlistmDto : appaiappobjlistmList) {
                switch(appaiappobjlistmDto.getRowStatus()) {
                    case DataConst.INSERT :
                        result += appaiappobjlistmMapper.insertApapprserm(appaiappobjlistmDto);
                        break;

                    case DataConst.UPDATE :
                        break;

                    case DataConst.DELETE :
                        result += appaiappobjlistmMapper.deleteApapprserm(appaiappobjlistmDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappobjlistmDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 다면평가 대상자관리의 상태 수정 (대상자 확정 여부, 담당확인 여부)
     * @param appaiappobjlistmList
     * @return int
     */
    public int updateConfirm(List<AppaiappobjlistmDto> appaiappobjlistmList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistmList)) {
            for (AppaiappobjlistmDto appaiappobjlistmDto : appaiappobjlistmList) {
                if( "CONFM".equals(status) ) {  // 대상자 확정
                    appaiappobjlistmDto.setConfmInd("Y");
                    result += appaiappobjlistmMapper.updateApapprobjConfirm(appaiappobjlistmDto);

                } else if ( "CONFM_CANCEL".equals(status) ) {  // 대상자 확정 취소
                    appaiappobjlistmDto.setConfmInd("N");
                    result += appaiappobjlistmMapper.updateApapprobjConfirm(appaiappobjlistmDto);

                } else if ( "CONFM_SEND".equals(status) ) {  // 담당 확인
                    appaiappobjlistmDto.setConfmIndSend("Y");
                    result += appaiappobjlistmMapper.updateApapprobjConfirmSend(appaiappobjlistmDto);

                } else if ( "CONFM_SEND_CANCEL".equals(status) ) {  // 담당 확인 취소
                    appaiappobjlistmDto.setConfmIndSend("N");
                    result += appaiappobjlistmMapper.updateApapprobjConfirmSend(appaiappobjlistmDto);

                }
            }
        }
        return result;
    }

}
