package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapcapaDto;
import hr.system.api.app.appmng.dto.AppmngapforallotpointDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngapforallotpointMapper {

    List<CamelCaseMap> findAppmngapforallotpointList(AppmngapforallotpointDto appmngapforallotpointDto);

    int saveApforallotpoint(AppmngapforallotpointDto appmngapforallotpointDto);
    int insertapforallotpoint(AppmngapforallotpointDto appmngapforallotpointDto);

    int updateapforallotpoint(AppmngapforallotpointDto appmngapforallotpointDto);

    int deleteapforallotpoint(AppmngapforallotpointDto appmngapforallotpointDto);

}
