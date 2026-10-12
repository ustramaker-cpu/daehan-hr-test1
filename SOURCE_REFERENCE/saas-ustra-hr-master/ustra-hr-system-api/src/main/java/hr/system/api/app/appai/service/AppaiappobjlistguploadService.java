package hr.system.api.app.appai.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappobjlistgDto;
import hr.system.api.app.appai.mapper.AppaiappobjlistgMapper;
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
public class AppaiappobjlistguploadService {

    private final AppaiappobjlistgMapper appaiappobjlistgMapper;

    /**
     * 목표설정 대상자관리(엑셀업로드) 저장
     * @param appaiappobjlistgList - 등록할 정보가 담긴 appaiappobjlistgList
     * @return
     */
    public int saveAppaiappobjlistgUpload(List<AppaiappobjlistgDto> appaiappobjlistgList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appaiappobjlistgList)) {
            for(AppaiappobjlistgDto appaiappobjlistgDto : appaiappobjlistgList) {
                switch(appaiappobjlistgDto.getRowStatus()) {
                    case DataConst.INSERT :
                        // 평가예외자 테이블에 있는 사람인지 확인
                        if(0 < (appaiappobjlistgMapper.checkApexcpter(appaiappobjlistgDto)))
                            throw new ServiceException("["+appaiappobjlistgDto.getEmplNo()+" - " +appaiappobjlistgDto.getEmplNameHan()+"] "+ " 평가 예외자입니다.");

                        appaiappobjlistgDto.setApprGroupCode("999");
                        appaiappobjlistgDto.setAppraiserSeqNo("0");
                        appaiappobjlistgDto.setAppraiserEmplNo(appaiappobjlistgDto.getEmplNo());


                        result += appaiappobjlistgMapper.insertApapprobj(appaiappobjlistgDto);
                        // appaiappobjlistgMapper.insertApobjdtl(appaiappobjlistgDto);
                        appaiappobjlistgMapper.insertApapprser(appaiappobjlistgDto);

                        break;

                    default :
                        log.debug("RowStatus : {}", appaiappobjlistgDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

}
