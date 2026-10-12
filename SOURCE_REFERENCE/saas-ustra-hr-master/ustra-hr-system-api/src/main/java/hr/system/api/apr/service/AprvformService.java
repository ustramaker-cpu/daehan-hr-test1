package hr.system.api.apr.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvformDto;
import hr.system.api.apr.mapper.AprvformMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AprvformService {

    private final AprvformMapper aprvformMapper;

    public List<CamelCaseMap> findAprformList(AprvformDto aprvformDto){
        return aprvformMapper.findAprvformList(aprvformDto);
    }

    public List<CamelCaseMap> findApprovalAprformList(AprvformDto aprvformDto){
        return aprvformMapper.findApprovalAprformList(aprvformDto);
    }

    public List<CamelCaseMap> findEmpAprvformList(AprvformDto aprvformDto){
        return aprvformMapper.findEmpAprvformList(aprvformDto);
    }

    public int saveAprvform(AprvformDto aprvformDto){
        int result = 0;
        List<AprvformDto> rows = aprvformDto.getAprvformDtoList();

        if(CollectionUtils.isNotEmpty(rows)) {
            for(AprvformDto row : rows) {
                switch(row.getRowStatus()) {
                    case DataConst.INSERT :
                        result += aprvformMapper.insertAprvform(row);
                        break;
                    case DataConst.UPDATE :
                        result += aprvformMapper.updateAprvform(row);
                        break;
                    case DataConst.DELETE :
                        result += aprvformMapper.deleteAprvform(row);
                        break;
                    default :
                        log.debug("RowStatus : {}", aprvformDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int updateAprvformDepthYn(AprvformDto aprvformDto){
        int result = 0;
        List<AprvformDto> rows = aprvformDto.getAprvformDtoList();

        if(CollectionUtils.isNotEmpty(rows)) {
            for(AprvformDto row : rows) {
                switch(row.getRowStatus()) {
                    case DataConst.UPDATE :
                        if ("N".equals(row.getDepthYn()) && aprvformMapper.checkExistApprover(row) <= 0) {
                            throw new ServiceException("DEPTH사용여부가 N일 경우 결재자는 필수항목 입니다.");
                        }
                        result += aprvformMapper.updateAprvformDepthYn(row);
                        break;
                    default :
                        log.debug("RowStatus : {}", aprvformDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }

        return result;
    }

}
