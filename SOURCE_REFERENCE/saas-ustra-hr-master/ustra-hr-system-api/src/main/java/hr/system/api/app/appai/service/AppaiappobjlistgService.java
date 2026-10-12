package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistgMapper;
import hr.system.api.edu.edumng.dto.EduidfmngDto;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppaiappobjlistgService {

    private final AppaiappobjlistgMapper appaiappobjlistgMapper;

    public List<CamelCaseMap> findAppaiappobjlistgList(AppaiappobjlistgDto appaiappobjlistgDto){
        return appaiappobjlistgMapper.findAppaiappobjlistgList(appaiappobjlistgDto);
    }

    /**
     * 목표설정 대상자관리 저장
     * @param appaiappobjlistgList - 등록할 정보가 담긴 appaiappobjlistgList
     * @return
     */
    public int saveAppaiappobjlistg(List<AppaiappobjlistgDto> appaiappobjlistgList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistgList)) {
            for(AppaiappobjlistgDto appaiappobjlistgDto : appaiappobjlistgList) {
                switch(appaiappobjlistgDto.getRowStatus()) {
                    case DataConst.INSERT :
                        // 평가예외자 테이블에 있는 사람인지 확인
                        if(0 < (appaiappobjlistgMapper.checkApexcpter(appaiappobjlistgDto)))
                            throw new ServiceException("["+appaiappobjlistgDto.getEmplNo()+" - " +appaiappobjlistgDto.getEmplNameHan()+"] "+ " 평가 예외자입니다.");

                        appaiappobjlistgDto.setConfmIndSend("N");
                        result += appaiappobjlistgMapper.insertApapprobj(appaiappobjlistgDto);
                        // result += appaiappobjlistgMapper.insertApobjdtl(appaiappobjlistgDto);

                        // 1, 2차 평가자 둘 다 없을 때
                        if ( (appaiappobjlistgDto.getAppraiserEmplNo1st() == null || appaiappobjlistgDto.getAppraiserEmplNo1st().equals(""))
                           && (appaiappobjlistgDto.getAppraiserEmplNo2nd() == null || appaiappobjlistgDto.getAppraiserEmplNo2nd().equals("") ) ) {

                            appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getEmplNo());
                            appaiappobjlistgDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);
                        }

                        // 1차 평가자만 있을 때
                        else if (  (appaiappobjlistgDto.getAppraiserEmplNo1st() != null || !appaiappobjlistgDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistgDto.getAppraiserEmplNo2nd() == null || appaiappobjlistgDto.getAppraiserEmplNo2nd().equals("") )){

                            appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getEmplNo());
                            appaiappobjlistgDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                            appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo1st());
                            appaiappobjlistgDto.setAppraiserSeqNo("1");
                            result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                        }
                        // 2차 평가자만 있을 때 ( 이 경우는 없음 )
                        else if( (appaiappobjlistgDto.getAppraiserEmplNo1st() == null || appaiappobjlistgDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistgDto.getAppraiserEmplNo2nd() != null || !appaiappobjlistgDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getEmplNo());
                            appaiappobjlistgDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                            appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo2nd());
                            appaiappobjlistgDto.setAppraiserSeqNo("2");
                            result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);
                        }

                        // 둘 다 있을 때
                        else if( (appaiappobjlistgDto.getAppraiserEmplNo1st() != null || !appaiappobjlistgDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistgDto.getAppraiserEmplNo2nd() != null || !appaiappobjlistgDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getEmplNo());
                            appaiappobjlistgDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                            appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo1st());
                            appaiappobjlistgDto.setAppraiserSeqNo("1");
                            result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                            appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo2nd());
                            appaiappobjlistgDto.setAppraiserSeqNo("2");
                            result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);
                        }
                        break;

                    case DataConst.UPDATE :

                        appaiappobjlistgDto.setConfmIndSend("N");
                        result += appaiappobjlistgMapper.updateApapprobj(appaiappobjlistgDto);
                        // result += appaiappobjlistgMapper.updateApobjdtl(appaiappobjlistgDto);

                        appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getEmplNo());
                        appaiappobjlistgDto.setAppraiserSeqNo("0");
                        appaiappobjlistgMapper.updateApapprser(appaiappobjlistgDto); // 평가조직을 수정 했을 때 0차엔 업데이트가 안되어서 추가

                        // 1, 2차 평가자 둘 다 없을 때
                        if ( (appaiappobjlistgDto.getAppraiserEmplNo1st() == null || appaiappobjlistgDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistgDto.getAppraiserEmplNo2nd() == null || appaiappobjlistgDto.getAppraiserEmplNo2nd().equals("") ) ) {
                            break;
                        }
                        // 1차 평가자만 있을 때
                        if (  (appaiappobjlistgDto.getAppraiserEmplNo1st() != null || !appaiappobjlistgDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistgDto.getAppraiserEmplNo2nd() == null || appaiappobjlistgDto.getAppraiserEmplNo2nd().equals("") )){

                            appaiappobjlistgDto.setAppraiserSeqNo(appaiappobjlistgDto.getSeqOne());
                            if ( appaiappobjlistgMapper.checkApapprser(appaiappobjlistgDto) == 1 ) {

                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo1st());
                                result += appaiappobjlistgMapper.updateApapprser(appaiappobjlistgDto);

                            }else if ( appaiappobjlistgMapper.checkApapprser(appaiappobjlistgDto) == 0 ) {

                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo1st());
                                appaiappobjlistgDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);
                            }

                        }

                        // 2차 평가자만 있을 때 ( 이 경우는 없음 )
                        else if( (appaiappobjlistgDto.getAppraiserEmplNo1st() == null || appaiappobjlistgDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistgDto.getAppraiserEmplNo2nd() != null || !appaiappobjlistgDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiappobjlistgDto.setAppraiserSeqNo(appaiappobjlistgDto.getSeqTwo());
                            if ( appaiappobjlistgMapper.checkApapprser(appaiappobjlistgDto) == 1 ) {

                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo2nd());
                                result += appaiappobjlistgMapper.updateApapprser(appaiappobjlistgDto);

                            }else if ( appaiappobjlistgMapper.checkApapprser(appaiappobjlistgDto) == 0 ) {

                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo2nd());
                                appaiappobjlistgDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);
                            }
                        }

                        // 둘 다 있을 때
                        else if( (appaiappobjlistgDto.getAppraiserEmplNo1st() != null || !appaiappobjlistgDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistgDto.getAppraiserEmplNo2nd() != null || !appaiappobjlistgDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiappobjlistgDto.setAppraiserSeqNo(appaiappobjlistgDto.getSeqOne());
                            int seqOne = appaiappobjlistgMapper.checkApapprser(appaiappobjlistgDto);
                            appaiappobjlistgDto.setAppraiserSeqNo(appaiappobjlistgDto.getSeqTwo());
                            int seqTwo = appaiappobjlistgMapper.checkApapprser(appaiappobjlistgDto);

                            if ( seqOne == 0 && seqTwo == 0 ) {

                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo1st());
                                appaiappobjlistgDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo2nd());
                                appaiappobjlistgDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                            }else if ( seqOne == 1 && seqTwo == 0 ) {
                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo1st());
                                appaiappobjlistgDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistgMapper.updateApapprser(appaiappobjlistgDto);

                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo2nd());
                                appaiappobjlistgDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                            } else if ( seqOne == 0 && seqTwo == 1 ) {
                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo1st());
                                appaiappobjlistgDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo2nd());
                                appaiappobjlistgDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistgMapper.updateApapprser(appaiappobjlistgDto);

                            } else if ( seqOne == 1 && seqTwo == 1 ) {
                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo1st());
                                appaiappobjlistgDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistgMapper.updateApapprser(appaiappobjlistgDto);

                                appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getAppraiserEmplNo2nd());
                                appaiappobjlistgDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistgMapper.updateApapprser(appaiappobjlistgDto);

                            }

                        }
                        break;

                    case DataConst.DELETE :
                        // 확정여부 확인 후 삭제해야함
                        result += appaiappobjlistgMapper.deleteApapprobj(appaiappobjlistgDto);
                        // result += appaiappobjlistgMapper.deleteApobjdtl(appaiappobjlistgDto);
                        result += appaiappobjlistgMapper.deleteApapprser(appaiappobjlistgDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappobjlistgDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    /**
     * 목표설정 대상자관리의 상태를 수정한다. (대상자 확정 여부, 담당확인 여부)
     * @param appaiappobjlistgList
     * @return int
     */
    public int updateConfirm(List<AppaiappobjlistgDto> appaiappobjlistgList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistgList)) {
            for (AppaiappobjlistgDto appaiappobjlistgDto : appaiappobjlistgList) {
                if( "CONFM".equals(status) ) {  // 대상자 확정
                    appaiappobjlistgDto.setConfmInd("Y");
                    result += appaiappobjlistgMapper.updateApapprobjConfirm(appaiappobjlistgDto);

                } else if ( "CONFM_CANCEL".equals(status) ) {  // 대상자 확정 취소
                    appaiappobjlistgDto.setConfmInd("N");
                    result += appaiappobjlistgMapper.updateApapprobjConfirm(appaiappobjlistgDto);

                } else if ( "CONFM_SEND".equals(status) ) {  // 담당 확인
                    appaiappobjlistgDto.setConfmIndSend("Y");
                    result += appaiappobjlistgMapper.updateApapprobjConfirmSend(appaiappobjlistgDto);

                } else if ( "CONFM_SEND_CANCEL".equals(status) ) {  // 담당 확인 취소
                    appaiappobjlistgDto.setConfmIndSend("N");
                    result += appaiappobjlistgMapper.updateApapprobjConfirmSend(appaiappobjlistgDto);

                }
            }
        }
        return result;
    }

}
