package hr.system.api.edu.educdp.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.educdp.dto.EducdpcdprsltDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EducdpcdprsltMapper {
    CamelCaseMap findEducdpcdprsltList(EducdpcdprsltDto eduCdpcdprsltDto);

    CamelCaseMap findPgResultComAvg(EducdpcdprsltDto educdpcdprsltDto) ;

    CamelCaseMap findPgResultPgCodeAvg(EducdpcdprsltDto educdpcdprsltDto);

    CamelCaseMap findPgResultJobCodeAvg(EducdpcdprsltDto educdpcdprsltDto);

    CamelCaseMap findPgResultJobHopeAvg(EducdpcdprsltDto educdpcdprsltDto);
}