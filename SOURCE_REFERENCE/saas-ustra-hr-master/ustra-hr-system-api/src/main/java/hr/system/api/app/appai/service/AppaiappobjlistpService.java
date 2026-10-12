package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistpDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistpMapper;
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
public class AppaiappobjlistpService {

    private final AppaiappobjlistpMapper appaiappobjlistpMapper;

    public List<CamelCaseMap> findAppaiappobjlistpList(AppaiappobjlistpDto appaiappobjlistpDto){
        return appaiappobjlistpMapper.findAppaiappobjlistpList(appaiappobjlistpDto);
    }

    /**
     * 목표설정 대상자관리 저장
     * @param appaiappobjlistpList - 등록할 정보가 담긴 appaiappobjlistpList
     * @return
     */
    public int saveappaiappobjlistp(List<AppaiappobjlistpDto> appaiappobjlistpList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistpList)) {
            for(AppaiappobjlistpDto appaiappobjlistpDto : appaiappobjlistpList) {
                switch(appaiappobjlistpDto.getRowStatus()) {
                    case DataConst.INSERT :
                        // 평가예외자 테이블에 있는 사람인지 확인
                        if(0 < (appaiappobjlistpMapper.checkApexcpter(appaiappobjlistpDto)))
                            throw new ServiceException("["+appaiappobjlistpDto.getEmplNo()+" - " +appaiappobjlistpDto.getEmplNameHan()+"] "+ " 평가 예외자입니다.");

                        result += appaiappobjlistpMapper.insertApapprobj(appaiappobjlistpDto);
                        // result += appaiappobjlistpMapper.insertApobjdtl(appaiappobjlistpDto);

                        // 1, 2차 평가자 둘 다 없을 때
                        if ( (appaiappobjlistpDto.getAppraiserEmplNo1st() == null || appaiappobjlistpDto.getAppraiserEmplNo1st().equals(""))
                           && (appaiappobjlistpDto.getAppraiserEmplNo2nd() == null || appaiappobjlistpDto.getAppraiserEmplNo2nd().equals("") ) ) {

                            appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getEmplNo());
                            appaiappobjlistpDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);
                        }

                        // 1차 평가자만 있을 때
                        else if (  (appaiappobjlistpDto.getAppraiserEmplNo1st() != null || !appaiappobjlistpDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistpDto.getAppraiserEmplNo2nd() == null || appaiappobjlistpDto.getAppraiserEmplNo2nd().equals("") )){

                            appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getEmplNo());
                            appaiappobjlistpDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);

                            appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo1st());
                            appaiappobjlistpDto.setAppraiserSeqNo("1");
                            result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);

                        }
                        // 2차 평가자만 있을 때 ( 이 경우는 없음 )
                        else if( (appaiappobjlistpDto.getAppraiserEmplNo1st() == null || appaiappobjlistpDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistpDto.getAppraiserEmplNo2nd() != null || !appaiappobjlistpDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getEmplNo());
                            appaiappobjlistpDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);

                            appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo2nd());
                            appaiappobjlistpDto.setAppraiserSeqNo("2");
                            result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);
                        }

                        // 둘 다 있을 때
                        else if( (appaiappobjlistpDto.getAppraiserEmplNo1st() != null || !appaiappobjlistpDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistpDto.getAppraiserEmplNo2nd() != null || !appaiappobjlistpDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getEmplNo());
                            appaiappobjlistpDto.setAppraiserSeqNo("0");
                            result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);

                            appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo1st());
                            appaiappobjlistpDto.setAppraiserSeqNo("1");
                            result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);

                            appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo2nd());
                            appaiappobjlistpDto.setAppraiserSeqNo("2");
                            result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);
                        }
                        break;

                    case DataConst.UPDATE :
                        result += appaiappobjlistpMapper.updateApapprobj(appaiappobjlistpDto);
                        // result += appaiappobjlistpMapper.updateApobjdtl(appaiappobjlistpDto);

                        appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getEmplNo());
                        appaiappobjlistpDto.setAppraiserSeqNo("0");
                        appaiappobjlistpMapper.updateApapprser(appaiappobjlistpDto); // 평가조직을 수정 했을 때 0차엔 업데이트가 안되어서 추가

                        // 1, 2차 평가자 둘 다 없을 때
                        if ( (appaiappobjlistpDto.getAppraiserEmplNo1st() == null || appaiappobjlistpDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistpDto.getAppraiserEmplNo2nd() == null || appaiappobjlistpDto.getAppraiserEmplNo2nd().equals("") ) ) {
                            break;
                        }
                        // 1차 평가자만 있을 때
                        if (  (appaiappobjlistpDto.getAppraiserEmplNo1st() != null || !appaiappobjlistpDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistpDto.getAppraiserEmplNo2nd() == null || appaiappobjlistpDto.getAppraiserEmplNo2nd().equals("") )){

                            appaiappobjlistpDto.setAppraiserSeqNo(appaiappobjlistpDto.getSeqOne());
                            if ( appaiappobjlistpMapper.checkApapprser(appaiappobjlistpDto) == 1 ) {

                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo1st());
                                result += appaiappobjlistpMapper.updateApapprser(appaiappobjlistpDto);

                            }else if ( appaiappobjlistpMapper.checkApapprser(appaiappobjlistpDto) == 0 ) {

                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo1st());
                                appaiappobjlistpDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);
                            }

                        }

                        // 2차 평가자만 있을 때 ( 이 경우는 없음 )
                        else if( (appaiappobjlistpDto.getAppraiserEmplNo1st() == null || appaiappobjlistpDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistpDto.getAppraiserEmplNo2nd() != null || !appaiappobjlistpDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiappobjlistpDto.setAppraiserSeqNo(appaiappobjlistpDto.getSeqTwo());
                            if ( appaiappobjlistpMapper.checkApapprser(appaiappobjlistpDto) == 1 ) {

                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo2nd());
                                result += appaiappobjlistpMapper.updateApapprser(appaiappobjlistpDto);

                            }else if ( appaiappobjlistpMapper.checkApapprser(appaiappobjlistpDto) == 0 ) {

                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo2nd());
                                appaiappobjlistpDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);
                            }
                        }

                        // 둘 다 있을 때
                        else if( (appaiappobjlistpDto.getAppraiserEmplNo1st() != null || !appaiappobjlistpDto.getAppraiserEmplNo1st().equals(""))
                                && (appaiappobjlistpDto.getAppraiserEmplNo2nd() != null || !appaiappobjlistpDto.getAppraiserEmplNo2nd().equals("")) ) {

                            appaiappobjlistpDto.setAppraiserSeqNo(appaiappobjlistpDto.getSeqOne());
                            int seqOne = appaiappobjlistpMapper.checkApapprser(appaiappobjlistpDto);
                            appaiappobjlistpDto.setAppraiserSeqNo(appaiappobjlistpDto.getSeqTwo());
                            int seqTwo = appaiappobjlistpMapper.checkApapprser(appaiappobjlistpDto);

                            if ( seqOne == 0 && seqTwo == 0 ) {

                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo1st());
                                appaiappobjlistpDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);

                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo2nd());
                                appaiappobjlistpDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);

                            }else if ( seqOne == 1 && seqTwo == 0 ) {
                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo1st());
                                appaiappobjlistpDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistpMapper.updateApapprser(appaiappobjlistpDto);

                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo2nd());
                                appaiappobjlistpDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);

                            } else if ( seqOne == 0 && seqTwo == 1 ) {
                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo1st());
                                appaiappobjlistpDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistpMapper.insertApapprser(appaiappobjlistpDto);

                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo2nd());
                                appaiappobjlistpDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistpMapper.updateApapprser(appaiappobjlistpDto);

                            } else if ( seqOne == 1 && seqTwo == 1 ) {
                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo1st());
                                appaiappobjlistpDto.setAppraiserSeqNo("1");
                                result += appaiappobjlistpMapper.updateApapprser(appaiappobjlistpDto);

                                appaiappobjlistpDto.setAppraiserEmplNo(appaiappobjlistpDto.getAppraiserEmplNo2nd());
                                appaiappobjlistpDto.setAppraiserSeqNo("2");
                                result += appaiappobjlistpMapper.updateApapprser(appaiappobjlistpDto);

                            }

                        }
                        break;

                    case DataConst.DELETE :
                        // 확정여부 확인 후 삭제해야함
                        result += appaiappobjlistpMapper.deleteApapprobj(appaiappobjlistpDto);
                        // result += appaiappobjlistpMapper.deleteApobjdtl(appaiappobjlistpDto);
                        result += appaiappobjlistpMapper.deleteApapprser(appaiappobjlistpDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappobjlistpDto.getRowStatus());
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
    public int updateConfirm(List<AppaiappobjlistpDto> appaiappobjlistpList, String status) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistpList)) {
            for (AppaiappobjlistpDto appaiappobjlistpDto : appaiappobjlistpList) {
                if( "CONFM".equals(status) ) {  // 대상자 확정
                    appaiappobjlistpDto.setConfmInd("Y");
                    result += appaiappobjlistpMapper.updateApapprobjConfirm(appaiappobjlistpDto);

                } else if ( "CONFM_CANCEL".equals(status) ) {  // 대상자 확정 취소
                    appaiappobjlistpDto.setConfmInd("N");
                    result += appaiappobjlistpMapper.updateApapprobjConfirm(appaiappobjlistpDto);

                } else if ( "CONFM_SEND".equals(status) ) {  // 담당 확인
                    appaiappobjlistpDto.setConfmIndSend("Y");
                    result += appaiappobjlistpMapper.updateApapprobjConfirmSend(appaiappobjlistpDto);

                } else if ( "CONFM_SEND_CANCEL".equals(status) ) {  // 담당 확인 취소
                    appaiappobjlistpDto.setConfmIndSend("N");
                    result += appaiappobjlistpMapper.updateApapprobjConfirmSend(appaiappobjlistpDto);

                }
            }
        }
        return result;
    }

}
