package hr.system.api.app.appai.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;

import hr.system.api.app.appai.dto.AppaiappobjlistsDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistsMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppaiappobjlistsService {

    private final AppaiappobjlistsMapper appaiappobjlistsMapper;

    /**
     * 자기신고서 대상자조회
     * @param AppaiappobjlistsDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppaiappobjlistsList(AppaiappobjlistsDto AppaiappobjlistsDto){
        return appaiappobjlistsMapper.findAppaiappobjlistsList(AppaiappobjlistsDto);
    }

    /**
     * 자기신고서 대상자관리 저장
     * @param appaiappobjlistsList - 등록할 정보가 담긴 appaiappobjlistsList
     * @return
     */
    public int saveAppaiappobjlists(List<AppaiappobjlistsDto> appaiappobjlistsList) {
        int result = 0;
        if(CollectionUtils.isNotEmpty(appaiappobjlistsList)) {
            for(AppaiappobjlistsDto AppaiappobjlistsDto : appaiappobjlistsList) {

            	String AprsrEmplNo1 = AppaiappobjlistsDto.getAppraiserEmplNo1st(); // 1차 평가자
            	String AprsrEmplNo2 = AppaiappobjlistsDto.getAppraiserEmplNo2nd(); // 2차 평가자

                switch(AppaiappobjlistsDto.getRowStatus()) {
                    case DataConst.INSERT :
                        // 평가예외자 테이블에 있는 사람인지 확인
                        if(0 < (appaiappobjlistsMapper.checkApexcpter(AppaiappobjlistsDto)))
                            throw new ServiceException("["+AppaiappobjlistsDto.getEmplNo()+" - " +AppaiappobjlistsDto.getEmplNameHan()+"] "+ " 평가 예외자입니다.");

                        result += appaiappobjlistsMapper.insertApapprobj(AppaiappobjlistsDto);
                        // result += appaiappobjlistsMapper.insertApobjdtl(AppaiappobjlistsDto);

                        // 1, 2차 평가자 둘 다 없을 때
                        if (   StringUtils.isEmpty(AprsrEmplNo1)
                            && StringUtils.isEmpty(AprsrEmplNo2)) {

                            AppaiappobjlistsDto.setAppraiserEmplNo(AppaiappobjlistsDto.getEmplNo());
                            AppaiappobjlistsDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);
                        }

                        // 1차 평가자만 있을 때
                        else if (   StringUtils.isNotEmpty(AprsrEmplNo1)
                                 && StringUtils.isEmpty(AprsrEmplNo2)){

                            AppaiappobjlistsDto.setAppraiserEmplNo(AppaiappobjlistsDto.getEmplNo());
                            AppaiappobjlistsDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);

                            AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo1);
                            AppaiappobjlistsDto.setAppraiserSeqNo("1");
                            result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);

                        }
                        // 2차 평가자만 있을 때 ( 이 경우는 없음 )
                        else if(   StringUtils.isEmpty(AprsrEmplNo1)
                                && StringUtils.isNotEmpty(AprsrEmplNo2) ) {

                            AppaiappobjlistsDto.setAppraiserEmplNo(AppaiappobjlistsDto.getEmplNo());
                            AppaiappobjlistsDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);

                            AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo2);
                            AppaiappobjlistsDto.setAppraiserSeqNo("2");
                            result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);
                        }

                        // 둘 다 있을 때
                        else if(   StringUtils.isNotEmpty(AprsrEmplNo1)
                                && StringUtils.isNotEmpty(AprsrEmplNo2) ) {

                            AppaiappobjlistsDto.setAppraiserEmplNo(AppaiappobjlistsDto.getEmplNo());
                            AppaiappobjlistsDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);

                            AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo1);
                            AppaiappobjlistsDto.setAppraiserSeqNo("1");
                            result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);

                            AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo2);
                            AppaiappobjlistsDto.setAppraiserSeqNo("2");
                            result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);
                        }
                        break;

                    case DataConst.UPDATE :
                        result += appaiappobjlistsMapper.updateApapprobj(AppaiappobjlistsDto);
                        // result += appaiappobjlistsMapper.updateApobjdtl(AppaiappobjlistsDto);

                        AppaiappobjlistsDto.setAppraiserEmplNo(AppaiappobjlistsDto.getEmplNo());
                        AppaiappobjlistsDto.setAppraiserSeqNo("0");
                        appaiappobjlistsMapper.updateApapprser(AppaiappobjlistsDto); // 평가조직을 수정 했을 때 0차엔 업데이트가 안되어서 추가

                        // 1, 2차 평가자 둘 다 없을 때
                        if (   StringUtils.isEmpty(AprsrEmplNo1)
                            && StringUtils.isEmpty(AprsrEmplNo2) ) {
                            break;
                        }
                        // 1차 평가자만 있을 때
                        if (    StringUtils.isNotEmpty(AprsrEmplNo1)
                             && StringUtils.isEmpty(AprsrEmplNo2) ){

                            AppaiappobjlistsDto.setAppraiserSeqNo(AppaiappobjlistsDto.getSeqOne());
                            if ( appaiappobjlistsMapper.checkApapprser(AppaiappobjlistsDto) == 1 ) {

                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo1);
                                result += appaiappobjlistsMapper.updateApapprser(AppaiappobjlistsDto);

                            }else if ( appaiappobjlistsMapper.checkApapprser(AppaiappobjlistsDto) == 0 ) {

                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo1);
                                AppaiappobjlistsDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);
                            }

                        }

                        // 2차 평가자만 있을 때 ( 이 경우는 없음 )
                        else if(   StringUtils.isEmpty(AprsrEmplNo1)
                                && StringUtils.isNotEmpty(AprsrEmplNo2)) {

                            AppaiappobjlistsDto.setAppraiserSeqNo(AppaiappobjlistsDto.getSeqTwo());
                            if ( appaiappobjlistsMapper.checkApapprser(AppaiappobjlistsDto) == 1 ) {

                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo2);
                                result += appaiappobjlistsMapper.updateApapprser(AppaiappobjlistsDto);

                            }else if ( appaiappobjlistsMapper.checkApapprser(AppaiappobjlistsDto) == 0 ) {

                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo2);
                                AppaiappobjlistsDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);
                            }
                        }

                        // 둘 다 있을 때
                        else if(   StringUtils.isNotEmpty(AprsrEmplNo1)
                                && StringUtils.isNotEmpty(AprsrEmplNo2)) {

                            AppaiappobjlistsDto.setAppraiserSeqNo(AppaiappobjlistsDto.getSeqOne());
                            int seqOne = appaiappobjlistsMapper.checkApapprser(AppaiappobjlistsDto);
                            AppaiappobjlistsDto.setAppraiserSeqNo(AppaiappobjlistsDto.getSeqTwo());
                            int seqTwo = appaiappobjlistsMapper.checkApapprser(AppaiappobjlistsDto);

                            if ( seqOne == 0 && seqTwo == 0 ) {

                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo1);
                                AppaiappobjlistsDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);

                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo2);
                                AppaiappobjlistsDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);

                            }else if ( seqOne == 1 && seqTwo == 0 ) {
                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo1);
                                AppaiappobjlistsDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistsMapper.updateApapprser(AppaiappobjlistsDto);

                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo2);
                                AppaiappobjlistsDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);

                            } else if ( seqOne == 0 && seqTwo == 1 ) {
                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo1);
                                AppaiappobjlistsDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistsMapper.insertApapprser(AppaiappobjlistsDto);

                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo2);
                                AppaiappobjlistsDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistsMapper.updateApapprser(AppaiappobjlistsDto);

                            } else if ( seqOne == 1 && seqTwo == 1 ) {
                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo1);
                                AppaiappobjlistsDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistsMapper.updateApapprser(AppaiappobjlistsDto);

                                AppaiappobjlistsDto.setAppraiserEmplNo(AprsrEmplNo2);
                                AppaiappobjlistsDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistsMapper.updateApapprser(AppaiappobjlistsDto);

                            }

                        }
                        break;

                    case DataConst.DELETE :
                        // 확정여부 확인 후 삭제해야함
                        result += appaiappobjlistsMapper.deleteApapprobj(AppaiappobjlistsDto);
                        // result += appaiappobjlistsMapper.deleteApobjdtl(AppaiappobjlistsDto);
                        result += appaiappobjlistsMapper.deleteApapprser(AppaiappobjlistsDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", AppaiappobjlistsDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 자기신고서 대상자관리의 상태를 수정한다. (대상자 확정 여부, 담당확인 여부)
     * @param appaiappobjlistsList
     * @return int
     */
    public int updateConfirm(List<AppaiappobjlistsDto> appaiappobjlistsList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistsList)) {
            for (AppaiappobjlistsDto AppaiappobjlistsDto : appaiappobjlistsList) {
                if( "CONFM".equals(status) ) {  // 대상자 확정
                    AppaiappobjlistsDto.setConfmInd("Y");
                    result += appaiappobjlistsMapper.updateApapprobjConfirm(AppaiappobjlistsDto);

                } else if ( "CONFM_CANCEL".equals(status) ) {  // 대상자 확정 취소
                    AppaiappobjlistsDto.setConfmInd("N");
                    result += appaiappobjlistsMapper.updateApapprobjConfirm(AppaiappobjlistsDto);

                } else if ( "CONFM_SEND".equals(status) ) {  // 담당 확인
                    AppaiappobjlistsDto.setConfmIndSend("Y");
                    result += appaiappobjlistsMapper.updateApapprobjConfirmSend(AppaiappobjlistsDto);

                } else if ( "CONFM_SEND_CANCEL".equals(status) ) {  // 담당 확인 취소
                    AppaiappobjlistsDto.setConfmIndSend("N");
                    result += appaiappobjlistsMapper.updateApapprobjConfirmSend(AppaiappobjlistsDto);

                }
            }
        }
        return result;
    }

}
