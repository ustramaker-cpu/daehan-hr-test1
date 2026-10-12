package hr.system.api.app.appai.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import hr.system.api.app.appai.mapper.AppaiappprogressgaMapper;
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
public class AppaiappprogressgaService {

    private final AppaiappprogressgaMapper appaiappprogressgaMapper;

    /**
     * 목표설정 평가진행현황관리(평가자) 조회
     * @param appaiappprogressgDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappprogressgaList(AppaiappprogressgDto appaiappprogressgDto){
        return appaiappprogressgaMapper.findAppaiappprogressgaList(appaiappprogressgDto);
    }

    /**
     * 목표설정 평가진행현황관리(평가자) 평가자 업데이트
     * @param appaiappprogressgDto, status
     * @return int
     */
    public int updateStatus(AppaiappprogressgDto appaiappprogressgDto, String status) {
        int result = 0;
        if ("READING".equals(status)) {
            appaiappprogressgDto.setAppraiserEmplNo(appaiappprogressgDto.getAppraiserEmplNo1st());
            appaiappprogressgDto.setAppraiserSeqNo("1");
            appaiappprogressgDto.setExecInd("R");
            result += appaiappprogressgaMapper.update1stStatus(appaiappprogressgDto);
        }
        return result;
    }

    /**
     * 목표설정 평가진행현황관리(평가자) 상태를 수정한다. (목표승인)
     * @param appaiappprogressgList
     * @return int
     */
    public int updateStatusInd(List<AppaiappprogressgDto> appaiappprogressgList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappprogressgList)) {
            for (AppaiappprogressgDto appaiappprogressgDto : appaiappprogressgList) {
                appaiappprogressgDto.setAppraiserEmplNo(appaiappprogressgDto.getAppraiserEmplNo1st());
                appaiappprogressgDto.setAppraiserSeqNo("1");
                if( "APPROVE".equals(status) ) {  // 목표승인
                    appaiappprogressgDto.setExecInd("Y");
                    result += appaiappprogressgaMapper.update1stStatus(appaiappprogressgDto);

                } else if ( "APPROVE_CANCEL".equals(status) ) {  // 목표승인 취소
                    appaiappprogressgDto.setExecInd("N");
                    result += appaiappprogressgaMapper.update1stStatus(appaiappprogressgDto);

                }
            }
        }
        return result;
    }

}
