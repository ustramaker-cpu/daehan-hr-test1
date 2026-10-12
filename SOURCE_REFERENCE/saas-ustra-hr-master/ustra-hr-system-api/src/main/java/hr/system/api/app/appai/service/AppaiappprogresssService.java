package hr.system.api.app.appai.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsitm.framework.core.type.CamelCaseMap;

import hr.system.api.app.appai.dto.AppaiappprogresssDto;
import hr.system.api.app.appai.mapper.AppaiappprogresssMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppaiappprogresssService {

    private final AppaiappprogresssMapper appaiappprogresssMapper;

    /**
     * 자기신고서 평가진행현황관리(담당자) 조회
     * @param appaiappprogresssDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappprogresssList(AppaiappprogresssDto appaiappprogresssDto){
    	return appaiappprogresssMapper.findAppaiappprogresssList(appaiappprogresssDto);
    }

    /**
     * 자기신고서 평가진행현황관리(담당자) 상태를 수정한다. (오픈 여부, 평가마감 여부, 최종마감 여부)
     * @param appaiappprogresssList
     * @return int
     */
    public int updateStatusInd(List<AppaiappprogresssDto> appaiappprogresssList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappprogresssList)) {
            for (AppaiappprogresssDto appaiappprogresssDto : appaiappprogresssList) {
                if( "OPEN".equals(status) ) {  // 오픈
                    appaiappprogresssDto.setOpenInd("Y");
                    result += appaiappprogresssMapper.updatAppprogresssOpenInd(appaiappprogresssDto);

                } else if ( "OPEN_CANCEL".equals(status) ) {  // 오픈 취소
                    appaiappprogresssDto.setOpenInd("N");
                    result += appaiappprogresssMapper.updatAppprogresssOpenInd(appaiappprogresssDto);

                } else if ( "CLOSE".equals(status) ) {  // 평가마감
                    appaiappprogresssDto.setCloseInd("Y");
                    result += appaiappprogresssMapper.updatAppprogresssCloseInd(appaiappprogresssDto);

                } else if ( "CLOSE_CANCEL".equals(status) ) {  // 평가마감 취소
                    appaiappprogresssDto.setCloseInd("N");
                    result += appaiappprogresssMapper.updatAppprogresssCloseInd(appaiappprogresssDto);

                } else if ( "CLOSE_LAST".equals(status) ) {  // 최종마감
                    appaiappprogresssDto.setCloseIndLast("Y");
                    result += appaiappprogresssMapper.updatAppprogresssCloseIndLast(appaiappprogresssDto);

                } else if ( "CLOSE_LAST_CANCEL".equals(status) ) {  // 최종마감 취소
                    appaiappprogresssDto.setCloseIndLast("N");
                    result += appaiappprogresssMapper.updatAppprogresssCloseIndLast(appaiappprogresssDto);

                }
            }
        }
        return result;
    }

}
