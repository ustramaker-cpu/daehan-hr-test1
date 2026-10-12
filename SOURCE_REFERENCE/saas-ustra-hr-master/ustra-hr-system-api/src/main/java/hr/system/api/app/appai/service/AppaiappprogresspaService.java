package hr.system.api.app.appai.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogresspDto;
import hr.system.api.app.appai.dto.AppaiappryearendpDto;
import hr.system.api.app.appai.mapper.AppaiappprogresspMapper;
import hr.system.api.app.appai.mapper.AppaiappprogresspaMapper;
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
public class AppaiappprogresspaService {

    private final AppaiappprogresspaMapper appaiappprogresspaMapper;

    /**
     * 중간점검 평가진행현황관리(관리자) 조회
     * @param appaiappprogresspDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappprogresspaList(AppaiappprogresspDto appaiappprogresspDto){
        return appaiappprogresspaMapper.findAppaiappprogresspaList(appaiappprogresspDto);
    }

    /**
     * 중간점검 작성 여부 조회
     * @param appaiappprogresspDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> appaiappryearendpaList(AppaiappprogresspDto appaiappprogresspDto) {
        return appaiappprogresspaMapper.appaiappryearendpaList(appaiappprogresspDto);
    }

    /**
     * 중간점검 평가진행현황관리(관리자) 평가자 업데이트
     * @param appaiappprogresspDto, status
     * @return int
     */
    public int updateStatus(AppaiappprogresspDto appaiappprogresspDto, String status) {
        int result = 0;
        if ("READING".equals(status)) {
            appaiappprogresspDto.setExecInd("R");
            result += appaiappprogresspaMapper.updateApprserStatus(appaiappprogresspDto);

        }
        return result;
    }

    /**
     * 중간점검 평가진행현황관리(관리자) 전송, 전송취소
     * @param appaiappprogresspList
     * @return int
     */
    public int updateSubmit(List<AppaiappprogresspDto> appaiappprogresspList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappprogresspList)) {
            for (AppaiappprogresspDto appaiappprogresspDto : appaiappprogresspList) {
                if( "SUBMIT".equals(status) ) {  // 전송
                    appaiappprogresspDto.setAppraiserSeqNo("1"); // 중간점검 1차 평가자
                    appaiappprogresspDto.setExecInd("Y");
                    result += appaiappprogresspaMapper.updateApprserStatus(appaiappprogresspDto);

                } else if ( "SUBMIT_CANCEL".equals(status) ) {  // 전송 취소
                    appaiappprogresspDto.setAppraiserSeqNo("1"); // 중간점검 1차 평가자
                    appaiappprogresspDto.setExecInd("N"); // 다시 READ 상태로? -> N 상태로 업데이트(21.10.21)
                    result += appaiappprogresspaMapper.updateApprserStatus(appaiappprogresspDto);

                }
            }
        }
        return result;
    }

}
