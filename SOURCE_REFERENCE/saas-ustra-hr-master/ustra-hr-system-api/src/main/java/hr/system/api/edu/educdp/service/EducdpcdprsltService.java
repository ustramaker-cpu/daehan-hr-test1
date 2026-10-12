package hr.system.api.edu.educdp.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcdprsltDto;
import hr.system.api.edu.educdp.mapper.EducdpcdprsltMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EducdpcdprsltService {

    private final EducdpcdprsltMapper educdpcdprsltMapper;

    public CamelCaseMap findEducdpcdprsltList(EducdpcdprsltDto educdpcdprsltDto){
        return educdpcdprsltMapper.findEducdpcdprsltList(educdpcdprsltDto);
    }

    public CamelCaseMap findPgResultComAvg(EducdpcdprsltDto educdpcdprsltDto) {
        return educdpcdprsltMapper.findPgResultComAvg(educdpcdprsltDto);
    }

    public CamelCaseMap findPgResultPgCodeAvg(EducdpcdprsltDto educdpcdprsltDto) {
        return educdpcdprsltMapper.findPgResultPgCodeAvg(educdpcdprsltDto);
    }

    public CamelCaseMap findPgResultJobCodeAvg(EducdpcdprsltDto educdpcdprsltDto) {
        return educdpcdprsltMapper.findPgResultJobCodeAvg(educdpcdprsltDto);
    }

    public CamelCaseMap findPgResultJobHopeAvg(EducdpcdprsltDto educdpcdprsltDto) {
        return educdpcdprsltMapper.findPgResultJobHopeAvg(educdpcdprsltDto);
    }
}