package hr.system.api.edu.edumng.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfwriteDto;
import hr.system.api.edu.edumng.mapper.EduidfwriteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EduidfwriteService {

    private final EduidfwriteMapper eduidfwriteMapper;

    /**
     * 자기개발계획수립 조회
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findEduidfwriteList(EduidfwriteDto eduidfwriteDto) {
        return eduidfwriteMapper.findEduidfwriteList(eduidfwriteDto);
    }

    /**
     * 자기개발계획상태 조회
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> checkPlanStatus(EduidfwriteDto eduidfwriteDto) {
        return eduidfwriteMapper.checkPlanStatus(eduidfwriteDto);
    }

    /**
     * 교육과정 조회 팝업 리스트
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findEdcoursePopList(EduidfwriteDto eduidfwriteDto) {
        return eduidfwriteMapper.findEdcoursePopList(eduidfwriteDto);
    }

    /**
     * 교육과정 조회 팝업 리스트
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findEdcourseAllPopList(EduidfwriteDto eduidfwriteDto) {
        return eduidfwriteMapper.findEdcourseAllPopList(eduidfwriteDto);
    }



    /**
     * 직무마스터 조회
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findCpJobMasterList(EduidfwriteDto eduidfwriteDto) {
        return eduidfwriteMapper.findCpJobMasterList(eduidfwriteDto);
    }

    /**
     * 역량관리 조회
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findInitCpPgdesc(EduidfwriteDto eduidfwriteDto) {
        return eduidfwriteMapper.findInitCpPgdesc(eduidfwriteDto);
    }

    /**
     * 직무에 따른 역량관리 조회
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findCpPgdescList(EduidfwriteDto eduidfwriteDto) {
        return eduidfwriteMapper.findCpPgdescList(eduidfwriteDto);
    }

    /**
     * 자기개발계획수립 등록
     * @param eduidfwriteDto
     * @return int
     */
    public int insertEduidfwrite(EduidfwriteDto eduidfwriteDto) {
        int checkCount = eduidfwriteMapper.checkCountNo(eduidfwriteDto);
        if( checkCount == 0) {
            eduidfwriteDto.setYearSeqNo("1");
        }else {
            int lastSeq = eduidfwriteMapper.checkYearSeqNo(eduidfwriteDto);
            eduidfwriteDto.setYearSeqNo(String.valueOf(lastSeq+1));
        }
        return eduidfwriteMapper.insertEduidfwrite(eduidfwriteDto);
    }

    public int updateEduidfwrite(EduidfwriteDto eduidfwriteDto) {
        return eduidfwriteMapper.updateEduidfwrite(eduidfwriteDto);
    }

    /**
     * 자기개발계획수립 삭제
     * @param eduidfwriteDto
     * @return int
     */
    public int deleteEduidfwrite(EduidfwriteDto eduidfwriteDto) {
        return eduidfwriteMapper.deleteEduidfwrite(eduidfwriteDto);
    }

}