package hr.system.api.edu.edumng.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumng.dto.EdumngcourseDto;
import hr.system.api.edu.edumng.mapper.EdumngcourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EdumngcourseService {

    private final EdumngcourseMapper edumngcourseMapper;

    public List<CamelCaseMap> findEdumngcourseList(EdumngcourseDto edumngcourseDto){
        return edumngcourseMapper.findEdumngcourseList(edumngcourseDto);
    }

    /**
     * 교육과정관리를 등록한다.
     * @param edumngcourseDto
     * @return int
     */
    public int insertEdumngcourse(EdumngcourseDto edumngcourseDto) {
        return edumngcourseMapper.insertEdumngcourse(edumngcourseDto);
    }

    public int updateEdumngcourse(EdumngcourseDto edumngcourseDto) {
        return edumngcourseMapper.updateEdumngcourse(edumngcourseDto);
    }

    /**
     * 교육과정관리를 삭제한다.
     * @param edumngcourseDto
     * @return int
     */
    public int deleteEdumngcourse(EdumngcourseDto edumngcourseDto) {
        return edumngcourseMapper.deleteEdumngcourse(edumngcourseDto);
    }

}