package hr.system.api.edu.educdp.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobcapaDto;
import hr.system.api.edu.educdp.dto.EducdpcpjobeduDto;
import hr.system.api.edu.educdp.dto.EducdpcpjoblisncDto;
import hr.system.api.edu.educdp.dto.EducdpcpjobunitDto;
import hr.system.api.edu.educdp.mapper.EducdpcpmapmngMapper;
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
public class EducdpcpmapmngService {

    private final EducdpcpmapmngMapper educdpcpmapmngMapper;

    public List<CamelCaseMap> findEducdpcpjobunitList(EducdpcpjobunitDto educdpcpjobunitDto){
        return educdpcpmapmngMapper.findEducdpcpjobunitList(educdpcpjobunitDto);
    }

    public List<CamelCaseMap> findEducdpcpjobeduList(EducdpcpjobeduDto educdpcpjobeduDto) {
        return educdpcpmapmngMapper.findEducdpcpjobeduList(educdpcpjobeduDto);
    }

    public List<CamelCaseMap> findEducdpcpjoblisncList(EducdpcpjoblisncDto educdpcpjoblisncDto) {
        return educdpcpmapmngMapper.findEducdpcpjoblisncList(educdpcpjoblisncDto);
    }

    public List<CamelCaseMap> findJobunitcodeHistory(EducdpcpjobunitDto educdpcpjobunitDto){
        return educdpcpmapmngMapper.findJobunitcodeHistory(educdpcpjobunitDto);
    }

    public List<CamelCaseMap> findEducdpcpjobcapaList(EducdpcpjobcapaDto educdpcpjobcapaDto){
        return educdpcpmapmngMapper.findEducdpcpjobcapaList(educdpcpjobcapaDto);
    }

    public int insertupdatedeleteCpjobunit(List<EducdpcpjobunitDto> educdpcpjobunitDto ) {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(educdpcpjobunitDto)) {
            for(EducdpcpjobunitDto innerEducdpcpjobunitDto : educdpcpjobunitDto) {
                log.info("educdpcpjobmngDtoList.getRowStatus()===>"+innerEducdpcpjobunitDto.getRowStatus());
                switch(innerEducdpcpjobunitDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        result += educdpcpmapmngMapper.insertCpjobunit(innerEducdpcpjobunitDto);
                        break;
                    case DataConst.UPDATE :
                        result += educdpcpmapmngMapper.updateCpjobunit(innerEducdpcpjobunitDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += educdpcpmapmngMapper.deleteCpjobunit(innerEducdpcpjobunitDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerEducdpcpjobunitDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int insertupdatedeleteCpjobedu(List<EducdpcpjobeduDto> educdpcpjobeduDto ) {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(educdpcpjobeduDto)) {
            for(EducdpcpjobeduDto innerEducdpcpjobeduDto : educdpcpjobeduDto) {
                log.info("educdpcpjobmngDtoList.getRowStatus()===>"+innerEducdpcpjobeduDto.getRowStatus());
                switch(innerEducdpcpjobeduDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = educdpcpmapmngMapper.checkCpjobeduList(innerEducdpcpjobeduDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 해당직무에 등록된 교육이 있습니다.");
                        }
                        result += educdpcpmapmngMapper.insertCpjobedu(innerEducdpcpjobeduDto);
                        break;
                    case DataConst.UPDATE :
                        result += educdpcpmapmngMapper.updateCpjobedu(innerEducdpcpjobeduDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += educdpcpmapmngMapper.deleteCpjobedu(innerEducdpcpjobeduDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerEducdpcpjobeduDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int insertupdatedeleteCpjoblisnc(List<EducdpcpjoblisncDto> educdpcpjoblisncDto ) {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(educdpcpjoblisncDto)) {
            for(EducdpcpjoblisncDto innerEducdpcpjoblisncDto : educdpcpjoblisncDto) {
                log.info("educdpcpjobmngDtoList.getRowStatus()===>"+innerEducdpcpjoblisncDto.getRowStatus());
                switch(innerEducdpcpjoblisncDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = educdpcpmapmngMapper.checkCpjoblisncList(innerEducdpcpjoblisncDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 해당직무에 등록된 자격증이 있습니다.");
                        }
                        result += educdpcpmapmngMapper.insertCpjoblisnc(innerEducdpcpjoblisncDto);
                        break;
                    case DataConst.UPDATE :
                        result += educdpcpmapmngMapper.updateCpjoblisnc(innerEducdpcpjoblisncDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += educdpcpmapmngMapper.deleteCpjoblisnc(innerEducdpcpjoblisncDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerEducdpcpjoblisncDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int insertupdatedeleteCpjobcapa(List<EducdpcpjobcapaDto> educdpcpjobcapaDto) {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(educdpcpjobcapaDto)) {
            for(EducdpcpjobcapaDto innerEducdpcpjobcapaDto : educdpcpjobcapaDto) {
                log.info("educdpcpjobmngDtoList.getRowStatus()===>"+innerEducdpcpjobcapaDto.getRowStatus());
                switch(innerEducdpcpjobcapaDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = educdpcpmapmngMapper.checkCpjobcapaList(innerEducdpcpjobcapaDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 해당직무에 등록된 역량 있습니다.");
                        }
                        result += educdpcpmapmngMapper.insertCpjobcapa(innerEducdpcpjobcapaDto);
                        break;
                    case DataConst.UPDATE :
                        result += educdpcpmapmngMapper.updateCpjobcapa(innerEducdpcpjobcapaDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += educdpcpmapmngMapper.deleteCpjobcapa(innerEducdpcpjobcapaDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerEducdpcpjobcapaDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

}