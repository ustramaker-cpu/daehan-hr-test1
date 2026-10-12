package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.*;
import hr.system.api.app.appai.mapper.AppaiapappraiserMapper;
import hr.system.api.app.appai.mapper.AppaiapapprdetailMapper;
import hr.system.api.app.appai.mapper.AppaiapapprtargetMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AppaiapappraiserService {

    private final AppaiapapprdetailMapper appaiapapprdetailMapper;
    private final AppaiapapprtargetMapper appaiapapprtargetMapper;
    private final AppaiapappraiserMapper appaiapappraiserMapper;

    /**
     * 평가 대상자 조회
     * @param : appaiapapprindDto
     * @return
     */
//    public List<CamelCaseMap> findAppraisementTargetList(AppaiapapprindDto appaiapapprindDto){
//        // 평가자 seqNo count
//        Integer apappraiserSeqno = appaiapapprtargetMapper.countApappraiserSeqno(appaiapapprindDto);
//        List<Integer> seqnoList = new ArrayList<>();
//        if(apappraiserSeqno != null) {
//            for (int i = 0; i <= apappraiserSeqno; i++) {
//                seqnoList.add(i);
//            }
//        }
//        appaiapapprindDto.setSeqnoList(seqnoList);
//
//        return appaiapapprtargetMapper.findAppraisementTargetList(appaiapapprindDto);
//    }

    /**
     * 평가대상자 진행관리  - 목표설정 승인/승인취소, 중간점검 전송/전송취소
     * @param appaiapappraiserDtoList
     * @return int
     */
    public int updateApappraiserInd(List<AppaiapappraiserDto> appaiapappraiserDtoList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiapappraiserDtoList)) {
            for (AppaiapappraiserDto appaiapappraiserDto : appaiapappraiserDtoList) {
                result += appaiapappraiserMapper.updateApappraiserInd(appaiapappraiserDto);
            }
        }

        return result;
    }
}
