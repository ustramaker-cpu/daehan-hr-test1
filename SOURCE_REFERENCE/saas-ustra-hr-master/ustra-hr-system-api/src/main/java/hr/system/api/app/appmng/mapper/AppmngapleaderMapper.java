package hr.system.api.app.appmng.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.app.appmng.dto.AppmngapleaderDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppmngapleaderMapper {
    List<CamelCaseMap> findAppleader(AppmngapleaderDto ororgDto);    //조회
    int findAppleaderIns(AppmngapleaderDto appmngapleaderDto);       //대상자 생성

    int saveAppleader(AppmngapleaderDto appmngapleaderDto);         // 평가자 여부
    int deleteAppleader(AppmngapleaderDto appmngapleaderDto);   // 평가자 삭제
}
