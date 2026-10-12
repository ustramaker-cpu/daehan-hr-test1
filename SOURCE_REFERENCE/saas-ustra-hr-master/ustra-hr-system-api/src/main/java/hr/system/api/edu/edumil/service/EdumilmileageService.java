package hr.system.api.edu.edumil.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumil.dto.EdumilmileageDto;
import hr.system.api.edu.edumil.mapper.EdumilmileageMapper;
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
public class EdumilmileageService {

    private final EdumilmileageMapper edumilmileageMapper;

    public List<CamelCaseMap> findEdumilmileageList(EdumilmileageDto edumilmileageDto){
        return edumilmileageMapper.findEdumilmileageList(edumilmileageDto);
    }

    public List<CamelCaseMap> findCded18_vList(EdumilmileageDto edumilmileageDto) {
        return edumilmileageMapper.findCded18_vList(edumilmileageDto);
    }

    public int insertupdatedeleteMileage(List<EdumilmileageDto> edumilmileageDtoList )  {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(edumilmileageDtoList)) {
            for(EdumilmileageDto innerEdumilmileageDto : edumilmileageDtoList) {
                log.info("innerEdumilmileageDto.getRowStatus()===>"+innerEdumilmileageDto.getRowStatus());
                //담당자의 경우는 저장상태는 안보이고 전송 및 확인만 보임..
                innerEdumilmileageDto.setAdminFlag("Y");
                switch(innerEdumilmileageDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = edumilmileageMapper.checkEdumilmileageList(innerEdumilmileageDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 등록된 마일리지가 있습니다.");
                        }
                        result += edumilmileageMapper.insertMileage(innerEdumilmileageDto);
                        break;
                    case DataConst.UPDATE :
                        //수정
                        cnt = edumilmileageMapper.checkEdumilmileageList(innerEdumilmileageDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 등록된 마일리지가 있습니다.");
                        }
                        result += edumilmileageMapper.updateMileage(innerEdumilmileageDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += edumilmileageMapper.deleteMileage(innerEdumilmileageDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerEdumilmileageDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    //마일리지 전송처리..
    public int tranferUpdateMileage(EdumilmileageDto edumilmileageDto, List<EdumilmileageDto> edumilmileageDtoList )  {
        int result = 0;
        if(CollectionUtils.isNotEmpty(edumilmileageDtoList)) {
            for (EdumilmileageDto innerEdumilmileageDto : edumilmileageDtoList) {
                innerEdumilmileageDto.setFlagKey(edumilmileageDto.getFlagKey());
                innerEdumilmileageDto.setStatusCode(edumilmileageDto.getFlagValue());
                result += edumilmileageMapper.tranferUpdateMileage(innerEdumilmileageDto);
            }
        }
        return result;
    }
}