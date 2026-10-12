package hr.system.api.edu.educdp.service;

import com.gsitm.framework.core.exception.ServiceException;
import hr.system.api.edu.educdp.dto.EducdpcpjobmngDto;
import hr.system.api.edu.educdp.mapper.EducdpcpjobmngMapper;
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
public class EducdpcpjobmnguploadService {

    @Autowired
    private final EducdpcpjobmngMapper educdpcpjobmngMapper;

    public int saveeducdpcpjobmngupload(List<EducdpcpjobmngDto> educdpcpjobmngDtoList) {
        int result = 0;
        int cnt = 0;
        log.info("educdpcpjobmngDtoList====>"+educdpcpjobmngDtoList);
        if (CollectionUtils.isNotEmpty(educdpcpjobmngDtoList)) {
            for (EducdpcpjobmngDto educdpcpjobmngDto : educdpcpjobmngDtoList) {
                log.info("educdpcpjobmngDto.getRowStatus()====>"+educdpcpjobmngDto.getRowStatus());
                switch(educdpcpjobmngDto.getRowStatus()) {
                    case DataConst.INSERT :
                        cnt = educdpcpjobmngMapper.checkCpjobmasterList(educdpcpjobmngDto );
                        if( cnt > 0) {
                            throw new ServiceException("이미 등록된 직무가 있습니다.");
                        }
                        result += educdpcpjobmngMapper.insertCpjobmaster(educdpcpjobmngDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", educdpcpjobmngDto.getRowStatus());
                        //throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

}