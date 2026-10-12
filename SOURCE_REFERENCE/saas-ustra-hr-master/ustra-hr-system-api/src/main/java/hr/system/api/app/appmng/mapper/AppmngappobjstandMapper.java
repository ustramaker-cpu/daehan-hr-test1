package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngappobjstandDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngappobjstandMapper {
    List<CamelCaseMap> findAppmngappobjstand(AppmngappobjstandDto ororgDto);    // 조회

    int saveAppmngappobjstand(AppmngappobjstandDto appmngappobjstandDto);
    int insertAppmngappobjstand(AppmngappobjstandDto appmngapleaderDto);        // 추가
    int updateAppmngappobjstand(AppmngappobjstandDto appmngapleaderDto);        // 수정
    int deleteAppmngappobjstand(AppmngappobjstandDto appmngapleaderDto);        // 삭제
}
