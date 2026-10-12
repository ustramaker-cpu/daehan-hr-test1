package hr.system.api.app.appai.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistyDto;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistyMapper;
import hr.system.api.app.appai.mapper.AppaiappprogressyMapper;
import hr.system.core.util.DeduplicationUtils;
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
public class AppaiappprogressyService {

    private final AppaiappprogressyMapper appaiappprogressyMapper;

    private final AppaiappobjlistyMapper appaiappobjlistyMapper;

    /**
     * 연말평가진행관리(담당자) 리스트
     * @param appaiappobjlistyDto
     * @return
     */
    public List<CamelCaseMap> findAppaiappprogressyList(AppaiappobjlistyDto appaiappobjlistyDto){
        // 연말평가대상자 리스트 쿼리와 동일
        return appaiappobjlistyMapper.findAppaiappobjlistyList(appaiappobjlistyDto);
    }

    /**
     * 중간점검 평가진행현황관리(담당자) 상태를 수정한다. (오픈 여부, 평가마감 여부, 최종마감 여부)
     * @param appaiappprogressyList
     * @return int
     */
    public int updateStatusInd(List<AppaiappprogressyDto> appaiappprogressyList, String status) {
        int result = 0;

        //루프가 돌기전 사용자를 하나로 묶어서 돌려야함..
        if(CollectionUtils.isNotEmpty(appaiappprogressyList)) {
            for (AppaiappprogressyDto appaiappprogressyDto : appaiappprogressyList) {
                if( "OPEN".equals(status) ) {  // 오픈
                    appaiappprogressyDto.setOpenInd("Y");
                    result += appaiappprogressyMapper.updatAppprogresspOpenInd(appaiappprogressyDto);

                } else if ( "OPEN_CANCEL".equals(status) ) {  // 오픈 취소
                    appaiappprogressyDto.setOpenInd("N");
                    result += appaiappprogressyMapper.updatAppprogresspOpenInd(appaiappprogressyDto);

                } else if ( "CLOSE".equals(status) ) {  // 평가마감
                    //평가 마감시 합산점수 가져와서
                    appaiappprogressyDto.setCloseInd("Y");
                    result += appaiappprogressyMapper.updatAppprogresspCloseInd(appaiappprogressyDto);

                } else if ( "CLOSE_CANCEL".equals(status) ) {  // 평가마감 취소
                    appaiappprogressyDto.setCloseInd("N");
                    result += appaiappprogressyMapper.updatAppprogresspCloseInd(appaiappprogressyDto);

                } else if ( "CLOSE_LAST".equals(status) ) {  // 최종마감
                    appaiappprogressyDto.setCloseIndLast("Y");
                    //평가 마감시 APLSTPNT에 insert

                    result += appaiappprogressyMapper.updatAppprogresspCloseIndLast(appaiappprogressyDto);

                    if(result > 0)
                    {
                        //평가 마감시 합산점수 가져와서
                        appaiappprogressyDto.setCloseInd("Y");
                        result = appaiappprogressyMapper.updatAppprogresspCloseInd(appaiappprogressyDto);
                    }

                } else if ( "CLOSE_LAST_CANCEL".equals(status) ) {  // 최종마감 취소
                    appaiappprogressyDto.setCloseIndLast("N");

                    result += appaiappprogressyMapper.updatAppprogresspCloseIndLast(appaiappprogressyDto);

                    if(result > 0)
                    {
                        //평가 마감시 합산점수 가져와서
                        appaiappprogressyDto.setCloseInd("N");
                        result = appaiappprogressyMapper.updatAppprogresspCloseInd(appaiappprogressyDto);
                    }
                } else if("GROUPING".equals(status) ){  // 그룹핑 업데이트
                    result += appaiappprogressyMapper.updatAppprogresspGrouping(appaiappprogressyDto);
                }
            }

            //평가의 loop와 한사람이 여러사람의 평가를 받는경우
            //emplno로 중복을 제거해서 부분평가가 있더라도 한번만 돌도록 수정..
            //최종평가 마감시에 점수 따로 계산해서 넣는로직을 만듬..
            if ( "CLOSE_LAST".equals(status) || "CLOSE_LAST_CANCEL".equals(status) ) {
                appaiappprogressyList = DeduplicationUtils.deduplication(appaiappprogressyList, AppaiappprogressyDto::getEmplNo);
                for (AppaiappprogressyDto appaiappprogressyDto : appaiappprogressyList) {
                    if ("CLOSE_LAST".equals(status)) {
                        //평가 마감시 여러 부서에서 평가받는 사람인지를 체크하여 다른로직으로 처리함..
                        int cnt = appaiappprogressyMapper.checkApprObj(appaiappprogressyDto);

                        appaiappprogressyMapper.deleteAplstpnt(appaiappprogressyDto);
                        if (cnt > 1) {
                            appaiappprogressyMapper.insertAplstpnt2(appaiappprogressyDto);
                        } else {
                            //기존에 있는지 체크하는것보다 지우고 넣는 방식으로 처리함...
                            appaiappprogressyMapper.insertAplstpnt(appaiappprogressyDto);
                        }
                    } else if ("CLOSE_LAST_CANCEL".equals(status)) {
                        //평가 마감취소시 APLSTPNT delete
                        appaiappprogressyMapper.deleteAplstpnt(appaiappprogressyDto);
                    }
                }
            }
        }
        return result;
    }

    public int closeIndYn(AppaiappprogressyDto appaiappprogressyDto) {
        int cnt = 0;

        for(AppaiappprogressyDto dto : appaiappprogressyDto.getAppaiappprogressyList()){
            dto.setCloseInd(appaiappprogressyDto.getCloseInd());
            cnt += appaiappprogressyMapper.closeIndYn(dto);
        }

        return cnt;
    }

}
