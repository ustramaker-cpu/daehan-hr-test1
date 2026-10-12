package hr.system.api.edu.educdp.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobmngDto;
import hr.system.api.edu.educdp.mapper.EducdpcpjobmngMapper;
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
public class EducdpcpjobmngService {

    private final EducdpcpjobmngMapper educdpcpjobmngMapper;

    public List<CamelCaseMap> findEducdpcpjobmngList(EducdpcpjobmngDto educdpcpjobmngDto){
        return educdpcpjobmngMapper.findEducdpcpjobmngList(educdpcpjobmngDto);
    }

    public int insertupdatedeleteCpjobmaster(List<EducdpcpjobmngDto> educdpcpjobmngDtoList )  {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(educdpcpjobmngDtoList)) {
            for(EducdpcpjobmngDto innerEducdpcpjobmngDto : educdpcpjobmngDtoList) {
                log.info("educdpcpjobmngDtoList.getRowStatus()===>"+innerEducdpcpjobmngDto.getRowStatus());
                switch(innerEducdpcpjobmngDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = educdpcpjobmngMapper.checkCpjobmasterList(innerEducdpcpjobmngDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 등록된 직무가 있습니다.");
                        }
                        result += educdpcpjobmngMapper.insertCpjobmaster(innerEducdpcpjobmngDto);
                        break;
                    case DataConst.UPDATE :
                        result += educdpcpjobmngMapper.updateCpjobmaster(innerEducdpcpjobmngDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += educdpcpjobmngMapper.deleteCpjobmaster(innerEducdpcpjobmngDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerEducdpcpjobmngDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public List<CamelCaseMap> findJobcodeHistory(EducdpcpjobmngDto educdpcpjobmngDto){
        return educdpcpjobmngMapper.findJobcodeHistory(educdpcpjobmngDto);
    }
}