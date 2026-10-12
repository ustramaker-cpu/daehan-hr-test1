package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngappobjstandDto;
import hr.system.api.app.appmng.mapper.AppmngappobjstandMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AppmngappobjstandService {

    private final AppmngappobjstandMapper appmngappobjstandMapper;

    public List<CamelCaseMap> findAppmngappobjstand(AppmngappobjstandDto appmngappobjstandDto) {
        return appmngappobjstandMapper.findAppmngappobjstand(appmngappobjstandDto);
    }

    public int saveAppmngappobjstand(AppmngappobjstandDto appmngappobjstandDto) {
        int result = 0;
        List<AppmngappobjstandDto> dtoList = appmngappobjstandDto.getAppmngappobjstandList();
        if (CollectionUtils.isNotEmpty(dtoList)) {
            for (AppmngappobjstandDto dto : dtoList) {
                switch(dto.getRowStatus()) {
                    case DataConst.INSERT :
                    case DataConst.UPDATE :
                        result += appmngappobjstandMapper.saveAppmngappobjstand(dto);
                        break;
                    case DataConst.DELETE :
                        result += appmngappobjstandMapper.deleteAppmngappobjstand(dto);
                        break;
                    default :
                        log.debug("RowStatus : {}", dto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }
}


