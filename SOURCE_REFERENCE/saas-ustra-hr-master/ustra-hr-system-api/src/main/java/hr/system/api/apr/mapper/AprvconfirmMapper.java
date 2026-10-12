package hr.system.api.apr.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.apr.dto.AprvconfirmDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AprvconfirmMapper {

    List<CamelCaseMap> findAprvconfirmList(AprvconfirmDto aprvconfirmDto); // 확인자 리스트 조회
    int insertAprvconfirm(AprvconfirmDto aprvconfirmDto);                  // 확인자 추가
    int deleteAprvconfirm(AprvconfirmDto aprvconfirmDto);                  // 확인자 삭제

}
