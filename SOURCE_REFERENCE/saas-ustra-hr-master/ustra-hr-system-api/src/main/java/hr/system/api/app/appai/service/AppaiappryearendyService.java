package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappryearendyDto;
import hr.system.api.app.appai.mapper.AppaiappryearendyMapper;
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
public class AppaiappryearendyService {

    private final AppaiappryearendyMapper appaiappryearendyMapper;

    /**
     * 연말평가(담당자) 조회
     * @param appaiappryearendyDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappryearendyList(AppaiappryearendyDto appaiappryearendyDto){

        //데이터가 없으면 화면에서 추가해줌..
        appaiappryearendyDto.setTypeCodeAppr("Y"); // 연말평가 데이터 유무 확인하기위해
        return appaiappryearendyMapper.findAppaiappryearendyList(appaiappryearendyDto);
    }

    /* 공통으로 사용하기 위해 삭제... */
    /**
     * 연말평가(개인용) 저장/수정
     * @param appaiappryearendyList - 등록할 정보가 담긴 appaiappryearendyList
     * @return
     */
    public int saveAppaiappryearendy0(List<AppaiappryearendyDto> appaiappryearendyList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappryearendyList)) {
            for(AppaiappryearendyDto appaiappryearendyDto : appaiappryearendyList) {
                appaiappryearendyDto.setTypeCodeAppr("Y"); // 평가유형코드 연말평가로
                switch(appaiappryearendyDto.getRowStatus()) {
                    case DataConst.INSERT :
                        if( appaiappryearendyMapper.checkInsertAptask(appaiappryearendyDto) == 0 ) {
                            result += appaiappryearendyMapper.insertAptasky(appaiappryearendyDto);
                        }else { // 데이터 있으면 업데이트
                            result += appaiappryearendyMapper.updateAptasky(appaiappryearendyDto);
                        }

                        break;
                    case DataConst.UPDATE :
                        // 개인과업 테이블
                        if( appaiappryearendyMapper.checkInsertAptask(appaiappryearendyDto) == 0 ) {
                            result += appaiappryearendyMapper.insertAptasky(appaiappryearendyDto);
                        }else { // 데이터 있으면 업데이트
                            result += appaiappryearendyMapper.updateAptasky(appaiappryearendyDto);
                        }

                        // 평가의견 테이블(평가자만) -- 개인은 저장안함
                        /*if (!appaiappryearendyDto.getMakerCode().equals("0")) {
                            if( appaiappryearendyMapper.checkInsertAptlkrsltp(appaiappryearendyDto) == 0 ) {
                                result += appaiappryearendyMapper.insertAptlkrsltp(appaiappryearendyDto);
                            }else { // 데이터 있으면 업데이트
                                result += appaiappryearendyMapper.updateAptlkrsltp(appaiappryearendyDto);
                            }
                        }*/
                        break;
                    case DataConst.DELETE :
                        result += appaiappryearendyMapper.deleteAptasky(appaiappryearendyDto);

                        break;
                    default :
                        log.debug("RowStatus : {}", appaiappryearendyDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
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
            result += appaiappryearendyMapper.updateSubmitStatus(appaiappryearendyDto);
        }else if ("CANCEL".equals(status)) {
            appaiappryearendyDto.setTypeCodeAppr("Y");  // 연말평가
            appaiappryearendyDto.setExecInd("N");
            result += appaiappryearendyMapper.updateSubmitStatus(appaiappryearendyDto);

        }
        return result;
    }

}
