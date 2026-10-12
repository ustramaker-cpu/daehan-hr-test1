package hr.system.api.edu.edumng.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EduidfwriteDto;
import hr.system.api.edu.edumng.mapper.EduidfwriteMapper;
import hr.system.api.edu.edumng.mapper.EduidfwritemngMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EduidfwritepopService {

    private final EduidfwriteMapper eduidfwriteMapper;
    private final EduidfwritemngMapper eduidfwritemngMapper;

    /**
     * 자기개발계획수립(담당자) 조회
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findEduidfwritemngList(EduidfwriteDto eduidfwriteDto) {
        return eduidfwritemngMapper.findEduidfwritemngList(eduidfwriteDto);
    }

    /**
     * 자기개발계획수립(담당자) 등록
     * @param eduidfwriteDto
     * @return int
     */
    public int insertEduidfwritemng(EduidfwriteDto eduidfwriteDto) {
        int checkCount = eduidfwriteMapper.checkCountNo(eduidfwriteDto);
        if( checkCount == 0) {
            eduidfwriteDto.setYearSeqNo("1");
        }else {
            int lastSeq = eduidfwriteMapper.checkYearSeqNo(eduidfwriteDto);
            eduidfwriteDto.setYearSeqNo(String.valueOf(lastSeq+1));
        }
        return eduidfwritemngMapper.insertEduidfwritemng(eduidfwriteDto);
    }

    public int updateEduidfwritemng(EduidfwriteDto eduidfwriteDto) {
        return eduidfwritemngMapper.updateEduidfwritemng(eduidfwriteDto);
    }

    /**
     * 자기개발계획수립(담당자) 삭제
     * @param eduidfwriteDto
     * @return int
     */
    public int deleteEduidfwritemng(EduidfwriteDto eduidfwriteDto) {
        return eduidfwritemngMapper.deleteEduidfwritemng(eduidfwriteDto);
    }

}