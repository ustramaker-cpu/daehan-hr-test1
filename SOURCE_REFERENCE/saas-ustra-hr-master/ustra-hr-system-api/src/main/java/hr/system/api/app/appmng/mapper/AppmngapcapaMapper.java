package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapcapaDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngapcapaMapper {
    List<CamelCaseMap> findAppmngapcapaList(AppmngapcapaDto appmngapcapaDto);

    int checkAppgcapaList(AppmngapcapaDto appmngapcapaDto);

    int saveAppgcapa(AppmngapcapaDto appmngapcapaDto);

    int insertAppgcapa(AppmngapcapaDto appmngapcapaDto);

    int checkYearSeqNo(AppmngapcapaDto appmngapcapaDto);

    int updateAppgcapa(AppmngapcapaDto appmngapcapaDto);

    int deleteAppgcapa(AppmngapcapaDto appmngapcapaDto);

    int insertselectAppgcapa(AppmngapcapaDto appmngapcapaDto) ;

    int deleteYearAppgcapa(AppmngapcapaDto appmngapcapaDto) ;

}
