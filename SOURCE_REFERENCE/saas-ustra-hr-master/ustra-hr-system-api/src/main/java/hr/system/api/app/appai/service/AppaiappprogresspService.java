package hr.system.api.app.appai.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import hr.system.api.app.appai.mapper.AppaiappprogressgMapper;
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
public class AppaiappprogresspService {

    private final AppaiappprogresspMapper appaiappprogresspMapper;

    /**
     * 중간점검 평가진행현황관리(담당자) 조회
     * @param appaiappprogresspDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappprogresspList(AppaiappprogresspDto appaiappprogresspDto){
        return appaiappprogresspMapper.findAppaiappprogresspList(appaiappprogresspDto);
    }

    /**
     * 중간점검 평가진행현황관리(담당자) 상태를 수정한다. (오픈 여부, 평가마감 여부, 최종마감 여부)
     * @param appaiappprogresspList
     * @return int
     */
    public int updateStatusInd(List<AppaiappprogresspDto> appaiappprogresspList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappprogresspList)) {
            for (AppaiappprogresspDto appaiappprogresspDto : appaiappprogresspList) {
                if( "OPEN".equals(status) ) {  // 오픈
                    appaiappprogresspDto.setOpenInd("Y");
                    result += appaiappprogresspMapper.updatAppprogresspOpenInd(appaiappprogresspDto);

                } else if ( "OPEN_CANCEL".equals(status) ) {  // 오픈 취소
                    appaiappprogresspDto.setOpenInd("N");
                    result += appaiappprogresspMapper.updatAppprogresspOpenInd(appaiappprogresspDto);

                } else if ( "CLOSE".equals(status) ) {  // 평가마감
                    appaiappprogresspDto.setCloseInd("Y");
                    result += appaiappprogresspMapper.updatAppprogresspCloseInd(appaiappprogresspDto);

                } else if ( "CLOSE_CANCEL".equals(status) ) {  // 평가마감 취소
                    appaiappprogresspDto.setCloseInd("N");
                    result += appaiappprogresspMapper.updatAppprogresspCloseInd(appaiappprogresspDto);

                } else if ( "CLOSE_LAST".equals(status) ) {  // 최종마감
                    appaiappprogresspDto.setCloseIndLast("Y");
                    result += appaiappprogresspMapper.updatAppprogresspCloseIndLast(appaiappprogresspDto);

                } else if ( "CLOSE_LAST_CANCEL".equals(status) ) {  // 최종마감 취소
                    appaiappprogresspDto.setCloseIndLast("N");
                    result += appaiappprogresspMapper.updatAppprogresspCloseIndLast(appaiappprogresspDto);

                }
            }
        }
        return result;
    }

}
