package hr.system.api.apr.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvconfirmDto;
import hr.system.api.apr.mapper.AprvconfirmMapper;
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
public class AprvconfirmService {

    private final AprvconfirmMapper aprvconfirmMapper;

    public List<CamelCaseMap> findAprformList(AprvconfirmDto aprvconfirmDto){
        return aprvconfirmMapper.findAprvconfirmList(aprvconfirmDto);
    }

    public int saveAprvform(AprvconfirmDto aprvconfirmDto){
        int result = 0;
        List<AprvconfirmDto> rows = aprvconfirmDto.getAprvconfirmDtoList();

        result += aprvconfirmMapper.deleteAprvconfirm(aprvconfirmDto);

        if(CollectionUtils.isNotEmpty(rows)) {
            for(AprvconfirmDto row : rows) {
                result += aprvconfirmMapper.insertAprvconfirm(row);
            }
        }
        return result;
    }

}
