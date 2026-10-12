package hr.system.api.edu.edumil.service;

import com.gsitm.framework.core.exception.ServiceException;
import hr.system.api.edu.edumil.dto.EdumilmileageDto;
import hr.system.api.edu.edumil.mapper.EdumilmileageMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EdumilmileageuploadService {

    @Autowired
    private final EdumilmileageMapper edumilmileageMapper;

    public int saveedumilmileageupload(List<EdumilmileageDto> edumilmileageDtoList) {
        int result = 0;
        int cnt = 0;
        log.info("edumngedursltDtoList====>"+edumilmileageDtoList);
        if (CollectionUtils.isNotEmpty(edumilmileageDtoList)) {
            for (EdumilmileageDto edumilmileageDto : edumilmileageDtoList) {
                log.info("edumilmileageDto.getRowStatus()====>"+edumilmileageDto.getRowStatus());
                edumilmileageDto.setAdminFlag("Y");
                switch(edumilmileageDto.getRowStatus()) {
                    case DataConst.INSERT :
                        cnt = edumilmileageMapper.checkEdumilmileageUploadList(edumilmileageDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 등록된 마일리지가 있습니다.");
                        }
                        result += edumilmileageMapper.insertMileage(edumilmileageDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", edumilmileageDto.getRowStatus());
                        //throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

    public int checkbackData(List<EdumilmileageDto> edumilmileageDtoList) {
        int result = 0;
        EdumilmileageDto rs = new EdumilmileageDto();
        if (CollectionUtils.isNotEmpty(edumilmileageDtoList)) {
            for (EdumilmileageDto edumilmileageDto : edumilmileageDtoList) {
                // 1. input data validation
                //inputDataValidation(asrecomndDto);
                // 2. 데이터 검증
                rs = edumilmileageMapper.checkbackData(edumilmileageDto);
                // 3. 데이터 결과 확인
                outputDataValidation(rs);
                result++;
            }
        }
        return result;
    }

    private void outputDataValidation(EdumilmileageDto rs) {
        if (StringUtils.isEmpty(rs.getEmplNo())) {
            throw new ServiceException("사번:" + rs.getEmplNo() + " [사번코드 오류]");
        }
        if (StringUtils.isEmpty(rs.getEvaArea())) {
            throw new ServiceException("사번:" + rs.getEmplNo() + " [평가영역 오류]");
        }
        if (StringUtils.isEmpty(rs.getEvaItem())) {
            throw new ServiceException("사번:" + rs.getEmplNo() + " [평가항목 오류]");
        }
        if (rs.getMilWeight() == null) {
            throw new ServiceException("사번:" + rs.getEmplNo() + " [비중 오류]");
        }
        if (rs.getMilPoint() == null) {
            throw new ServiceException("사번:" + rs.getEmplNo() + " [점수 오류]");
        }
        if (StringUtils.isEmpty(rs.getMilDateFrom())) {
            throw new ServiceException("사번:" + rs.getEmplNo() + " [시작일 오류]");
        }
        if (StringUtils.isEmpty(rs.getMilDateTo())) {
            throw new ServiceException("사번:" + rs.getEmplNo() + " [종료일 오류]");
        }
        if (StringUtils.isEmpty(rs.getActDetail())) {
            throw new ServiceException("사번:" + rs.getEmplNo() + " [활동내역 오류]");
        }
    }

}