package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapfactwgtDto;
import hr.system.api.app.appmng.mapper.AppmngapfactwgtMapper;
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
public class AppmngapfactwgtService {

    private final AppmngapfactwgtMapper appmngapfactwgtMapper;

    public List<CamelCaseMap> findAppmngapfactwgtList(AppmngapfactwgtDto appmngapfactwgtDto){
        return appmngapfactwgtMapper.findAppmngapfactwgtList(appmngapfactwgtDto);
    }

    public int insertupdatedeleteApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto,  List<AppmngapfactwgtDto> appmngapfactwgtDtoList )  {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(appmngapfactwgtDtoList)) {
            for(AppmngapfactwgtDto innerappmngapfactwgtDto : appmngapfactwgtDtoList) {
                log.info("appmngapfactwgtDtoList.getRowStatus()===>"+innerappmngapfactwgtDto.getRowStatus());
                switch(innerappmngapfactwgtDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = appmngapfactwgtMapper.checkApfactwgtList(innerappmngapfactwgtDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 등록된 요소별 비중 관리가 있습니다.");
                        }
                    case DataConst.UPDATE :
                        result += appmngapfactwgtMapper.saveApfactwgt(innerappmngapfactwgtDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += appmngapfactwgtMapper.deleteApfactwgt(innerappmngapfactwgtDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerappmngapfactwgtDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int copyApfactwgt(AppmngapfactwgtDto appmngapfactwgtDto) {
        int result = appmngapfactwgtMapper.deleteYearApfactwgt(appmngapfactwgtDto);
        result += appmngapfactwgtMapper.insertselectApfactwgt(appmngapfactwgtDto);

        return result;
    }
}
