package hr.system.api.edu.edumil.mapper;

import com.gsitm.framework.core.type.CamelCaseMap;
import hr.system.api.edu.edumil.dto.EduclreqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduclreqdocMapper {
    List<CamelCaseMap> findEduclreqList(EduclreqDto educlreqDto);           // 문서함에서 들어간 경우 신청서 조회
    List<CamelCaseMap> findEdureqList(EduclreqDto educlreqDto);             // (팝업) 교육 신청내역 조회
    String getDocNo(EduclreqDto educlreqDto);                               // 문서번호 생성
    int insertEduclreq(EduclreqDto educlreqDto);                            // 교육 취소신청 내역 저장
}
