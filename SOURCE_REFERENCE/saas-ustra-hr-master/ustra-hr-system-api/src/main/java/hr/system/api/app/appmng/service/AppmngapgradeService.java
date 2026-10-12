package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapgradeDto;
import hr.system.api.app.appmng.mapper.AppmngapgradeMapper;
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
public class AppmngapgradeService {

    private final AppmngapgradeMapper appmngapgradeMapper;
//    private final DayUtils dayUtils;

    public List<CamelCaseMap> findAppmngapgradeList(AppmngapgradeDto appmngapgradeDto){
        return appmngapgradeMapper.findAppmngapgradeList(appmngapgradeDto);
    }

    public List<CamelCaseMap> findGradeKindApprHistory(AppmngapgradeDto appmngapgradeDto){
        return appmngapgradeMapper.findGradeKindApprHistory(appmngapgradeDto);
    }

    public int insertupdatedeleteApgrade(AppmngapgradeDto appmngapgradeDto,  List<AppmngapgradeDto> appmngapgradeDtoList )  {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(appmngapgradeDtoList)) {
            for(AppmngapgradeDto innerAppmngapgradeDto : appmngapgradeDtoList) {
                log.info("appmngapgradeDtoList.getRowStatus()===>"+innerAppmngapgradeDto.getRowStatus());
                switch(innerAppmngapgradeDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = appmngapgradeMapper.checkApgradeList(innerAppmngapgradeDto);
                        if( cnt > 0) {
//                            throw new ServiceException("이미 등록된 평가등록코드가 있습니다.");

                            innerAppmngapgradeDto.setGradeKindApprOrg(innerAppmngapgradeDto.getGradeKindAppr());
                            innerAppmngapgradeDto.setGradeCodeApprOrg(innerAppmngapgradeDto.getGradeCodeAppr());

                            if(appmngapgradeMapper.checkApgradeEffdate(innerAppmngapgradeDto) > 0) {
                                throw new ServiceException("이미 등록된 평가등록코드의 시작일보다 이전으로 시작일을 설정할 수 없습니다.");
                            }

                            String effDateFromOrg = appmngapgradeMapper.checkApgradeEffdateto(innerAppmngapgradeDto);
                            innerAppmngapgradeDto.setEffDateFromOrg(effDateFromOrg);
                            if(effDateFromOrg != null) {
                                result += appmngapgradeMapper.updateApgradeEffdateto(innerAppmngapgradeDto);
                            }
                        }

                        result += appmngapgradeMapper.insertApgrade(innerAppmngapgradeDto);
                        break;
                    case DataConst.UPDATE :
//                        cnt = appmngapgradeMapper.checkApgradeList(innerAppmngapgradeDto);
//                        if( cnt > 0) {
//                            throw new ServiceException("이미 등록된 평가등록코드가 있습니다.");
//                        }

                        if(appmngapgradeMapper.checkApgradeEffdate(innerAppmngapgradeDto) > 0) {
                            throw new ServiceException("이미 등록된 평가등록코드의 시작일 이후로 종료일을 설정할 수 없습니다.");
                        }

                        result += appmngapgradeMapper.updateApgrade(innerAppmngapgradeDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += appmngapgradeMapper.deleteApgrade(innerAppmngapgradeDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerAppmngapgradeDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }

}
