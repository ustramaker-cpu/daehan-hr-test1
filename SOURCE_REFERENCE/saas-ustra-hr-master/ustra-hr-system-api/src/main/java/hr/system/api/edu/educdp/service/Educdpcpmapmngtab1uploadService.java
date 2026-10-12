package hr.system.api.edu.educdp.service;

import com.gsitm.framework.core.exception.ServiceException;
import hr.system.api.edu.educdp.dto.EducdpcpjobcapaDto;
import hr.system.api.edu.educdp.dto.EducdpcpjobeduDto;
import hr.system.api.edu.educdp.dto.EducdpcpjoblisncDto;
import hr.system.api.edu.educdp.dto.EducdpcpjobunitDto;
import hr.system.api.edu.educdp.mapper.EducdpcpmapmngMapper;
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
public class Educdpcpmapmngtab1uploadService {

    @Autowired
    private final EducdpcpmapmngMapper educdpcpmapmngMapper;

    public int saveeducdpcpjobunittab1upload(EducdpcpjobunitDto educdpcpjobunitDto , List<EducdpcpjobunitDto> educdpcpjobunitDtoList) {
        int result = 0;
        int cnt = 0;
        log.info("educdpcpjobunitDtoList====>"+educdpcpjobunitDtoList);
        if (CollectionUtils.isNotEmpty(educdpcpjobunitDtoList)) {
            for (EducdpcpjobunitDto innereducdpcpjobunitDto : educdpcpjobunitDtoList) {
                innereducdpcpjobunitDto.setJobCode(educdpcpjobunitDto.getJobCode());
                log.info("educdpcpjobmngDto.getRowStatus()====>"+innereducdpcpjobunitDto.getRowStatus());
                switch(innereducdpcpjobunitDto.getRowStatus()) {
                    case DataConst.INSERT :
                        result += educdpcpmapmngMapper.insertCpjobunit(innereducdpcpjobunitDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innereducdpcpjobunitDto.getRowStatus());
                        //throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int saveeducdpcpjobedutab2upload(EducdpcpjobeduDto educdpcpjobeduDto , List<EducdpcpjobeduDto> educdpcpjobeduDtoList) {
        int result = 0;
        int cnt = 0;
        log.info("educdpcpjobeduDtoList====>"+educdpcpjobeduDtoList);
        if (CollectionUtils.isNotEmpty(educdpcpjobeduDtoList)) {
            for (EducdpcpjobeduDto innereducdpcpjobeduDto : educdpcpjobeduDtoList) {
                innereducdpcpjobeduDto.setJobCode(educdpcpjobeduDto.getJobCode());
                log.info("educdpcpjobmngDto.getRowStatus()====>"+innereducdpcpjobeduDto.getRowStatus());
                switch(innereducdpcpjobeduDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = educdpcpmapmngMapper.checkCpjobeduList(innereducdpcpjobeduDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 해당직무에 등록된 교육이 있습니다.");
                        }
                        result += educdpcpmapmngMapper.insertCpjobedu(innereducdpcpjobeduDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innereducdpcpjobeduDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int saveeducdpcpjoblisnctab3upload(EducdpcpjoblisncDto educdpcpjoblisncDto , List<EducdpcpjoblisncDto> educdpcpjoblisncDtoList) {
        int result = 0;
        int cnt = 0;
        log.info("educdpcpjoblisncDtoList====>"+educdpcpjoblisncDtoList);
        if (CollectionUtils.isNotEmpty(educdpcpjoblisncDtoList)) {
            for (EducdpcpjoblisncDto innereducdpcpjoblisncDto : educdpcpjoblisncDtoList) {
                innereducdpcpjoblisncDto.setJobCode(educdpcpjoblisncDto.getJobCode());
                log.info("educdpcpjobmngDto.getRowStatus()====>"+innereducdpcpjoblisncDto.getRowStatus());
                switch(innereducdpcpjoblisncDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = educdpcpmapmngMapper.checkCpjoblisncList(innereducdpcpjoblisncDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 해당직무에 등록된 자격증이 있습니다.");
                        }
                        result += educdpcpmapmngMapper.insertCpjoblisnc(innereducdpcpjoblisncDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innereducdpcpjoblisncDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int saveeducdpcpjobcapatab4upload(EducdpcpjobcapaDto educdpcpjobcapaDto , List<EducdpcpjobcapaDto> educdpcpjobcapaDtoList) {
        int result = 0;
        int cnt = 0;
        log.info("educdpcpjobcapaDtoList====>"+educdpcpjobcapaDtoList);
        if (CollectionUtils.isNotEmpty(educdpcpjobcapaDtoList)) {
            for (EducdpcpjobcapaDto innereducdpcpjobcapaDto : educdpcpjobcapaDtoList) {
                innereducdpcpjobcapaDto.setJobCode(educdpcpjobcapaDto.getJobCode());
                log.info("educdpcpjobmngDto.getRowStatus()====>"+innereducdpcpjobcapaDto.getRowStatus());
                switch(innereducdpcpjobcapaDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = educdpcpmapmngMapper.checkCpjobcapaList(innereducdpcpjobcapaDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 해당직무에 등록된 역량이 있습니다.");
                        }
                        result += educdpcpmapmngMapper.insertCpjobcapa(innereducdpcpjobcapaDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innereducdpcpjobcapaDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }


}