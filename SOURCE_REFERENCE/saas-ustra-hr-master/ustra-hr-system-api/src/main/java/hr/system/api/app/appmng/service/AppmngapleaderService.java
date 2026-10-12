package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapleaderDto;
import hr.system.api.app.appmng.mapper.AppmngapleaderMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AppmngapleaderService {

    private final AppmngapleaderMapper appmngapleaderMapper;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public List<CamelCaseMap> findAppleader(AppmngapleaderDto appmngapleaderDto) {
        return appmngapleaderMapper.findAppleader(appmngapleaderDto);
    }

    public int findAppleaderIns(AppmngapleaderDto appmngapleaderDto) {
        int result = 0;

        result = appmngapleaderMapper.findAppleaderIns(appmngapleaderDto);

        return result;
    }

    public int insertupdatedeleteAppleader(AppmngapleaderDto appmngapleaderDto, List<AppmngapleaderDto> appmngapleaderDtoList )  {
        int result = 0;
        int cnt = 0;

        if(CollectionUtils.isNotEmpty(appmngapleaderDtoList)) {
            for(AppmngapleaderDto innerappmngapleaderDto : appmngapleaderDtoList) {
                log.info("appmngapexcpterDtoList.getRowStatus()===>"+innerappmngapleaderDto.getRowStatus());

                switch(innerappmngapleaderDto.getRowStatus()) {
                    case DataConst.INSERT :
                    case DataConst.UPDATE :
                        result += appmngapleaderMapper.saveAppleader(innerappmngapleaderDto);
                        break;
                    case DataConst.DELETE : //삭제
                        result += appmngapleaderMapper.deleteAppleader(innerappmngapleaderDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerappmngapleaderDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int updatAppleader(AppmngapleaderDto appmngapleaderDto, List<AppmngapleaderDto> appmngapleaderDtoList) {

        int result = 0;
        if (CollectionUtils.isNotEmpty(appmngapleaderDtoList)) {
            for (AppmngapleaderDto innerappmngapleaderDto : appmngapleaderDtoList) {
                result += appmngapleaderMapper.saveAppleader(innerappmngapleaderDto);
            }
        }
        return result;
    }

}


