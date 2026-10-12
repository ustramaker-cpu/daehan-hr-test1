package hr.system.api.edu.edumng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngedursltDto;
import hr.system.api.edu.edumng.mapper.EdumngedursltmngMapper;
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
public class EdumngedursltmnguploadService {

    private final EdumngedursltmngMapper edumngedursltmngMapper;

    public List<CamelCaseMap> findEdcourseList(EdumngedursltDto edumngedursltDto) {
        return edumngedursltmngMapper.findEdcourseList(edumngedursltDto);
    }

    public int checkbackData(List<EdumngedursltDto> edumngedursltDtoList) {
        int result = 0;

        if (CollectionUtils.isNotEmpty(edumngedursltDtoList)) {
            for (EdumngedursltDto dto : edumngedursltDtoList) {
                CamelCaseMap rs = edumngedursltmngMapper.checkbackData(dto);
                outputDataValidation(dto, rs);
                result ++;
            }
        }

        return result;
    }

    private void outputDataValidation(EdumngedursltDto inputDto, CamelCaseMap rs) {
        String emplCheck = rs.getString("emplCheck");
        if ("BLANK".equals(emplCheck)) {
            throw new ServiceException("사번: " + inputDto.getEmplNo() + " [사번코드 오류]");
        }

        String edcourseCheck = rs.getString("edcourseCheck");
        if ("BLANK".equals(edcourseCheck)) {
            throw new ServiceException("교육과정코드: " + inputDto.getCourseCode() + " [교육과정코드 오류]");
        }
    }

    public int saveedumngedursltmngupload(List<EdumngedursltDto> edumngedursltDtoList) {
        int result = 0;

        if (CollectionUtils.isNotEmpty(edumngedursltDtoList)) {
            for (EdumngedursltDto edumngedursltDto : edumngedursltDtoList) {
                result += edumngedursltmngMapper.insertEdedursltmng(edumngedursltDto);
            }
        }

        return result;
    }
}