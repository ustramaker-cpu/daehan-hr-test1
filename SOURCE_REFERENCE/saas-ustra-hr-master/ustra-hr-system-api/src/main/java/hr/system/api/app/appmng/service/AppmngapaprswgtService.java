package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapaprswgtDto;
import hr.system.api.app.appmng.mapper.AppmngapaprswgtMapper;
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
public class AppmngapaprswgtService {

    private final AppmngapaprswgtMapper appmngapaprswgtMapper;

    public List<CamelCaseMap> findAppmngapaprswgtList(AppmngapaprswgtDto appmngapaprswgtDto){
        return appmngapaprswgtMapper.findAppmngapaprswgtList(appmngapaprswgtDto);
    }

    /**
     * 평가자별기준관리 저장
     * @param List<AppmngapaprswgtDto> - 등록할 정보가 담긴 appmngapaprswgtList
     * @return
     */
    public int saveAppmngapaprswgt(List<AppmngapaprswgtDto> appmngapaprswgtList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(appmngapaprswgtList)) {
            for(AppmngapaprswgtDto appmngapaprswgtDto : appmngapaprswgtList) {
                switch(appmngapaprswgtDto.getRowStatus()) {
                    case DataConst.INSERT :
                        /*if(0 < (appmngapaprswgtDto.checkEmplNo(docapprovtmDto)))
                            throw new ServiceException("["+appmngapaprswgtDto.getEmplNo()+"] "+ " 중복된 데이터가 존재합니다.");*/
                        result += appmngapaprswgtMapper.insertAppmngapaprswgt(appmngapaprswgtDto);
                        break;

                    case DataConst.UPDATE :
                        result += appmngapaprswgtMapper.updateAppmngapaprswgt(appmngapaprswgtDto);
                        break;

                    case DataConst.DELETE :
                        result += appmngapaprswgtMapper.deleteAppmngapaprswgt(appmngapaprswgtDto);
                        break;

                    default :
                        log.debug("RowStatus : {}", appmngapaprswgtDto.getRowStatus());
                        //throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

}
