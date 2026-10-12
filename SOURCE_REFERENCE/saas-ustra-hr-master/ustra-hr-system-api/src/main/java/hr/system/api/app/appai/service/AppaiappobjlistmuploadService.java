package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.dto.AppaiappobjlistmDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistgMapper;
import hr.system.api.app.appai.mapper.AppaiappobjlistmMapper;
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
public class AppaiappobjlistmuploadService {

    private final AppaiappobjlistmMapper appaiappobjlistmMapper;

    /**
     * 다면평가 평가자 엑셀업로드 저장
     * @param appaiappobjlistmList - 등록할 정보가 담긴 appaiappobjlistmList
     * @return
     */
    public int saveAppaiappobjlistmUpload(List<AppaiappobjlistmDto> appaiappobjlistmList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistmList)) {
            for(AppaiappobjlistmDto appaiappobjlistmDto : appaiappobjlistmList) {
                switch(appaiappobjlistmDto.getRowStatus()) {
                    case DataConst.INSERT :

                        // 평가대상자가 등록되어있는지 확인 -- 수정( 21.10.19 ) : 평가자, 평가대상자 동시 저장
                        /*if((appaiappobjlistmMapper.checkObjExcel(appaiappobjlistmDto)) == 0)
                            throw new ServiceException("["+appaiappobjlistmDto.getEmplNo()+"] "+ " 등록된 평가대상자가 아닙니다.");*/

                        appaiappobjlistmDto.setApprGroupCode("03"); // 다면평가(obj)
                        appaiappobjlistmDto.setConfmInd("N");       // 확정여부(obj)
                        appaiappobjlistmDto.setSendInd("N");        // 전송여부(obj)
                        appaiappobjlistmDto.setConfmIndSend("N");   // 담당확인여부(obj)
                        appaiappobjlistmDto.setOpenInd("N");        // 오픈여부(obj)
                        
                        appaiappobjlistmDto.setExecInd("N");        // 평가실시여부(serm)
                        if((appaiappobjlistmMapper.checkObjExcel(appaiappobjlistmDto)) == 0) {
                            result += appaiappobjlistmMapper.insertApapprobj(appaiappobjlistmDto);
                        }
                        result += appaiappobjlistmMapper.insertApapprserm(appaiappobjlistmDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappobjlistmDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

}
