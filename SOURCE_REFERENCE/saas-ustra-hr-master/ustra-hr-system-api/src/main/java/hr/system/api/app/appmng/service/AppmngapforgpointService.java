package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appai.dto.AppaiappprogressyDto;
import hr.system.api.app.appmng.dto.AppmngapforgpointDto;
import hr.system.api.app.appmng.mapper.AppmngapforgpointMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppmngapforgpointService {

    private final AppmngapforgpointMapper appmngapforgpointMapper;

    public List<CamelCaseMap> appmngapfactpointList(AppmngapforgpointDto appmngapforgpointDto){
        return appmngapforgpointMapper.appmngapfactpointList(appmngapforgpointDto);
    }

    public AppmngapforgpointDto findappmngapfactTotpointList(AppmngapforgpointDto appmngapforgpointDto){
        AppmngapforgpointDto resultDto = new AppmngapforgpointDto();


            resultDto  = appmngapforgpointMapper.findappmngapfactTotpointList(appmngapforgpointDto);
            log.info(">>>>>>>"+resultDto.getTotAllotPoint());

        return resultDto;
    }

    public AppmngapforgpointDto findappmngapforgpointList(AppmngapforgpointDto appmngapforgpointDto) {
         AppmngapforgpointDto resultDto = new AppmngapforgpointDto();
            resultDto  = appmngapforgpointMapper.findAppmngapforgpointList(appmngapforgpointDto);
        log.info("resultDto is null!!!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            if(resultDto == null){
                log.info("resultDto is null!!!");
            }

        return resultDto;
    }

    public AppmngapforgpointDto findappmngapfactpointList(AppmngapforgpointDto appmngapforgpointDto) {
        AppmngapforgpointDto resultDto = new AppmngapforgpointDto();
        resultDto = appmngapforgpointMapper.findappmngapfactpointList(appmngapforgpointDto);
        //log.info("resultDto is null!!!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (resultDto == null) {
            log.info("resultDto is null!!!");
        }

        return resultDto;
    }

    public int insertupdatedeleteaApforgpoint(AppmngapforgpointDto AppmngapforgpointDto, List<AppmngapforgpointDto> AppmngapforgpointDtoList) {
        int result = 0;
        int cnt = 0;
        log.info("AppmngapforgpointDtoList====>"+AppmngapforgpointDtoList);
        if (CollectionUtils.isNotEmpty(AppmngapforgpointDtoList)) {
            for (AppmngapforgpointDto innerAppmngapforgpointDto : AppmngapforgpointDtoList) {
                log.info("innerappmngappgcapaDto.getRowStatus()====>"+innerAppmngapforgpointDto.getRowStatus());

                innerAppmngapforgpointDto.setApprSeqNo("1");

                switch(innerAppmngapforgpointDto.getRowStatus()) {
                    case DataConst.INSERT :
                        result += appmngapforgpointMapper.insertapforgpoint(innerAppmngapforgpointDto);
                        break;
                    case DataConst.UPDATE :
                        result += appmngapforgpointMapper.updateapforgpoint(innerAppmngapforgpointDto);
                        break;
                    case DataConst.DELETE :
                        result += appmngapforgpointMapper.deleteapforgpoint(innerAppmngapforgpointDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerAppmngapforgpointDto.getRowStatus());
                        //throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }
}
