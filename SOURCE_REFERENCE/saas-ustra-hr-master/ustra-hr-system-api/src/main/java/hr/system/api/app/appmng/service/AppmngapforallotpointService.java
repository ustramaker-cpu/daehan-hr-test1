package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapforallotpointDto;
import hr.system.api.app.appmng.dto.AppmngapforgpointDto;
import hr.system.api.app.appmng.mapper.AppmngapforallotpointMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppmngapforallotpointService {

    private final AppmngapforallotpointMapper appmngapforallotpointMapper;

    public List<CamelCaseMap> findappmngapforallotpointList(AppmngapforallotpointDto appmngapforallotpointDto)
    {
        return appmngapforallotpointMapper.findAppmngapforallotpointList(appmngapforallotpointDto);
    }

    public int insertupdatedeleteApforallotpoint(AppmngapforallotpointDto AppmngapforallotpointDto, List<AppmngapforallotpointDto> AppmngapforallotpointDtoList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(AppmngapforallotpointDtoList)) {
            for(AppmngapforallotpointDto innerAppmngapforallotpointDto : AppmngapforallotpointDtoList) {
                switch(innerAppmngapforallotpointDto.getRowStatus()) {
                    case DataConst.INSERT :
                    case DataConst.UPDATE :
                        result += appmngapforallotpointMapper.saveApforallotpoint(innerAppmngapforallotpointDto);
                        break;
                    case DataConst.DELETE :
                        result += appmngapforallotpointMapper.deleteapforallotpoint(innerAppmngapforallotpointDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerAppmngapforallotpointDto.getRowStatus());
                        break;
                }
            }
        }

        return result;
    }

    public int saveappmngapforallotpointupload(AppmngapforallotpointDto AppmngapforallotpointDto, List<AppmngapforallotpointDto> AppmngapforallotpointDtoList) {
        int result = 0;
        int cnt = 0;

        log.info("AppmngapforallotpointDtoList=====>" + AppmngapforallotpointDtoList);
        if(CollectionUtils.isNotEmpty(AppmngapforallotpointDtoList)) {
            for(AppmngapforallotpointDto innerAppmngapforallotpointDto : AppmngapforallotpointDtoList) {
                switch(innerAppmngapforallotpointDto.getRowStatus()) {
                    case DataConst.INSERT :
                        result += appmngapforallotpointMapper.insertapforallotpoint(innerAppmngapforallotpointDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerAppmngapforallotpointDto.getRowStatus());
                        break;
                }
            }
        }

        return result;
    }
}
