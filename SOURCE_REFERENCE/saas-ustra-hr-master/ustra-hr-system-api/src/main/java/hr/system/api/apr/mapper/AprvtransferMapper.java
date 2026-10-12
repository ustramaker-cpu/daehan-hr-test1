package hr.system.api.apr.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvtransferDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AprvtransferMapper {

    List<CamelCaseMap> findTransferList(AprvtransferDto req); // 대결자 관리 리스트 조회
    
    int countDuplTransferData(AprvtransferDto req);
    int countTransProperData(AprvtransferDto req);
    
    int insertTransferData(AprvtransferDto req);
    int updateTransferData(AprvtransferDto req);
    int deleteTransferData(AprvtransferDto req);
}
