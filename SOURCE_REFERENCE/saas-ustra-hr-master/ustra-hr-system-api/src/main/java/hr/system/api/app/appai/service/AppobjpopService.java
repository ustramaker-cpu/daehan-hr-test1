package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistgMapper;
import hr.system.api.app.appai.mapper.AppobjpopMapper;
import hr.system.core.code.dto.CdgradecdDto;
import hr.system.core.code.mapper.CdgradecdMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AppobjpopService {

    private final CdgradecdMapper cdgradecdMapper;
    private final AppaiappobjlistgMapper appaiappobjlistgMapper;
    private final AppobjpopMapper appobjpopMapper;

    /**
     * 회사별 직급 코드 조회
     * @param cdgradecdDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findCdgradecd(CdgradecdDto cdgradecdDto){
        return cdgradecdMapper.findCdgradecd(cdgradecdDto);
    }

    public String findApapprobjstandStandpoint(AppaiappobjlistgDto appaiappobjlistgDto) {
        return appobjpopMapper.findApapprobjstandStandpoint(appaiappobjlistgDto);
    }

    /**
     * 생성기준일자 조회
     * @param cdgradecdDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAppobjBaseDate(AppaiappobjlistgDto appaiappobjlistgDto) {
        return appobjpopMapper.findAppobjBaseDate(appaiappobjlistgDto);
    }

    /**
     * 목표설정 대상자 생성
     * @param appaiappobjlistgDto
     * @return int
     */
    public int objTargetList(AppaiappobjlistgDto appaiappobjlistgDto){
        int result = 0;
        
        // 목표설정 상태이고, 확정상태가 Y가 아닌경우 일단 삭제 ( 선택한 직급 내의 직원들 것만 삭제하기 - 순서 수정 필요 )
        // appobjpopMapper.deleteApobjdtlBeforeTarget(appaiappobjlistgDto);
        appobjpopMapper.deleteApapprserBeforeTarget(appaiappobjlistgDto);
        appobjpopMapper.deleteApapprobjBeforeTarget(appaiappobjlistgDto); // 제일 마지막에 삭제

        appaiappobjlistgDto.setApprYear(appaiappobjlistgDto.getBaseYear());
        appaiappobjlistgDto.setTypeCodeAppr("C");  // 목표설정 코드 값
        appaiappobjlistgDto.setConfmInd("N");
        appaiappobjlistgDto.setSendInd("N");
        appaiappobjlistgDto.setConfmIndSend("N");
        appaiappobjlistgDto.setOpenInd("N");

        // 재직중인 사원들
        List<CamelCaseMap> targetList = appobjpopMapper.objTargetList(appaiappobjlistgDto);
        for(CamelCaseMap list : targetList) {
            appaiappobjlistgDto.setEmplNo(list.getString("emplNo"));
            appaiappobjlistgDto.setOrgCodeFactor(list.getString("orgCode"));
            appaiappobjlistgDto.setApprGroupCode("999"); // 목표설정은 999
            appaiappobjlistgDto.setAppraiserEmplNo(list.getString("emplNo")); // 목표설정은 평가자가 자기 자신
            appaiappobjlistgDto.setAppraiserSeqNo("0");  // 목표설정은 평가자 차수가 0

            int checkApexcpter = appaiappobjlistgMapper.checkApexcpter(appaiappobjlistgDto);
            int checkApprobj = appobjpopMapper.checkApprobj(appaiappobjlistgDto);
            /*
            1. checkApexcpter : 해당년도 평가 예외자 여부 확인
            2. checkApprobj : 목표설정(C) 상태에 대상자 확정 여부가 Y 인지 확인
             */
            if ( checkApexcpter == 0 && checkApprobj == 0 ) {
                result += appaiappobjlistgMapper.insertApapprobj(appaiappobjlistgDto);
                // result += appaiappobjlistgMapper.insertApobjdtl(appaiappobjlistgDto);
                result += appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);
            }
        }

        return result;
    }

    /**
     * 목표설정 대상자 생성 (프로시저)
     * @param appaiappobjlistgDto
     * @return int
     */
    public  int insertObjTargetList(AppaiappobjlistgDto appaiappobjlistgDto){
        // 확정여부 확인하기 ( 추후에 결정 )
        /*int closeYn = appobjpopMapper.chkCloseWrkleaveCnt(appaiappobjlistgDto);
        if(closeYn > 0) {
            throw new ServiceException("확정처리된 데이터입니다. 대상자 선정을 하실수 없습니다.");
        }*/
        /*int result = 0;
        result += appobjpopMapper.insertObjTargetList(appaiappobjlistgDto);
        return result;*/


        appobjpopMapper.insertObjTargetList(appaiappobjlistgDto); // 프로시저 호출

        int appRslt = appaiappobjlistgDto.getReturnCode();
        if( appRslt < 1) {
            throw new ServiceException(appaiappobjlistgDto.getReturnMsg());
        }

        return appRslt;

    }

}
