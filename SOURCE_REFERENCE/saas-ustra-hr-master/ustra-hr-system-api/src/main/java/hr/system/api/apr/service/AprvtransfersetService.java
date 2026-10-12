package hr.system.api.apr.service;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvtransfersetDto;
import hr.system.api.apr.mapper.AprvtransfersetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AprvtransfersetService {

    private final AprvtransfersetMapper aprvtransfersetMapper;

    /**
     * 대리결재내역 조회
     * @param aprvtransfersetDto
     * @return List<CamelCaseMap>
     */
    public List<CamelCaseMap> findAprvtransfersetList(AprvtransfersetDto aprvtransfersetDto) {
        return aprvtransfersetMapper.findAprvtransfersetList(aprvtransfersetDto);
    }

    /**
     * 대리결재
     * @param aprvtransfersetDto
     * @return int
     */
    public int insertAprvtransfer(AprvtransfersetDto aprvtransfersetDto) {
        return aprvtransfersetMapper.insertAprvtransfer(aprvtransfersetDto);
    }

    /**
     * 회수
     * @param aprvtransfersetDto
     * @return int
     */
    public int updateAprvtransfer(AprvtransfersetDto aprvtransfersetDto) {
        return aprvtransfersetMapper.updateAprvtransfer(aprvtransfersetDto);
    }

    /**
     * 삭제
     * @param aprvtransfersetDto
     * @return int
     */
    public int deleteAprvtransfer(AprvtransfersetDto aprvtransfersetDto){
        return aprvtransfersetMapper.deleteAprvtransfer(aprvtransfersetDto);
    }

}
