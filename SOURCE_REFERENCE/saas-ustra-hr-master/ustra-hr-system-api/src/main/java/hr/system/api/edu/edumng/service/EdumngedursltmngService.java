package hr.system.api.edu.edumng.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import hr.system.api.edu.edumng.mapper.EdumngedursltMapper;
import hr.system.api.edu.edumng.mapper.EdumngedursltmngMapper;
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
public class EdumngedursltmngService {

    private final EdumngedursltMapper edumngedursltMapper;
    private final EdumngedursltmngMapper edumngedursltmngMapper;

    public List<CamelCaseMap> findEdumngedursltmngList(EdumngedursltDto edumngedursltDto){
        return edumngedursltmngMapper.findEdumngedursltmngList(edumngedursltDto);
    }

    public List<CamelCaseMap> findEdumngedursltmngTabList(EdumngedursltDto edumngedursltDto){
        return edumngedursltmngMapper.findEdumngedursltmngTabList(edumngedursltDto);
    }

    public int insertupdatedeleteEduRsltmng(EdumngedursltDto edumngedursltDto, List<EdumngedursltDto> edumngedursltDtoList )  {
        int result = 0;
        if(CollectionUtils.isNotEmpty(edumngedursltDtoList)) {
            for(EdumngedursltDto innerEdumngedursltDto : edumngedursltDtoList) {
                log.info("innerEdumngedursltDto.getRowStatus()===>"+innerEdumngedursltDto.getRowStatus());
                switch(innerEdumngedursltDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        result += edumngedursltmngMapper.insertEdedursltmng(innerEdumngedursltDto);
                        break;
                    case DataConst.UPDATE :
                        //수정
                        result += edumngedursltmngMapper.updateEdedursltmng(innerEdumngedursltDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += edumngedursltmngMapper.deleteEdedursltmng(innerEdumngedursltDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerEdumngedursltDto.getRowStatus());
                        //throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public List<CamelCaseMap> findCpjobmasterList(EdumngedursltDto edumngedursltDto){
        return edumngedursltMapper.findCpjobmasterList(edumngedursltDto);
    }

    public List<CamelCaseMap> findCppgdescList(EdumngedursltDto edumngedursltDto){
        return edumngedursltMapper.findCppgdescList(edumngedursltDto);
    }

    public List<CamelCaseMap> findCpjobeduList(EdumngedursltDto edumngedursltDto){
        return edumngedursltMapper.findCpjobeduList(edumngedursltDto);
    }

    public int checkOrleader(EdumngedursltDto edumngedursltDto){
        return edumngedursltmngMapper.checkOrleader(edumngedursltDto);
    }

}