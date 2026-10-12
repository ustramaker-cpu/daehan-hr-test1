package hr.system.api.apr.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvtransfersetDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AprvtransfersetMapper { // 대리결재자

    List<CamelCaseMap> findAprvtransfersetList(AprvtransfersetDto aprvtransfersetDto);  // 대리결재자 조회
    int insertAprvtransfer(AprvtransfersetDto aprvtransfersetDto); // 대리결재자 저장
    int updateAprvtransfer(AprvtransfersetDto aprvtransfersetDto); // 대리결재자 수정
    int deleteAprvtransfer(AprvtransfersetDto aprvtransfersetDto); // 대리결재자 삭제

}
