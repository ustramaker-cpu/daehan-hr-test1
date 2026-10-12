package hr.system.api.app.appai.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.dto.AppaiappprogressgDto;
import hr.system.api.app.appai.dto.AppaiappryearendDto;
import hr.system.api.app.appai.mapper.AppaiappprogressgMapper;
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
public class AppaiappprogressgService {

    private final AppaiappprogressgMapper appaiappprogressgMapper;

    private final AppaiappryearendService appaiappryearendService;

    /**
     * 목표설정 평가진행현황관리(담당자) 조회
     * @param appaiappprogressgDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappprogressgList(AppaiappprogressgDto appaiappprogressgDto){
        return appaiappprogressgMapper.findAppaiappprogressgList(appaiappprogressgDto);
    }

    /**
     * 목표설정 평가진행현황관리(담당자) 상태를 수정한다. (오픈 여부, 평가마감 여부, 최종마감 여부)
     * @param appaiappprogressgList
     * @return int
     */
    public int updateStatusInd(List<AppaiappprogressgDto> appaiappprogressgList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappprogressgList)) {
            for (AppaiappprogressgDto appaiappprogressgDto : appaiappprogressgList) {
                if( "OPEN".equals(status) ) {  // 오픈
                    appaiappprogressgDto.setOpenInd("Y");
                    result += appaiappprogressgMapper.updatAppprogressgOpenInd(appaiappprogressgDto);

                } else if ( "OPEN_CANCEL".equals(status) ) {  // 오픈 취소
                    appaiappprogressgDto.setOpenInd("N");
                    result += appaiappprogressgMapper.updatAppprogressgOpenInd(appaiappprogressgDto);

                } else if ( "CLOSE".equals(status) ) {  // 평가마감
                    appaiappprogressgDto.setCloseInd("Y");
                    result += appaiappprogressgMapper.updatAppprogressgCloseInd(appaiappprogressgDto);

                } else if ( "CLOSE_CANCEL".equals(status) ) {  // 평가마감 취소
                    appaiappprogressgDto.setCloseInd("N");
                    result += appaiappprogressgMapper.updatAppprogressgCloseInd(appaiappprogressgDto);

                } else if ( "CLOSE_LAST".equals(status) ) {  // 최종마감
                    appaiappprogressgDto.setCloseIndLast("Y");
                    result += appaiappprogressgMapper.updatAppprogressgCloseIndLast(appaiappprogressgDto);

                } else if ( "CLOSE_LAST_CANCEL".equals(status) ) {  // 최종마감 취소
                    appaiappprogressgDto.setCloseIndLast("N");
                    result += appaiappprogressgMapper.updatAppprogressgCloseIndLast(appaiappprogressgDto);

                } else if ( "CLOSE_EXEC_IND".equals(status) ) {  // 1차평가전송

                    AppaiappryearendDto appaiappryearendDto = new AppaiappryearendDto();
                    appaiappryearendDto.setApprYear(appaiappprogressgDto.getApprYear());
                    appaiappryearendDto.setApprSeqNo(appaiappprogressgDto.getApprSeqNo());
                    appaiappryearendDto.setUserId(appaiappprogressgDto.getEmplNo());
                    appaiappryearendDto.setTypeCodeAppr(appaiappprogressgDto.getTypeCodeAppr());
                    appaiappryearendDto.setOrgCodeFactor(appaiappprogressgDto.getOrgCodeFactor());
                    appaiappryearendDto.setAppraiserSeqNo("1");

                    appaiappprogressgDto.setAppraiserExecInd1st("Y");
                    result += appaiappryearendService.updateSubmitStatus(appaiappryearendDto, "SUB");

                } else if ( "CLOSE_EXEC_IND_CANCEL".equals(status) ) {  // 1차평가전송취소

                    AppaiappryearendDto appaiappryearendDto = new AppaiappryearendDto();
                    appaiappryearendDto.setApprYear(appaiappprogressgDto.getApprYear());
                    appaiappryearendDto.setApprSeqNo(appaiappprogressgDto.getApprSeqNo());
                    appaiappryearendDto.setUserId(appaiappprogressgDto.getEmplNo());
                    appaiappryearendDto.setTypeCodeAppr(appaiappprogressgDto.getTypeCodeAppr());
                    appaiappryearendDto.setOrgCodeFactor(appaiappprogressgDto.getOrgCodeFactor());
                    appaiappryearendDto.setAppraiserSeqNo("1");

                    result += appaiappryearendService.updateSubmitStatus(appaiappryearendDto, "CANCEL");
                }


            }
        }
        return result;
    }

}
