package hr.system.api.app.appmng.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapexcpterDto;
import hr.system.api.app.appmng.mapper.AppmngapexcpterMapper;
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
public class AppmngapexcpterService {

    private final AppmngapexcpterMapper appmngapexcpterMapper;

    public List<CamelCaseMap> findAppmngapexcpterList(AppmngapexcpterDto appmngapexcpterDto){
        return appmngapexcpterMapper.findAppmngapexcpterList(appmngapexcpterDto);
    }

    public List<CamelCaseMap> findappmngapAddPointList(AppmngapexcpterDto appmngapexcpterDto){
        return appmngapexcpterMapper.findappmngapAddPointList(appmngapexcpterDto);
    }

    public int checkApapprobj(AppmngapexcpterDto appmngapexcpterDto, List<AppmngapexcpterDto> appmngapexcpterDtoList){
        int result = 0;
        if(CollectionUtils.isNotEmpty(appmngapexcpterDtoList)) {
            for (AppmngapexcpterDto innerappmngapexcpterDto : appmngapexcpterDtoList) {
                result += appmngapexcpterMapper.checkApapprobj(innerappmngapexcpterDto);
            }
        }

        return result;
    }

    public  int inserTargetAddPointList(AppmngapexcpterDto appmngapexcpterDto){
        appmngapexcpterMapper.inserTargetAddPointList(appmngapexcpterDto); // 프로시저 호출
        /*log.info("appaiappobjlistgDto.getReturnCode()===>"+appaiappobjlistgDto.getReturnCode());
        log.info("appaiappobjlistgDto.getReturnMsg()===>"+appaiappobjlistgDto.getReturnMsg());*/
        int appRslt = appmngapexcpterDto.getReturnCode();
        if( appRslt < 1) {
            throw new ServiceException(appmngapexcpterDto.getReturnMsg());
        }

        return appRslt;
    }

    public int insertupdatedeleteAddPoint(AppmngapexcpterDto appmngapexcpterDto,  List<AppmngapexcpterDto> appmngapexcpterDtoList )  {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(appmngapexcpterDtoList)) {
            for(AppmngapexcpterDto innerappmngapexcpterDto : appmngapexcpterDtoList) {
                log.info("appmngapexcpterDtoList.getRowStatus()===>"+innerappmngapexcpterDto.getRowStatus());
                switch(innerappmngapexcpterDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = appmngapexcpterMapper.checkAddPintList(innerappmngapexcpterDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미 등록이 되어있어 등록을 할 수 없습니다.)");
                        }
                        result += appmngapexcpterMapper.insertAddPint(innerappmngapexcpterDto);
                        break;
                    case DataConst.UPDATE :
                        result += appmngapexcpterMapper.updateAddPint(innerappmngapexcpterDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += appmngapexcpterMapper.deleteAddPint(innerappmngapexcpterDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerappmngapexcpterDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }


    public int insertupdatedeleteApexcpter(AppmngapexcpterDto appmngapexcpterDto,  List<AppmngapexcpterDto> appmngapexcpterDtoList )  {
        int result = 0;
        int cnt = 0;
        if(CollectionUtils.isNotEmpty(appmngapexcpterDtoList)) {
            for(AppmngapexcpterDto innerappmngapexcpterDto : appmngapexcpterDtoList) {
                log.info("appmngapexcpterDtoList.getRowStatus()===>"+innerappmngapexcpterDto.getRowStatus());
                switch(innerappmngapexcpterDto.getRowStatus()) {
                    case DataConst.INSERT :
                        //등록
                        cnt = appmngapexcpterMapper.checkApexcpterList(innerappmngapexcpterDto);
                        if( cnt > 0) {
                            throw new ServiceException("이미예외자로 등록이 되어있어 예외자 등록을 할 수 없습니다.)");
                        }
                    case DataConst.UPDATE :
                        result += appmngapexcpterMapper.saveApexcpter(innerappmngapexcpterDto);
                        break;
                    case DataConst.DELETE :
                        //삭제
                        result += appmngapexcpterMapper.deleteApexcpter(innerappmngapexcpterDto);
                        break;
                    default :
                        log.debug("RowStatus : {}", innerappmngapexcpterDto.getRowStatus());
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
        return result;
    }
}
