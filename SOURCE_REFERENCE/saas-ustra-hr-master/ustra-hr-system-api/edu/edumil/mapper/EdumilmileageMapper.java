package hr.system.api.edu.edumil.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumil.dto.EdumilmileageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EdumilmileageMapper {
    List<CamelCaseMap> findEdumilmileageList(EdumilmileageDto edumilmileageDto);

    List<CamelCaseMap> findCded18_vList(EdumilmileageDto edumilmileageDto);

    List<CamelCaseMap> findOrleader_vList(EdumilmileageDto edumilmileageDto);

    int checkEdumilmileageList(EdumilmileageDto edumilmileageDto) ;

    int checkEdumilmileageUploadList(EdumilmileageDto edumilmileageDto) ;


    int insertMileage(EdumilmileageDto edumilmileageDto) ;

    int updateMileage(EdumilmileageDto edumilmileageDto) ;

    int deleteMileage(EdumilmileageDto edumilmileageDto) ;

    int tranferUpdateMileage(EdumilmileageDto edumilmileageDto) ;

    EdumilmileageDto checkbackData(EdumilmileageDto edumilmileageDto);
}