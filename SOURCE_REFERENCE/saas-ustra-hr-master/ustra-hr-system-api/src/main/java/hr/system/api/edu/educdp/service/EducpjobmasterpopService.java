package hr.system.api.edu.educdp.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobindivmngDto;
import hr.system.api.edu.educdp.mapper.EducpjobmasterpopMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EducpjobmasterpopService {

    private final EducpjobmasterpopMapper educpjobmasterpopMapper;

    public List<CamelCaseMap> findEducpjobmasterList(EducdpcpjobindivmngDto educdpcpjobindivmngDto){
        return educpjobmasterpopMapper.findEducpjobmasterList(educdpcpjobindivmngDto);
    }
}