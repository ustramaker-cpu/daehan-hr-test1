package hr.system.api.apr.service;

import com.gsitm.framework.core.exception.ServiceException;
import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvtransferDto;
import hr.system.api.apr.mapper.AprvtransferMapper;
import hr.system.core.DataConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AprvtransferService {

    private final AprvtransferMapper aprvtransferMapper;

    public List<CamelCaseMap> findTransferList(AprvtransferDto req){
        return aprvtransferMapper.findTransferList(req);
    }

    public int saveTransferList(AprvtransferDto req){
        int result = 0;
        if(ObjectUtils.isNotEmpty(req.getDataList())) {
            for(AprvtransferDto i : req.getDataList()) {
                boolean isValid = true;
                if(StringUtils.isBlank(i.getEmplNo())) isValid = false;
                if(StringUtils.isBlank(i.getOrgCode())) isValid = false;
                if(StringUtils.isBlank(i.getTransEmplNo())) isValid = false;
                if(StringUtils.isBlank(i.getTransDateFrom())) isValid = false;
                if(StringUtils.isBlank(i.getTransTimeFrom())) isValid = false;
                if(StringUtils.isBlank(i.getTransDateTo())) isValid = false;
                if(StringUtils.isBlank(i.getTransTimeTo())) isValid = false;
                if(!isValid) throw new ServiceException("필수값을 확인해주세요.");
                
                i.setTransInd("Y");
    
                checkValidation(i);
                switch(i.getRowStatus()) {
                    case DataConst.INSERT:
                        result += insertTransferData(i);
                        break;
                    case DataConst.UPDATE:
                        result += updateTransferData(i);
                        break;
                    default:
                        throw new ServiceException("msg.system.access.wrong");
                }
            }
        }
    
        return result;
    }
    
    public void checkValidation(AprvtransferDto i) {
        //1. 같은 emplNo / orgCode / transEmplNo + transDateXX가 겹치면 안된다.
        //2. 대결자는 지정 기간에 등록된 사용자가 아니어야한다.
        
        //length 4 or 5
        if(i.getTransTimeFrom().length() < 4) {
            throw new ServiceException("시간(From)의 값이 올바르지 않습니다.");
        }
        //length 4 or 5
        if(i.getTransTimeTo().length() < 4) {
            throw new ServiceException("시간(To)의 값이 올바르지 않습니다.");
        }
        
        i.setTransDateFrom(i.getTransDateFrom().replace("-",""));
        i.setTransDateTo(i.getTransDateTo().replace("-",""));
        
        int startTime =
            Integer.parseInt(i.getTransTimeFrom().substring(0,2))*60
            + Integer.parseInt(i.getTransTimeFrom().substring(i.getTransTimeFrom().length()-2));
        
        int endTime =
            Integer.parseInt(i.getTransTimeTo().substring(0,2))*60
            + Integer.parseInt(i.getTransTimeTo().substring(i.getTransTimeTo().length()-2));
        
        int startDate = Integer.parseInt(i.getTransDateFrom());
        int endDate = Integer.parseInt(i.getTransDateTo());
        
        if(startDate > endDate) {
            throw new ServiceException("설정기간을 확인해주세요.");
        }else if(startDate == endDate && startTime > endTime) {
            throw new ServiceException("설정시간을 확인해주세요.");
        }
    
        if(startTime >= 1440 || endTime >= 1440) {
            throw new ServiceException("설정시간은 23:59를 넘길 수 없습니다.");
        }
        
        i.setTransDateFrom(i.getTransDateFrom()+""+i.getTransTimeFrom().substring(0,2)+i.getTransTimeFrom().substring(i.getTransTimeFrom().length()-2)+"00");
        i.setTransDateTo(i.getTransDateTo()+""+i.getTransTimeTo().substring(0,2)+i.getTransTimeTo().substring(i.getTransTimeTo().length()-2)+"00");
    
        //TODO + 슈퍼인 경우에는 현재 로그인한 ID가 아니어도 insert 가능 아니라면 자기꺼만
        int count = aprvtransferMapper.countDuplTransferData(i);
        if(count > 0) {
            String errMsg = "이미 등록되어 있는 데이터가 있습니다.";
            errMsg += "("+i.getEmplNameHan()+"/"+i.getOrgNameHan()+"/"+i.getTransEmplNameHan()+")";
            throw new ServiceException(errMsg);
        }
        
        int count2 = aprvtransferMapper.countTransProperData(i);
        if(count2 > 0) {
            String errMsg = "지정한 대결자는 지정 기간에 이미 등록된 사용자입니다.";
            errMsg += "("+i.getEmplNameHan()+"/"+i.getOrgNameHan()+"/"+i.getTransEmplNameHan()+")";
            throw new ServiceException(errMsg);
        }
    }
    
    public int insertTransferData(AprvtransferDto i) {
        int result = 0;
        result += aprvtransferMapper.insertTransferData(i);
        return result;
    }
    
    public int updateTransferData(AprvtransferDto i) {
        int result = 0;
        result += aprvtransferMapper.updateTransferData(i);
        return result;
    }
    
    public int deleteTransferList(AprvtransferDto req) {
        int result = 0;
        if(ObjectUtils.isNotEmpty(req.getDataList())) {
            for(AprvtransferDto i : req.getDataList()) {
                result += deleteTransferData(i);
            }
        }
        return result;
    }
    
    public int deleteTransferData(AprvtransferDto i) {
        int result = 0;
        i.setTransDateFrom(i.getTransDateFrom()+""+i.getTransTimeFrom().substring(0,2)+i.getTransTimeFrom().substring(i.getTransTimeFrom().length()-2)+"00");
        result += aprvtransferMapper.deleteTransferData(i);
        return result;
    }

}
