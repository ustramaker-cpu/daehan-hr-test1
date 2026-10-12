package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngappgresDto;
import hr.system.api.app.appmng.mapper.AppmngappgresMapper;
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
public class AppmngappgresService {

    private final AppmngappgresMapper appmngappgresMapper;


    public List<CamelCaseMap> findAppmngappgresList(AppmngappgresDto appmngappgresDto) {
        return appmngappgresMapper.findAppmngappgresList(appmngappgresDto);
    }

    public int insertupdatedeleteAppgres(AppmngappgresDto appmngappgresDto,  List<AppmngappgresDto> appmngappgresDtoList )  {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(appmngappgresDtoList)) {
            for(AppmngappgresDto innerappmngappgresDto : appmngappgresDtoList) {
                log.info("appmngappgresDtoList.getRowStatus()===>"+innerappmngappgresDto.getRowStatus());
                switch(innerappmngappgresDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = appmngappgresMapper.checkAppgresList(innerappmngappgresDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 등록된 요소별 비중관리가 있습니다.");
                        }
                        result += appmngappgresMapper.insertAppgres(innerappmngappgresDto);
                        break;
                    case DataConst.UPDATE :
                        result += appmngappgresMapper.updateAppgres(innerappmngappgresDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += appmngappgresMapper.deleteAppgres(innerappmngappgresDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerappmngappgresDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int copyAppgres(AppmngappgresDto appmngappgresDto) {
        int result = appmngappgresMapper.deleteYearAppgres(appmngappgresDto);
        result += appmngappgresMapper.insertselectAppgres(appmngappgresDto);

        return result;
    }

    public int saveappmngappgresupload(AppmngappgresDto appmngappgresDto , List<AppmngappgresDto> appmngappgresDtoList) {
        int result = 0;
        int cnt = 0;
        log.info("appmngappgresDtoList====>"+appmngappgresDtoList);
        if (CollectionUtils.isNotEmpty(appmngappgresDtoList)) {
            for (AppmngappgresDto innerappmngappgresDto : appmngappgresDtoList) {
                log.info("innerappmngappgresDto.getRowStatus()====>"+innerappmngappgresDto.getRowStatus());
                switch(innerappmngappgresDto.getRowStatus()) {
                    case DataConst.INSERT :
                        result += appmngappgresMapper.insertAppgres(innerappmngappgresDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerappmngappgresDto.getRowStatus());
                        //throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }


}
