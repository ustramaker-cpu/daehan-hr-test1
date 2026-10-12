package hr.system.api.app.appai.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.dto.AppaiappprogressmDto;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import hr.system.api.app.appai.mapper.AppaiappprogressmMapper;
import hr.system.api.app.appai.mapper.AppaiappprogresspMapper;
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
public class AppaiappprogressmService {

    private final AppaiappprogressmMapper appaiappprogressmMapper;

    /**
     * 다면평가 평가진행현황관리(담당자) 조회
     * @param appaiappprogressmDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappprogressmList(AppaiappprogressmDto appaiappprogressmDto){
        return appaiappprogressmMapper.findAppaiappprogressmList(appaiappprogressmDto);
    }

    /**
     * 다면평가 평가진행현황관리(담당자) 상태를 수정한다. (오픈 여부, 평가마감 여부, 최종마감 여부)
     * @param appaiappprogressmList
     * @return int
     */
    public int updateStatusInd(List<AppaiappprogressmDto> appaiappprogressmList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappprogressmList)) {
            for (AppaiappprogressmDto appaiappprogressmDto : appaiappprogressmList) {
                if( "OPEN".equals(status) ) {  // 오픈
                    appaiappprogressmDto.setOpenInd("Y");
                    result += appaiappprogressmMapper.updatAppprogressmOpenInd(appaiappprogressmDto);

                } else if ( "OPEN_CANCEL".equals(status) ) {  // 오픈 취소
                    appaiappprogressmDto.setOpenInd("N");
                    result += appaiappprogressmMapper.updatAppprogressmOpenInd(appaiappprogressmDto);

                } else if ( "CLOSE".equals(status) ) {  // 평가마감
                    appaiappprogressmDto.setCloseInd("Y");
                    result += appaiappprogressmMapper.updatAppprogressmCloseInd(appaiappprogressmDto);

                } else if ( "CLOSE_CANCEL".equals(status) ) {  // 평가마감 취소
                    appaiappprogressmDto.setCloseInd("N");
                    result += appaiappprogressmMapper.updatAppprogressmCloseInd(appaiappprogressmDto);

                } else if ( "CLOSE_LAST".equals(status) ) {  // 최종마감
                    appaiappprogressmDto.setCloseIndLast("Y");
                    result += appaiappprogressmMapper.updatAppprogressmCloseIndLast(appaiappprogressmDto);

                } else if ( "CLOSE_LAST_CANCEL".equals(status) ) {  // 최종마감 취소
                    appaiappprogressmDto.setCloseIndLast("N");
                    result += appaiappprogressmMapper.updatAppprogressmCloseIndLast(appaiappprogressmDto);

                }
            }
        }
        return result;
    }

}
