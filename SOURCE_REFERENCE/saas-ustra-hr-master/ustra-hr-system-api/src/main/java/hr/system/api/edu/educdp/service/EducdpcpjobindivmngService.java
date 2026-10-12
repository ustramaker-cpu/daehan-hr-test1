package hr.system.api.edu.educdp.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcpjobindivmngDto;
import hr.system.api.edu.educdp.mapper.EducdpcpjobindivmngMapper;
import hr.system.core.DataConst;
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
public class EducdpcpjobindivmngService {

    private final EducdpcpjobindivmngMapper educdpcpjobindivmngMapper;

    public List<CamelCaseMap> findEducdpcpjobindivmngList(EducdpcpjobindivmngDto educdpcpjobindivmngDto){
        return educdpcpjobindivmngMapper.findEducdpcpjobindivmngList(educdpcpjobindivmngDto);
    }

    public List<CamelCaseMap> findEducdpcpjobindivHistory(EducdpcpjobindivmngDto educdpcpjobindivmngDto){
        return educdpcpjobindivmngMapper.findEducdpcpjobindivHistory(educdpcpjobindivmngDto);
    }

    /**
     * 개인별직무관리 저장
     * @param List<EducdpcpjobindivmngDto> - 등록할 정보가 담긴 educdpcpjobindivmngList
     * @return
     */
    public int saveEducdpcpjobindivmng(List<EducdpcpjobindivmngDto> educdpcpjobindivmngList) {
        int result = 0;

        if(CollectionUtils.isNotEmpty(educdpcpjobindivmngList)) {
            for(EducdpcpjobindivmngDto educdpcpjobindivmngDto : educdpcpjobindivmngList) {
                switch(educdpcpjobindivmngDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //기등록된 개인별직무관리의 유효시작일보다 이전 유효시작일인지 체크
                        if(0 < (educdpcpjobindivmngMapper.checkEduEffDateFrom(educdpcpjobindivmngDto)))
                            throw new ServiceException(educdpcpjobindivmngDto.getEmplNo()+" - "+ educdpcpjobindivmngDto.getJobCode() + " 개인별직무관리 등록시 유효시작일을 기등록된 유효시작일 이전일자로 등록할 수 없습니다.\n유효시작일을 확인하신 후 다시 등록하세요.");

                        if(0 < educdpcpjobindivmngMapper.checkEduEffDateTo(educdpcpjobindivmngDto)) {
                            // 신규 데이터 생성시 이전 데이터의 유효 종료일이 29991231이면
                            // 이전 데이터의 유효종료일을 신규 데이터 유효시작일의 이전날짜로 변경함
                            educdpcpjobindivmngMapper.updateEduEffDateTo1(educdpcpjobindivmngDto);
                        }

                        result += educdpcpjobindivmngMapper.insertEducdpcpjobindivmng(educdpcpjobindivmngDto);
                        break;
                    case DataConst.UPDATE :
                        result += educdpcpjobindivmngMapper.updateEducdpcpjobindivmng(educdpcpjobindivmngDto);
                        break;
                    case DataConst.DELETE :
                        // 가장 최신 데이터만 삭제할 수 있음
                        if(0 < educdpcpjobindivmngMapper.checkEduEffDateFrom(educdpcpjobindivmngDto)) {
                            throw new ServiceException("선택하신 ["+educdpcpjobindivmngDto.getJobCode()+"] 직무코드는 최신 자료가 아니므로 삭제할 수 없습니다.");
                        }

                        result += educdpcpjobindivmngMapper.deleteEducdpcpjobindivmng(educdpcpjobindivmngDto);
                        // 이력의 유효종료일을 삭제 이력의 유효종료일로 update
                        educdpcpjobindivmngMapper.updateEduEffDateTo2(educdpcpjobindivmngDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", educdpcpjobindivmngDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }
}