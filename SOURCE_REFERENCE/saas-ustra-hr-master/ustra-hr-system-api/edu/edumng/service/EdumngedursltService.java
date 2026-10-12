package hr.system.api.edu.edumng.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import hr.system.api.edu.edumng.mapper.EdumngedursltMapper;
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
public class EdumngedursltService {

    private final EdumngedursltMapper edumngedursltMapper;

    public List<CamelCaseMap> findEdumngedursltList(EdumngedursltDto edumngedursltDto){
        return edumngedursltMapper.findEdumngedursltList(edumngedursltDto);
    }

    public List<CamelCaseMap> findEdumngedursltTabList(EdumngedursltDto edumngedursltDto){
        return edumngedursltMapper.findEdumngedursltTabList(edumngedursltDto);
    }

    public int insertupdatedeleteEduRslt(EdumngedursltDto edumngedursltDto, List<EdumngedursltDto> edumngedursltDtoList )  {
        int result = 0;
        if(CollectionUtils.isNotEmpty(edumngedursltDtoList)) {
            for(EdumngedursltDto innerEdumngedursltDto : edumngedursltDtoList) {
                switch(innerEdumngedursltDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        result += edumngedursltMapper.insertEdedurslt(innerEdumngedursltDto);
                        break;
                    case DataConst.UPDATE :
                        //수정
                        result += edumngedursltMapper.updateEdedurslt(innerEdumngedursltDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += edumngedursltMapper.deleteEdedurslt(innerEdumngedursltDto);
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

    public int updateCompleteChange(EdumngedursltDto edumngedursltDto, List<EdumngedursltDto> edumngedursltDtoList )  {
        int result = 0;
        if(CollectionUtils.isNotEmpty(edumngedursltDtoList)) {
            log.info("edumngedursltDtoList==>"+edumngedursltDtoList.size());
            for(EdumngedursltDto innerEdumngedursltDto : edumngedursltDtoList) {
                //수정
                innerEdumngedursltDto.setFlagKey(edumngedursltDto.getFlagKey());
                innerEdumngedursltDto.setFlagValue(edumngedursltDto.getFlagValue());
                log.info("innerEdumngedursltDto==>"+innerEdumngedursltDto);
                result += edumngedursltMapper.updateEdedursltCompleteChange(innerEdumngedursltDto);
            }
        }
        return result;
    }

}