package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistyDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistyMapper;
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
public class AppaiappobjlistyService {

    private final AppaiappobjlistyMapper appaiappobjlistyMapper;

    public List<CamelCaseMap> findAppaiappobjlistyList(AppaiappobjlistyDto appaiappobjlistyDto){
        return appaiappobjlistyMapper.findAppaiappobjlistyList(appaiappobjlistyDto);
    }

    /**
     * 연말평가 대상자관리 저장
     * @param appaiappobjlistpList - 등록할 정보가 담긴 appaiappobjlistpList
     * @return
     */
    public int saveappaiappobjlisty(List<AppaiappobjlistyDto> appaiappobjlistyList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistyList)) {
            for(AppaiappobjlistyDto appaiappobjlistyDto : appaiappobjlistyList) {
                switch(appaiappobjlistyDto.getRowStatus()) {
                    case DataConst.INSERT :
                        // 평가예외자 테이블에 있는 사람인지 확인
                        if(0 < (appaiappobjlistyMapper.checkApexcpter(appaiappobjlistyDto)))
                            throw new ServiceException("["+appaiappobjlistyDto.getEmplNo()+" - " +appaiappobjlistyDto.getEmplNameHan()+"] "+ " 평가 예외자입니다.");

                        // 동록된 평가대상자인지 체크
                        if(appaiappobjlistyMapper.selectApapprindEmplNoCheck(appaiappobjlistyDto) > 0){
                            throw new ServiceException("["+appaiappobjlistyDto.getEmplNo()+" - " +appaiappobjlistyDto.getEmplNameHan()+"] "+ " 등록된 대상자입니다.");
                        }

                        // 평가대상자 저장
                        result += appaiappobjlistyMapper.insertApapprobj(appaiappobjlistyDto);

                        // 본인평가
                        appaiappobjlistyDto.setAppraiserEmplNo(appaiappobjlistyDto.getEmplNo());
                        appaiappobjlistyDto.setAppraiserSeqNo("0");
                        result += appaiappobjlistyMapper.insertApapprser(appaiappobjlistyDto);

                        // 1차 평가자
                        appaiappobjlistyDto.setAppraiserEmplNo(appaiappobjlistyDto.getAppraiserEmplNo1st());
                        appaiappobjlistyDto.setAppraiserSeqNo("1");
                        result += appaiappobjlistyMapper.insertApapprser(appaiappobjlistyDto);

                        // 2차 평가자
                        appaiappobjlistyDto.setAppraiserEmplNo(appaiappobjlistyDto.getAppraiserEmplNo2nd());
                        appaiappobjlistyDto.setAppraiserSeqNo("2");
                        result += appaiappobjlistyMapper.insertApapprser(appaiappobjlistyDto);

                        // 3차 평가자
                        appaiappobjlistyDto.setAppraiserEmplNo(appaiappobjlistyDto.getAppraiserEmplNo3nd());
                        appaiappobjlistyDto.setAppraiserSeqNo("3");
                        result += appaiappobjlistyMapper.insertApapprser(appaiappobjlistyDto);

                        // 그리드 내 대상자 개별 성과 및 역량 저장
                        result += appaiappobjlistyMapper.insertApapprcapa(appaiappobjlistyDto);

                        break;

                    case DataConst.UPDATE :
                        result += appaiappobjlistyMapper.updateApapprobj(appaiappobjlistyDto);

                        // 본인평가
                        appaiappobjlistyDto.setAppraiserEmplNo(appaiappobjlistyDto.getEmplNo());
                        appaiappobjlistyDto.setAppraiserSeqNo("0");
                        appaiappobjlistyMapper.updateApapprser(appaiappobjlistyDto);

                        // 1차 평가자
                        appaiappobjlistyDto.setAppraiserEmplNo(appaiappobjlistyDto.getAppraiserEmplNo1st());
                        appaiappobjlistyDto.setAppraiserSeqNo("1");
                        appaiappobjlistyMapper.updateApapprser(appaiappobjlistyDto);

                        // 2차 평가자
                        appaiappobjlistyDto.setAppraiserEmplNo(appaiappobjlistyDto.getAppraiserEmplNo2nd());
                        appaiappobjlistyDto.setAppraiserSeqNo("2");
                        appaiappobjlistyMapper.updateApapprser(appaiappobjlistyDto);

                        // 3차 평가자
                        appaiappobjlistyDto.setAppraiserEmplNo(appaiappobjlistyDto.getAppraiserEmplNo3nd());
                        appaiappobjlistyDto.setAppraiserSeqNo("3");
                        appaiappobjlistyMapper.updateApapprser(appaiappobjlistyDto);

                        break;

                    case DataConst.DELETE :
                        result += appaiappobjlistyMapper.deleteApapprobj(appaiappobjlistyDto);
                        result += appaiappobjlistyMapper.deleteApapprser(appaiappobjlistyDto);

                        // 그리드 내 대상자 개별 성과 및 역량 삭제
                        result += appaiappobjlistyMapper.deleteApapprcapa(appaiappobjlistyDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappobjlistyDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 목표설정 대상자관리의 상태를 수정한다. (대상자 확정 여부, 담당확인 여부)
     * @param appaiappobjlistpList
     * @return int
     */
    public int updateConfirm(List<AppaiappobjlistyDto> appaiappobjlistyList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistyList)) {
            for (AppaiappobjlistyDto appaiappobjlistyDto : appaiappobjlistyList) {
                if( "CONFM".equals(status) ) {  // 대상자 확정
                    appaiappobjlistyDto.setConfmInd("Y");
//                    appaiappobjlistyDto.setConfmIndSend("Y");
                    result = appaiappobjlistyMapper.updateApapprobjConfirm(appaiappobjlistyDto);

                    //대상자 확정 시 담당확인도 진행
                    /*if(result > 0) {
                        appaiappobjlistyDto.setConfmIndSend("Y");
                        result = appaiappobjlistyMapper.updateApapprobjConfirmSend(appaiappobjlistyDto);
                    }*/

                } else if ( "CONFM_CANCEL".equals(status) ) {  // 대상자 확정 취소
                    appaiappobjlistyDto.setConfmInd("N");
                    result = appaiappobjlistyMapper.updateApapprobjConfirm(appaiappobjlistyDto);

                    //대상자 확정 취소 시 담당 확인 취소도 진행
                    /*if(result > 0) {
                        appaiappobjlistyDto.setConfmIndSend("N");
                        result = appaiappobjlistyMapper.updateApapprobjConfirmSend(appaiappobjlistyDto);
                    }*/

                }/* else if ( "CONFM_SEND".equals(status) ) {  // 담당 확인
                    appaiappobjlistyDto.setConfmIndSend("Y");
                    result += appaiappobjlistyMapper.updateApapprobjConfirmSend(appaiappobjlistyDto);

                } else if ( "CONFM_SEND_CANCEL".equals(status) ) {  // 담당 확인 취소
                    appaiappobjlistyDto.setConfmIndSend("N");
                    result += appaiappobjlistyMapper.updateApapprobjConfirmSend(appaiappobjlistyDto);

                }*/
            }
        }
        return result;
    }

}
