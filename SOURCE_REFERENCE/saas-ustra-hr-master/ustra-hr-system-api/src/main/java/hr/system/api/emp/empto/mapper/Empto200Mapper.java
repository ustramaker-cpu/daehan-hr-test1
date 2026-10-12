package hr.system.api.emp.empto.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.emp.empto.dto.Empto200Dto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Empto200Mapper {

    String findEmpto200DocNo(Empto200Dto empto200Dto); // 문서번호 생성

    List<CamelCaseMap> empto200Init(Empto200Dto empto200Dto);
    List<CamelCaseMap> findEmpto200(Empto200Dto empto200Dto);
    List<CamelCaseMap> findEmpto200his(Empto200Dto empto200Dto);

    int checkEmpto200Dupl(Empto200Dto empto200Dto);
    int checkAprvdocmstr(Empto200Dto empto200Dto);

    int saveEmpto200Mstr(Empto200Dto empto200Dto);
    int saveEmpto200Detail(Empto200Dto empto200Dto);
    int saveEmtpo200His(Empto200Dto empto200Dto);

    int updateEmpto200(Empto200Dto empto200Dto);
    int updateEmpto200Detail(Empto200Dto empto200Dto);

    int updateEmpto200His(Empto200Dto empto200Dto);
    int updateEmpto200HisPrevDay(Empto200Dto empto200Dto);
}
