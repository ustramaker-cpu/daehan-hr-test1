package hr.system.api.edu.edumng.service;

import com.gsitm.framework.core.exception.ServiceException;
import hr.system.api.edu.edumng.dto.EdumngcourseDto;
import hr.system.api.edu.edumng.mapper.EdumngcourseMapper;
import hr.system.core.DataConst;
import hr.system.core.emp.empas.dto.AsrecomndDto;
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
public class EdumngcourseuploadService {

    @Autowired
    private final EdumngcourseMapper edumngcourseMapper;

    public int saveEdumngcourseupload(List<EdumngcourseDto> edumngcourseList) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(edumngcourseList)) {
            for (EdumngcourseDto edumngcourseDto : edumngcourseList) {
                switch(edumngcourseDto.getRowStatus()) {
                    case DataConst.INSERT :
                        result += edumngcourseMapper.insertEdumngcourse(edumngcourseDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", edumngcourseDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

}