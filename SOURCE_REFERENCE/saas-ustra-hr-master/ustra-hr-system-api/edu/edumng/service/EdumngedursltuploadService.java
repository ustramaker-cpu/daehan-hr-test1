package hr.system.api.edu.edumng.service;

import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import hr.system.api.edu.edumng.mapper.EdumngedursltMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EdumngedursltuploadService {

    @Autowired
    private final EdumngedursltMapper edumngedursltMapper;

    public int saveedumngedursltupload(List<EdumngedursltDto> edumngedursltDtoList) {
        int result = 0;
        log.info("edumngedursltDtoList====>"+edumngedursltDtoList);
        if (CollectionUtils.isNotEmpty(edumngedursltDtoList)) {
            for (EdumngedursltDto edumngedursltDto : edumngedursltDtoList) {
                log.info("edumngedursltDto.getRowStatus()====>"+edumngedursltDto.getRowStatus());
                switch(edumngedursltDto.getRowStatus()) {
                    case DataConst.INSERT :
                        result += edumngedursltMapper.insertEdedurslt(edumngedursltDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", edumngedursltDto.getRowStatus());
                        //throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

}