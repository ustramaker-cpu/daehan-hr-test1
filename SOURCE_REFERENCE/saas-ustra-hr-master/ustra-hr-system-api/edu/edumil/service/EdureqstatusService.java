package hr.system.api.edu.edumil.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumil.dto.EdureqstatusDto;
import hr.system.api.edu.edumil.mapper.EdureqstatusMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EdureqstatusService {
    private final EdureqstatusMapper edureqstatusMapper;

    /* 임직원용 교육신청현황 조회 */
    public List<CamelCaseMap> findEdureqstatusemplList(EdureqstatusDto edureqstatusDto) {
        return edureqstatusMapper.findEdureqstatusList(edureqstatusDto);
    }

    /* 부서장용 교육신청현황 조회 */
    public List<CamelCaseMap> findEdureqstatusleaderList(EdureqstatusDto edureqstatusDto) {
        return edureqstatusMapper.findEdureqstatusleaderList(edureqstatusDto);
    }

    /* 인사담당자용 교육신청현황 조회 */
    public List<CamelCaseMap> findEdureqstatusList(EdureqstatusDto edureqstatusDto) {
        return edureqstatusMapper.findEdureqstatusList(edureqstatusDto);
    }
}