package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendgDto;
import hr.system.api.app.appai.dto.AppaiappryearendmDto;
import hr.system.api.app.appai.dto.AppaiappryearendpDto;
import hr.system.api.app.appai.mapper.AppaiappryearendgMapper;
import hr.system.api.app.appai.mapper.AppaiappryearendmMapper;
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
public class AppaiappryearendmService {

    private final AppaiappryearendmMapper appaiappryearendmMapper;

    /**
     * 다면평가(담당자) 조회
     * @param appaiappryearendmDto
     * @return
     */
    public List<CamelCaseMap> findAppaiappryearendmList(AppaiappryearendmDto appaiappryearendmDto){
        // 평가점수테이블 데이터 유무 확인
        if (appaiappryearendmMapper.checkCapaApprm(appaiappryearendmDto) == 0) {
            return appaiappryearendmMapper.findAppaiappryearendmCapaList(appaiappryearendmDto);
        }else {
            return appaiappryearendmMapper.findAppaiappryearendmList(appaiappryearendmDto);
        }
    }

    /**
     * 역량테이블 조회
     * @param appaiappryearendmDto
     * @return
     */
    public List<CamelCaseMap> findCapaList(AppaiappryearendmDto appaiappryearendmDto){
        return appaiappryearendmMapper.findCapaList(appaiappryearendmDto);
    }

    /**
     * 평가등급코드 조회
     * @param appaiappryearendmDto
     * @return
     */
    public List<CamelCaseMap> findGradeList(AppaiappryearendmDto appaiappryearendmDto){
        return appaiappryearendmMapper.findGradeList(appaiappryearendmDto);
    }

    /**
     * 오픈여부 조회
     * @param appaiappryearendmDto
     * @return
     */
    public List<CamelCaseMap> checkIndObj(AppaiappryearendmDto appaiappryearendmDto){
        return appaiappryearendmMapper.checkIndObj(appaiappryearendmDto);
    }

    /**
     * 평가자 전송여부 조회
     * @param appaiappryearendmDto
     * @return
     */
    public List<CamelCaseMap> checkApprserList(AppaiappryearendmDto appaiappryearendmDto){
        return appaiappryearendmMapper.checkApprserList(appaiappryearendmDto);
    }

    /**
     * 다면평가(담당자) 저장/수정/삭제
     * @param appaiappryearendmList - 등록할 정보가 담긴 appaiappryearendmList
     * @return
     */
    public int saveAppaiappryearendm(List<AppaiappryearendmDto> appaiappryearendmList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappryearendmList)) {
            for(AppaiappryearendmDto appaiappryearendmDto : appaiappryearendmList) {
                switch(appaiappryearendmDto.getRowStatus()) {
                    case DataConst.INSERT :
                        // result += appaiappryearendmMapper.insertAppryearendm(appaiappryearendmDto);
                        break;

                    case DataConst.UPDATE :
                        if (appaiappryearendmMapper.checkCapaApprmInsert(appaiappryearendmDto) == 0) {
                            result += appaiappryearendmMapper.insertAppryearendm(appaiappryearendmDto);
                        }else {
                            result += appaiappryearendmMapper.updateAppryearendm(appaiappryearendmDto);
                        }
                        break;

                    case DataConst.DELETE :
                        result += appaiappryearendmMapper.deleteAppryearendm(appaiappryearendmDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappryearendmDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 다면평가(담당자) 전송 및 전송취소
     * @param appaiappryearendmDto, status
     * @return int
     */
    public int updateSubmitStatus(AppaiappryearendmDto appaiappryearendmDto, String status) {
        int result = 0;
        if ("SUB".equals(status)){
            appaiappryearendmDto.setExecInd("Y");
            result += appaiappryearendmMapper.updateSubmitStatus(appaiappryearendmDto);
        }else if ("CANCEL".equals(status)) {
            appaiappryearendmDto.setExecInd("N");
            result += appaiappryearendmMapper.updateSubmitStatus(appaiappryearendmDto);

        }
        return result;
    }

}
