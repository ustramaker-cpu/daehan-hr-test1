package hr.system.api.advice.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.advice.mapper.Advice100Mapper;
import hr.system.core.api.help.dto.HelpDto;
import hr.system.core.api.help.repository.HelpRepository;
import hr.system.core.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class Advice100Service {

    private final Advice100Mapper advice100Mapper;

    public List<CamelCaseMap> findAdvice100List(HelpDto helpDto) {
        return advice100Mapper.findAdvice100List(helpDto);
    }
}
