package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendgDto;
import hr.system.api.app.appai.mapper.AppaiappryearendgMapper;
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
public class AppaiappryearendgService {

    private final AppaiappryearendgMapper appaiappryearendgMapper;

    /**
     * 목표설정 조회
     * @param appaiappryearendgDto
     * @return
     */
    public List<CamelCaseMap> findAppaiappryearendgList(AppaiappryearendgDto appaiappryearendgDto){
        return appaiappryearendgMapper.findAppaiappryearendgList(appaiappryearendgDto);
    }

    /**
     * 목표설정 오픈여부 확인 데이터
     * @param appaiappryearendgDto
     * @return
     */
    public List<CamelCaseMap> checkInd(AppaiappryearendgDto appaiappryearendgDto){
        return appaiappryearendgMapper.checkInd(appaiappryearendgDto);
    }

    /**
     * 목표설정 전송여부 확인 데이터
     * @param appaiappryearendgDto
     * @return
     */
    public List<CamelCaseMap> checkApprserList(AppaiappryearendgDto appaiappryearendgDto){
        return appaiappryearendgMapper.checkApprserList(appaiappryearendgDto);
    }

    /**
     * 목표설정 저장/수정/삭제
     * @param appaiappryearendgList - 등록할 정보가 담긴 appaiappryearendgList
     * @return
     */
    public int saveAppaiappryearendg(List<AppaiappryearendgDto> appaiappryearendgList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappryearendgList)) {
            for(AppaiappryearendgDto appaiappryearendgDto : appaiappryearendgList) {
                switch(appaiappryearendgDto.getRowStatus()) {
                    case DataConst.INSERT :

                        int checkCount = appaiappryearendgMapper.checkCountNo(appaiappryearendgDto);
                        if( checkCount == 0) {
                            appaiappryearendgDto.setTaskSeqNo("1");
                        }else {
                            int lastSeq = appaiappryearendgMapper.checkYearSeqNo(appaiappryearendgDto);
                            appaiappryearendgDto.setTaskSeqNo(String.valueOf(lastSeq+1));
                        }
                        result += appaiappryearendgMapper.insertAppryearendg(appaiappryearendgDto);
                        break;

                    case DataConst.UPDATE :
                        result += appaiappryearendgMapper.updateAppryearendg(appaiappryearendgDto);
                        break;

                    case DataConst.DELETE :
                        result += appaiappryearendgMapper.deleteAppryearendg(appaiappryearendgDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappryearendgDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 목표설정 전송 및 전송취소
     * @param appaiappryearendgDto
     * @return int
     */
    public int updateSubmitStatus(AppaiappryearendgDto appaiappryearendgDto, String status) {
        int result = 0;
        if ("SUB".equals(status)){
            appaiappryearendgDto.setTypeCodeAppr("C");  // 목표설정
            appaiappryearendgDto.setExecInd("Y");
            result += appaiappryearendgMapper.updateSubmitStatus(appaiappryearendgDto);
        }else if ("CANCEL".equals(status)) {
            appaiappryearendgDto.setTypeCodeAppr("C");  // 목표설정
            appaiappryearendgDto.setExecInd("N");
            result += appaiappryearendgMapper.updateSubmitStatus(appaiappryearendgDto);

        }
        return result;
    }

}
